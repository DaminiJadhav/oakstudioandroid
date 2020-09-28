/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sdaemon.oakstudiotv.trackselectionclass;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.Nullable;

import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.google.android.exoplayer2.ui.DefaultTrackNameProvider;
import com.google.android.exoplayer2.ui.TrackNameProvider;
import com.google.android.exoplayer2.util.Assertions;
import com.sdaemon.oakstudiotv.utils.AppSession;

//import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
//import org.checkerframework.checker.nullness.qual.RequiresNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** A view for making track selections. */
public class TrackSelectionView extends LinearLayout {

  /** Listener for changes to the selected tracks. */
  public interface TrackSelectionListener {

    /**
     * Called when the selected tracks changed.
     *
     * @param isDisabled Whether the renderer is disabled.
     * @param overrides List of selected track selection overrides for the renderer.
     */
    void onTrackSelectionChanged(boolean isDisabled, List<SelectionOverride> overrides);
  }

  private final int selectableItemBackgroundResourceId;
  private final LayoutInflater inflater;
  private final CheckedTextView disableView;
  private final CheckedTextView defaultView;
  private  CheckedTextView emptyView;
  Boolean checkemptyView=true;
    int checkedPosition = 0;
    AppSession appSession;
    private final ComponentListener componentListener;
  private final SparseArray<SelectionOverride> overrides;
  List<String > list;

  private boolean allowAdaptiveSelections;
  private boolean allowMultipleOverrides;
  int max,min,maxindex=0,minindex=0;
  private String[] resoNameInitial = {"Large ", "Medium ", "Small", "Smaller"};
    int high = 0,medium = 0, low = 0,support=0;



  private TrackNameProvider trackNameProvider;
  private CheckedTextView[][] trackViews;
//  @MonotonicNonNull
  private MappingTrackSelector.MappedTrackInfo mappedTrackInfo;
  private int rendererIndex;
  private TrackGroupArray trackGroups;
  private boolean isDisabled;
  @Nullable
  private TrackSelectionListener listener;

  /** Creates a track selection view. */
  public TrackSelectionView(Context context) {
    this(context, null);
  }

  /** Creates a track selection view. */
  public TrackSelectionView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  /** Creates a track selection view. */
  @SuppressWarnings("nullness")
  public TrackSelectionView(
          Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setOrientation(LinearLayout.VERTICAL);

    overrides = new SparseArray<>();

    // Don't save view hierarchy as it needs to be reinitialized with a call to init.
    setSaveFromParentEnabled(false);

    TypedArray attributeArray =
        context
            .getTheme()
            .obtainStyledAttributes(new int[] {android.R.attr.selectableItemBackground});
    selectableItemBackgroundResourceId = attributeArray.getResourceId(0, 0);
    attributeArray.recycle();

    inflater = LayoutInflater.from(context);
    componentListener = new ComponentListener();
    trackNameProvider = new DefaultTrackNameProvider(getResources());
    trackGroups = TrackGroupArray.EMPTY;

    // View for disabling the renderer.
    disableView =
        (CheckedTextView)
            inflater.inflate(android.R.layout.simple_list_item_single_choice, this, false);
    disableView.setBackgroundResource(selectableItemBackgroundResourceId);
    disableView.setText(com.google.android.exoplayer2.ui.R.string.exo_track_selection_none);
    disableView.setEnabled(false);
    disableView.setFocusable(true);
    disableView.setOnClickListener(componentListener);
    disableView.setVisibility(View.GONE);
    addView(disableView);
    // Divider view.
    addView(inflater.inflate(com.google.android.exoplayer2.ui.R.layout.exo_list_divider, this, false));
    // View for clearing the override to allow the selector to use its default selection logic.
    defaultView =
        (CheckedTextView)
            inflater.inflate(android.R.layout.simple_list_item_single_choice, this, false);
    defaultView.setBackgroundResource(selectableItemBackgroundResourceId);
    defaultView.setText(com.google.android.exoplayer2.ui.R.string.exo_track_selection_auto);
    defaultView.setEnabled(false);
    defaultView.setFocusable(true);
//    disableView.setVisibility(View.INVISIBLE);
    defaultView.setOnClickListener(componentListener);
//    addView(defaultView,0);
  }

  /**
   * Sets whether adaptive selections (consisting of more than one track) can be made using this
   * selection view.
   *
   * <p>For the view to enable adaptive selection it is necessary both for this feature to be
   * enabled, and for the target renderer to support adaptation between the available tracks.
   *
   * @param allowAdaptiveSelections Whether adaptive selection is enabled.
   */
  public void setAllowAdaptiveSelections(boolean allowAdaptiveSelections) {
    if (this.allowAdaptiveSelections != allowAdaptiveSelections) {
      this.allowAdaptiveSelections = allowAdaptiveSelections;
      updateViews();
    }
  }

  /**
   * Sets whether tracks from multiple track groups can be selected. This results in multiple {@link
   * SelectionOverride SelectionOverrides} to be returned by {@link #getOverrides()}.
   *
   * @param allowMultipleOverrides Whether multiple track selection overrides can be selected.
   */
  public void setAllowMultipleOverrides(boolean allowMultipleOverrides) {
    if (this.allowMultipleOverrides != allowMultipleOverrides) {
      this.allowMultipleOverrides = allowMultipleOverrides;
      if (!allowMultipleOverrides && overrides.size() > 1) {
        for (int i = overrides.size() - 1; i > 0; i--) {
          overrides.remove(i);
        }
      }
      updateViews();
    }
  }

  /**
   * Sets whether an option is available for disabling the renderer.
   *
   * @param showDisableOption Whether the disable option is shown.
   */
  public void setShowDisableOption(boolean showDisableOption) {
    disableView.setVisibility(showDisableOption ? View.VISIBLE : View.GONE);
  }

  /**
   * Sets the {@link TrackNameProvider} used to generate the user visible name of each track and
   * updates the view with track names queried from the specified provider.
   *
   * @param trackNameProvider The {@link TrackNameProvider} to use.
   */
  public void setTrackNameProvider(TrackNameProvider trackNameProvider) {
    this.trackNameProvider = Assertions.checkNotNull(trackNameProvider);
    updateViews();
  }

  /**
   * Initialize the view to select tracks for a specified renderer using {@link MappedTrackInfo} and
   * a set of {@link DefaultTrackSelector.Parameters}.
   *
   * @param mappedTrackInfo The {@link MappedTrackInfo}.
   * @param rendererIndex The index of the renderer.
   * @param isDisabled Whether the renderer should be initially shown as disabled.
   * @param overrides List of initial overrides to be shown for this renderer. There must be at most
   *     one override for each track group. If {@link #setAllowMultipleOverrides(boolean)} hasn't
   *     been set to {@code true}, only the first override is used.
   * @param listener An optional listener for track selection updates.
   */
  public void init(
      MappedTrackInfo mappedTrackInfo,
      int rendererIndex,
      boolean isDisabled,
      List<SelectionOverride> overrides,
      @Nullable TrackSelectionListener listener) {
    this.mappedTrackInfo = mappedTrackInfo;
    this.rendererIndex = rendererIndex;
    this.isDisabled = isDisabled;
    this.listener = listener;
    int maxOverrides = allowMultipleOverrides ? overrides.size() : Math.min(overrides.size(), 1);
    for (int i = 0; i < maxOverrides; i++) {
      SelectionOverride override = overrides.get(i);
      this.overrides.put(override.groupIndex, override);
    }
    updateViews();
  }

  /** Returns whether the renderer is disabled. */
  public boolean getIsDisabled() {
    return isDisabled;
  }

  /**
   * Returns the list of selected track selection overrides. There will be at most one override for
   * each track group.
   */
  public List<SelectionOverride> getOverrides() {
    List<SelectionOverride> overrideList = new ArrayList<>(overrides.size());
    for (int i = 0; i < overrides.size(); i++) {
      overrideList.add(overrides.valueAt(i));
    }
    return overrideList;
  }

  // Private methods.

  private void updateViews() {
    // Remove previous per-track views.
    for (int i = getChildCount() - 1; i >= 3; i--) {
      removeViewAt(i);
    }

    if (mappedTrackInfo == null) {
      // The view is not initialized.
      disableView.setEnabled(false);
      defaultView.setEnabled(false);
      return;
    }
    disableView.setEnabled(true);
    defaultView.setEnabled(true);

    trackGroups = mappedTrackInfo.getTrackGroups(rendererIndex);

    // Add per-track views.
    trackViews = new CheckedTextView[trackGroups.length][];
    boolean enableMultipleChoiceForMultipleOverrides = shouldEnableMultiGroupSelection();
    for (int groupIndex = 0; groupIndex < trackGroups.length; groupIndex++) {
      TrackGroup group = trackGroups.get(groupIndex);
      boolean enableMultipleChoiceForAdaptiveSelections = shouldEnableAdaptiveSelection(groupIndex);
      trackViews[groupIndex] = new CheckedTextView[group.length];
      for (int trackIndex = 0; trackIndex < group.length; trackIndex++) {


//        if (trackIndex == 0) {
//          addView(inflater.inflate(com.google.android.exoplayer2.ui.R.layout.exo_list_divider, this, false));
//        }

        int trackViewLayoutId =
            enableMultipleChoiceForAdaptiveSelections || enableMultipleChoiceForMultipleOverrides
                ? android.R.layout.simple_list_item_multiple_choice
                : android.R.layout.simple_list_item_single_choice;
        CheckedTextView trackView =
            (CheckedTextView) inflater.inflate(trackViewLayoutId, this, false);
//        trackView.setContentDescription(resoNameInitial[trackIndex] + "");
//        trackView.setBackgroundResource(selectableItemBackgroundResourceId);
//        trackView.setText(trackNameProvider.getTrackName(group.getFormat(trackIndex)));
//        trackView.setText(resoNameInitial[trackIndex] + "");
          appSession = AppSession.getInstance(getContext().getApplicationContext());
          appSession.setvalueID("nonhd");
          String value="";
          value=appSession.getvalueID();

                  if (group.getFormat(trackIndex).height == 720  && high <= 0){
                      checkemptyView=false;
                      Log.i("High Quality", "" + trackNameProvider.getTrackName(group.getFormat(trackIndex)));
                      high++;
                      if((value.equals("ultrahd"))) {
                          if (mappedTrackInfo.getTrackSupport(rendererIndex, groupIndex, trackIndex)
                                  == RendererCapabilities.FORMAT_HANDLED) {
                              trackView.setBackgroundResource(selectableItemBackgroundResourceId);
                              trackView.setText("High");
                              trackView.setFocusable(true);
//                          trackView.setChecked(trackView.isChecked());
                              trackView.setTag(Pair.create(groupIndex, trackIndex));
                              trackView.setOnClickListener(componentListener);
                          } else {
                              trackView.setText("Not support HD video quality");
                              trackView.setFocusable(false);
                              trackView.setEnabled(false);
                          }
                          trackViews[groupIndex][trackIndex] = trackView;
                          addView(trackView, 0);
                      }

                  }

                  else if (group.getFormat(trackIndex).height == 432  && medium <= 0) {
                      checkemptyView=false;
                      Log.i("Medium Quality", "" + trackNameProvider.getTrackName(group.getFormat(trackIndex)));
                      medium++;
                      if(value.equals("ultrahd") || value.equals("hd")) {
                          if (mappedTrackInfo.getTrackSupport(rendererIndex, groupIndex, trackIndex)
                                  == RendererCapabilities.FORMAT_HANDLED) {
                              trackView.setBackgroundResource(selectableItemBackgroundResourceId);
                              trackView.setText("Medium");
                              trackView.setFocusable(true);
                              trackView.setTag(Pair.create(groupIndex, trackIndex));
                              trackView.setOnClickListener(componentListener);
                          } else {
                              trackView.setText("Not support Standard video quality");
                              trackView.setFocusable(false);
                              trackView.setEnabled(false);
                          }
                          trackViews[groupIndex][trackIndex] = trackView;
                          addView(trackView, 1);
                      }

                  }

               else    if (group.getFormat(trackIndex).height == 360  && low<= 0) {
                      checkemptyView=false;
                      Log.i("Low Quality", "" + trackNameProvider.getTrackName(group.getFormat(trackIndex)));
                      low++;
                      if(value.equals("ultrahd") || value.equals("hd") || value.equals("nonhd")) {
                          if (mappedTrackInfo.getTrackSupport(rendererIndex, groupIndex, trackIndex)
                                  == RendererCapabilities.FORMAT_HANDLED) {
                              trackView.setBackgroundResource(selectableItemBackgroundResourceId);
                              trackView.setText("Low");
                              trackView.setFocusable(true);
                              trackView.setTag(Pair.create(groupIndex, trackIndex));
                              trackView.setOnClickListener(componentListener);
                          } else {
                              trackView.setText("Not support Basic video quality");
                              trackView.setFocusable(false);
                              trackView.setEnabled(false);
                          }
                          trackViews[groupIndex][trackIndex] = trackView;
                          addView(trackView, 2);
                      }
                  }


//------------------------------------------------------------------------------------
//          if (group.length>1) {
//
//              if (large <= 0) {
//                  for (int i = 0; i < group.length; i++) {
//                      large++;
//                      if (group.getFormat(i).height == 720 && large1 == true) {
//                          Log.i("Large Quality", "" + trackNameProvider.getTrackName(group.getFormat(i)));
//
//                          large1 = false;
//                          trackView.setBackgroundResource(selectableItemBackgroundResourceId);
//                          trackView.setText("Large");
//                          if (mappedTrackInfo.getTrackSupport(rendererIndex, groupIndex, i)
//                                  == RendererCapabilities.FORMAT_HANDLED) {
//                              trackView.setFocusable(true);
//                              trackView.setTag(Pair.create(groupIndex, i));
//                              trackView.setOnClickListener(componentListener);
//                          } else {
//                              trackView.setFocusable(false);
//                              trackView.setEnabled(false);
//                          }
//                          trackViews[groupIndex][i] = trackView;
//                          addView(trackView);
//
//                      }
//
//                  }
//
//
//              } else if (medium <= 0) {
//                  for (int j = 0; j < group.length; j++) {
//                      medium++;
//                      if (group.getFormat(j).height == 432 && medium1 == true) {
//                          Log.i("Medium Quality", "" + trackNameProvider.getTrackName(group.getFormat(j)));
//
//                          medium1 = false;
//                          trackView.setBackgroundResource(selectableItemBackgroundResourceId);
//                          trackView.setText("Medium");
//                          if (mappedTrackInfo.getTrackSupport(rendererIndex, groupIndex, j)
//                                  == RendererCapabilities.FORMAT_HANDLED) {
//                              trackView.setFocusable(true);
//                              trackView.setTag(Pair.create(groupIndex, j));
//                              trackView.setOnClickListener(componentListener);
//                          } else {
//                              trackView.setFocusable(false);
//                              trackView.setEnabled(false);
//                          }
//                          trackViews[groupIndex][j] = trackView;
//                          addView(trackView);
//
//
//                      }
//
//                  }
//
//
//              } else if (small <= 0) {
//                  for (int k = 0; k < group.length; k++) {
//                      small++;
//                      if (group.getFormat(k).height == 360 && small1 == true) {
//                          Log.i("Small Quality", "" + trackNameProvider.getTrackName(group.getFormat(k)));
//                          small1 = false;
//                          trackView.setBackgroundResource(selectableItemBackgroundResourceId);
//                          trackView.setText("Small");
//                          if (mappedTrackInfo.getTrackSupport(rendererIndex, groupIndex, k)
//                                  == RendererCapabilities.FORMAT_HANDLED) {
//                              trackView.setFocusable(true);
//                              trackView.setTag(Pair.create(groupIndex, k));
//                              trackView.setOnClickListener(componentListener);
//                          } else {
//                              trackView.setFocusable(false);
//                              trackView.setEnabled(false);
//                          }
//                          trackViews[groupIndex][k] = trackView;
//                          addView(trackView);
//
//                      }
//
//                  }
//
//              }
//          }
//          }
//-------------------------------------------------------------------------
//             max=group.getFormat(0).bitrate;
//             min=group.getFormat(0).bitrate;
//
//           for (int i=1;i<group.length;i++){
//              if(group.getFormat(i).bitrate>max){
//                max=group.getFormat(i).bitrate;
//                maxindex=i;
//              }
//
//             if (group.getFormat(i).bitrate<min){
//                min=group.getFormat(i).bitrate;
//                minindex=i;
//              }
//            }

//------------------------------------------


//        if (mappedTrackInfo.getTrackSupport(rendererIndex, groupIndex, trackIndex)
//            == RendererCapabilities.FORMAT_HANDLED) {
//          trackView.setFocusable(true);
//          trackView.setTag(Pair.create(groupIndex, trackIndex));
//          trackView.setOnClickListener(componentListener);
//        } else {
//          trackView.setFocusable(false);
//          trackView.setEnabled(false);
//        }

//            trackViews[groupIndex][trackIndex] = trackView;
//            addView(trackView);
//          }
//            if (trackView.length()==0){
//
//            }
          Log.i("All Quality ","" +trackNameProvider.getTrackName(group.getFormat(trackIndex)));

      }

      if (checkemptyView==true){
//          TrackSelectionDialog.emptyqualitymsg="djf";
          emptyView =
                  (CheckedTextView)
                          inflater.inflate(android.R.layout.simple_list_item_single_choice, this, false);
//                        Drawable dr = this.getResources().getDrawable(R.drawable.iv_error);
//                        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
//                        Drawable d = new BitmapDrawable(this.getResources(), Bitmap.createScaledBitmap(bitmap, 75, 75, true));
//          emptyView.setCompoundDrawablesWithIntrinsicBounds(d,null,null,null);
          emptyView.setBackgroundResource(selectableItemBackgroundResourceId);
          emptyView.setText("\nYour device is not supported video quality");
          emptyView.setEnabled(true);
          emptyView.setFocusable(true);
          emptyView.setCheckMarkDrawable(null);
          emptyView.setVisibility(View.VISIBLE);
          addView(emptyView);
      }

      Log.i("Max Bitrate",""+max);
      Log.i("Min Bitrate",""+min);
      Log.i("Index",""+maxindex);
      Log.i("Index",""+minindex);

    }

    updateViewStates();
  }

  private void updateViewStates() {
    disableView.setChecked(isDisabled);
      defaultView.setChecked(!isDisabled && overrides.size() == 0);
    for (int i = 0; i < trackViews.length; i++) {
      SelectionOverride override = overrides.get(i);

//      for (int j = 0; j < trackViews[i].length; j++) {
//            trackViews[i][2].setChecked(override != null && override.containsTrack(j));
//        }



        for (int j = 0; j < trackViews[i].length; j++) {
//            trackViews[i][1].setChecked(!isDisabled && overrides.size()==0);

            if (trackViews[i][j]!=null){
                if (trackViews[i][j].getText().equals("High")) {
                    if (checkedPosition==0) {
//                        checkedPosition = 0;
                        trackViews[i][j].setChecked(override != null && override.containsTrack(j));
                    }
                }else  if (trackViews[i][j].getText().equals("Medium")){
//                    checkedPosition=1;
                    trackViews[i][j].setChecked(override != null && override.containsTrack(j));
                }else  if (trackViews[i][j].getText().equals("Low")){
//                    checkedPosition=2;
                    trackViews[i][j].setChecked(override != null && override.containsTrack(j));
                }
            }

        }


//-------------------------------------------------------------------------------
//              for (int j = 0; j < trackViews[i].length; j++) {
//
//                  if (trackViews[i][j]!=null){
//                      if (trackViews[i][j].getHeight()==720){
//                          trackViews[i][j].setChecked(override != null && override.containsTrack(j));
//                      }else  if (trackViews[i][j].getHeight()==480){
//                          trackViews[i][j].setChecked(override != null && override.containsTrack(j));
//                      }else  if (trackViews[i][j].getHeight()==360){
//                          trackViews[i][j].setChecked(override != null && override.containsTrack(j));
//                      }
//                  }
//              }

//--------------------------------------------------------------------------------------
//            for (int j = 0; j < trackViews[i].length; j++) {
//              if (j<4){
//                if (j==0){
//                  trackViews[i][0].setChecked(override != null && override.containsTrack(j));
//                }
//                if (j==1){
//                  trackViews[i][1].setChecked(override != null && override.containsTrack(j));
//                }
//                if (j==2){
//                  trackViews[i][2].setChecked(override != null && override.containsTrack(j));
//                }
//                if (j==3){
//                  trackViews[i][3].setChecked(override != null && override.containsTrack(j));
//                }
//              }
//            }
//--------------------------------------------------------------------------------------


    }
  }

  private void onClick(View view) {
    if (view == disableView) {
      onDisableViewClicked();
    } else if (view == defaultView) {
      onDefaultViewClicked();
    } else {
      onTrackViewClicked(view);
    }
    updateViewStates();
    if (listener != null) {
      listener.onTrackSelectionChanged(getIsDisabled(), getOverrides());
    }
  }

  private void onDisableViewClicked() {
    isDisabled = true;
    overrides.clear();
  }

  private void onDefaultViewClicked() {
    isDisabled = false;
    overrides.clear();
  }

  private void onTrackViewClicked(View view) {
    isDisabled = false;
    @SuppressWarnings("unchecked")
    Pair<Integer, Integer> tag = (Pair<Integer, Integer>) view.getTag();
    int groupIndex = tag.first;
    int trackIndex = tag.second;
    SelectionOverride override = overrides.get(groupIndex);
    Assertions.checkNotNull(mappedTrackInfo);
    if (override == null) {
      // Start new override.
      if (!allowMultipleOverrides && overrides.size() > 0) {
        // Removed other overrides if we don't allow multiple overrides.
        overrides.clear();
      }
      overrides.put(groupIndex, new SelectionOverride(groupIndex, trackIndex));
    } else {
      // An existing override is being modified.
      int overrideLength = override.length;
      int[] overrideTracks = override.tracks;
      boolean isCurrentlySelected = ((CheckedTextView) view).isChecked();
      boolean isAdaptiveAllowed = shouldEnableAdaptiveSelection(groupIndex);
      boolean isUsingCheckBox = isAdaptiveAllowed || shouldEnableMultiGroupSelection();
      if (isCurrentlySelected && isUsingCheckBox) {
        // Remove the track from the override.
        if (overrideLength == 1) {
          // The last track is being removed, so the override becomes empty.
          overrides.remove(groupIndex);
        } else {
          int[] tracks = getTracksRemoving(overrideTracks, trackIndex);
          overrides.put(groupIndex, new SelectionOverride(groupIndex, tracks));
        }
      } else if (!isCurrentlySelected) {
        if (isAdaptiveAllowed) {
          // Add new track to adaptive override.
          int[] tracks = getTracksAdding(overrideTracks, trackIndex);
          overrides.put(groupIndex, new SelectionOverride(groupIndex, tracks));
        } else {
          // Replace existing track in override.
          overrides.put(groupIndex, new SelectionOverride(groupIndex, trackIndex));
        }
      }
    }
  }

//  @RequiresNonNull("mappedTrackInfo")
  private boolean shouldEnableAdaptiveSelection(int groupIndex) {
    return allowAdaptiveSelections
        && trackGroups.get(groupIndex).length > 1
        && mappedTrackInfo.getAdaptiveSupport(rendererIndex, groupIndex, false)
            != RendererCapabilities.ADAPTIVE_NOT_SUPPORTED;
  }

  private boolean shouldEnableMultiGroupSelection() {
    return allowMultipleOverrides && trackGroups.length > 1;
  }

  private static int[] getTracksAdding(int[] tracks, int addedTrack) {
    tracks = Arrays.copyOf(tracks, tracks.length + 1);
    tracks[tracks.length - 1] = addedTrack;
    return tracks;
  }

  private static int[] getTracksRemoving(int[] tracks, int removedTrack) {
    int[] newTracks = new int[tracks.length - 1];
    int trackCount = 0;
    for (int track : tracks) {
      if (track != removedTrack) {
        newTracks[trackCount++] = track;
      }
    }
    return newTracks;
  }

  // Internal classes.

  private class ComponentListener implements OnClickListener {

    @Override
    public void onClick(View view) {
     TrackSelectionView.this.onClick(view);
    }
  }
}
