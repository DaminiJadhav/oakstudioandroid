package com.sdaemon.oakstudiotv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.dto.SupportDTO;

import java.util.HashMap;
import java.util.List;

public class SupportExpandListViewAdapter extends BaseExpandableListAdapter {
private Context context;
private List<String> answerList;
private HashMap<String, List<String>> expandableListDetail;
    List<SupportDTO.Questions> questions;
    List<SupportDTO.Answers> answers;


    public SupportExpandListViewAdapter(Context context,List<String> answerList,HashMap<String,  List<String>> expandableListDetail){
        this.context=context;
        this.answerList=answerList;
        this.expandableListDetail=expandableListDetail;
    }

    public SupportExpandListViewAdapter(Context context, List<SupportDTO.Questions> questions, List<SupportDTO.Answers> answers){
        this.context=context;
        this.questions=questions;
        this.answers=answers;

    }



    @Override
    public int getGroupCount() {
        return answerList.size();
//        return answerList.size();

    }

    @Override
    public int getChildrenCount(int i) {
        return  expandableListDetail.get(answerList.get(i)).size();
//        return expandableListDetail.get(answerList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return this.expandableListDetail.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return this.expandableListDetail.get(this.answerList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
//        String listTitle = (String) getGroup(i);
        String listTitle = answerList.get(i);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.support_group_item, null);
        }
        TextView listTitleTextView = (TextView) view
                .findViewById(R.id.tv_expandlistview_heading);
//        listTitleTextView.setTypeface(null, Typeface.BOLD);
//        listTitleTextView.setText(""+expandableListDetail.get(i));

        listTitleTextView.setText(listTitle);
        ImageView imageView=view.findViewById(R.id.iv_plus1);
        if (b){

            imageView.setImageResource(R.drawable.iv_minus);
            imageView.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
            imageView.setSelected(b);

        } else {
            imageView.setImageResource(R.drawable.iv_plus);
            imageView.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
        }


        return view;

    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String expandedListText = (String) getChild(i, i1);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.support_child_item, null);
        }
        TextView expandedListTextView = (TextView) view
                .findViewById(R.id.tv_support_child);
//        expandedListTextView.setText(""+answerList.get(i));
        expandedListTextView.setText(expandedListText);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
