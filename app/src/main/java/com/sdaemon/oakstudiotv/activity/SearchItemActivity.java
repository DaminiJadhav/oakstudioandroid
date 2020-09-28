package com.sdaemon.oakstudiotv.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sdaemon.oakstudiotv.Pagination.PaginationAdapterCallback;
import com.sdaemon.oakstudiotv.Pagination.PaginationScrollListener;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.SearchItemAdapter;
import com.sdaemon.oakstudiotv.adapter.SearchItemsAdapter;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.ContentDTO;
import com.sdaemon.oakstudiotv.model.ContentInfo;
import com.sdaemon.oakstudiotv.model.SearchData;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.DialogUtils;
import com.sdaemon.oakstudiotv.utils.Methods;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchItemActivity extends AppCompatActivity implements SearchItemsAdapter.SearchAdapterListener, PaginationAdapterCallback {
//    http://velmm.com/pagination-with-recyclerview-in-android/
    androidx.appcompat.widget.SearchView searchView;
    RecyclerView recyclerView;
    androidx.swiperefreshlayout.widget.SwipeRefreshLayout swipeRefreshLayout;
    private SearchItemAdapter searchItemAdapter;
    TextView txtError;
    Button btnRetry;
    AppSession appSession;
    private int userid;
    private int itemcount=0;
    private int count=10;
    PaginationScrollListener paginationScrollListener;

    private SearchItemsAdapter searchItemsAdapter;

    ImageView iv_back,ivvoice,ivsearch;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    private List searchDataList = new ArrayList<>();
    private ArrayList<ContentInfo> getcontentInfos = new ArrayList<>();
    private ProgressDialog progressDialog;
    ProgressBar progressBar;
    SearchData data;
    LinearLayout errorLayout;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final int PAGE_START = 1;
    private int TOTAL_PAGES = 3;
    private int lastid=0;

    private int currentPage = PAGE_START;
    ContentDTO.ContentDetail contentDetails;
    String query1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);

        recyclerView=findViewById(R.id.search_recycleview);
        searchView=(androidx.appcompat.widget.SearchView)findViewById(R.id.searchview);
        swipeRefreshLayout=(androidx.swiperefreshlayout.widget.SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

//        swipeRefresh.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) SearchItemActivity.this);
        progressBar = findViewById(R.id.main_progress);
        errorLayout = findViewById(R.id.error_layout);
        txtError = findViewById(R.id.error_txt_cause);
        btnRetry = findViewById(R.id.error_btn_retry);


        SearchManager searchManager= (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconifiedByDefault(true);
//        searchView.setOnQueryTextListener(searchView);
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchItemsAdapter = new SearchItemsAdapter(SearchItemActivity.this,this);


        try {
            appSession=AppSession.getInstance(this);
            if (appSession!=null) {
                userid = appSession.getUserDTO().getResult().getCustomerId();
                Log.i("User Id:", "" + userid);
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);





//        gridLayoutManager=new GridLayoutManager(this,2);
//        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());


//
//        linearLayoutManager  = new LinearLayoutManager(SearchItemActivity.this, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

        initialize();
//        getSearchData(query);
//        getSearchData();

//        SearchDataPrepare();


//        SnapHelper snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);
//        searchItemAdapter = new SearchItemAdapter(searchDataList,SearchItemActivity.this,SearchItemActivity.this);

//        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(searchItemAdapter);
        recyclerView.setAdapter(searchItemsAdapter);





        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("Search Again",""+query);

                query1=query;
//                hideProgress();
//                if (query==data.getName()){
                getSearchData();


                if (searchItemsAdapter.getItemCount()==0){
//                    showDialoge(SearchItemActivity.this, "      Sorry !!!", "We couldn't find any exact matches for your search.");

//                    showDialoge(SearchItemActivity.this, "      Sorry !!!", "Looks like something has gone wrong.Do you want to continue to search");
                    Toast.makeText(SearchItemActivity.this, "empty", Toast.LENGTH_LONG).show();}
                else{
//                    hideProgress();
                    Toast.makeText(SearchItemActivity.this, "value", Toast.LENGTH_LONG).show();}

//                    searchItemAdapter.getFilter().filter(query);
//                }
//                else {
//                    Toast.makeText(SearchItemActivity.this, "No Match found",Toast.LENGTH_LONG).show();
//                }

                    return false;
    }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("Search New Text",""+newText);

                //  searchItemAdapter.getFilter().filter(newText);
                if (newText.length() == 0){
                    Log.i("Search New Text...",""+newText);
//                    hideProgress();
                    query1="";
//                if (query==data.getName()){
                    Intent i = new Intent(SearchItemActivity.this, SearchItemActivity.class);  //your class
                    startActivity(i);
                    finish();

//                    searchView.setQuery("",false);
//                    getSearchData();
//                    loadNextPage();
//                    return false;
                }


                return false;
          }
    });
//=--------------------------------------------------------
//        paginationScrollListener=new PaginationScrollListener(staggeredGridLayoutManager) {
//            @Override
//            protected void loadMoreItems() {
//                Log.i("Recycleview load",""+currentPage);
//                isLoading = true;
//                currentPage += 5;
//                Log.i("Pagination",""+currentPage);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        loadNextPage();
//                        Log.d("TAG", "run: ");
//                    }
//                }, 1000);
//            }
//
//            @Override
//            public int getTotalPageCount() {
//                return TOTAL_PAGES;
//            }
//
//            @Override
//            public boolean isLastPage() {
//                return isLastPage;
//            }
//
//            @Override
//            public boolean isLoading() {
//                return isLoading;
//            }
//        };
//
//        recyclerView.addOnScrollListener(paginationScrollListener);

//-----------------------------------------------------

//        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                isLoading = true;
//                currentPage += 5;
//                Log.i("Pagination",""+currentPage);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        loadNextPage();
//                        Log.d("TAG", "run: ");
//                    }
//                }, 1000);
//            }
//
//            @Override
//            public int getTotalPageCount() {
//                return TOTAL_PAGES;
//            }
//        });

//---------------------------------------------------------------------------------

        recyclerView.addOnScrollListener(new PaginationScrollListener(staggeredGridLayoutManager) {
            @Override
            protected void loadMoreItems() {

                Log.i("Recycleview load",""+currentPage);
                isLoading = true;
                currentPage += 5;
                Log.i("Pagination",""+currentPage);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                        Log.d("LoadItems", "run: ");
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

        });

//--------------------------------------------------

//        if (Methods.isOnline(SearchItemActivity.this)) {
            query1="";
            getSearchData();
//        }
        btnRetry.setOnClickListener(view -> getSearchData());
        swipeRefreshLayout.setOnRefreshListener(this::doRefresh);

//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
//                    swipeRefreshLayout.setRefreshing(false);  // This hides the spinner
//                }
//            }
//        });


    }

    private void initialize(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        iv_back=(ImageView) findViewById(R.id.iv_tabback);
        ivvoice=(ImageView) findViewById(R.id.iv_voicesearching);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        ivvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                searchView=(androidx.appcompat.widget.SearchView)findViewById(R.id.searchview);
//                searchView.setSubmitButtonEnabled(true);
//                searchView.setIconified(false);
//                promptSpeechInput();
            }
        });
    }



    public void getSearchData(){

//        showProgress();
        hideErrorView();

        Call<ContentDTO> call= RetroClient.sdaemonApi().getSearchContent(userid,query1,0,5);
//        Call<ContentDTO> call= RetroClient.sdaemonApi().getsearchdata("$2a$10$0aU.MrwoZHUOA   adZPnedG.nKrUWy6HJ6nqpMezKR2MHMqAdisL.6.");
        Log.e(getClass().getName(), "===" + call.request().url());

        call.enqueue(new Callback<ContentDTO>() {
            @Override
            public void onResponse(Call<ContentDTO> call, Response<ContentDTO> response) {
//                hideProgress();
                hideErrorView();

                if (response.isSuccessful()) {
//                    hideErrorView();
                    Log.i("Search Response",""+response.message());
                    ContentDTO contentDTO = response.body();
                    contentDetails = contentDTO.getContentDetail();
                    getcontentInfos = (ArrayList<ContentInfo>) response.body().getContentDetail().getContentInfos();
                    if (getcontentInfos != null && !getcontentInfos.isEmpty()) {
                        // Got data. Send it to adapter
                        Log.i("Content Count ",""+getcontentInfos.size());




                        for (int i=0;i<getcontentInfos.size();i++){
                            itemcount++;
                            if (itemcount==5){
                            lastid=getcontentInfos.get(i).getContentID();
                            Log.i("Last Content id",""+lastid);
                                itemcount=0;
                            }

                        }

                        List<ContentInfo> results = fetchResults(response);
                        progressBar.setVisibility(View.GONE);

                        searchItemsAdapter.clear();
                        searchItemsAdapter.notifyDataSetChanged();
                        if (query1 == "") {
                            searchItemsAdapter.addAll(results);


                        }
//                        else if (){
//
//                        }
                        else {

                        /*    for (int i=0;i<results.size();i++){
                                if (query1 == results.get(i).getContentTitle()){*/
                            searchItemsAdapter.clear();
                            searchItemsAdapter.addAll(results);
                            searchItemsAdapter.notifyDataSetChanged();
//                            hideProgress();


                            // }
                          //  }

                        }

//                        if (currentPage != TOTAL_PAGES && results.size() == 4)
//                            searchItemsAdapter.addLoadingFooter();
//                        else
//                            isLastPage = true;



                        if (currentPage <= TOTAL_PAGES){
                            searchItemsAdapter.addLoadingFooter();
//                            hideProgress();
                        }
                        else{
//                            searchItemsAdapter.addLoadingFooter();
                            isLastPage = true;
                        }



                    }
                }
//                    searchItemAdapter = new SearchItemAdapter(contentDetails,SearchItemActivity.this,SearchItemActivity.this);
//        recyclerView.setAdapter(searchItemAdapter);
//                    Toast.makeText(getApplicationContext(),"Success" ,Toast.LENGTH_LONG).show();


                else if (response.code() == 404) {
//                    showDialoge(SearchItemActivity.this, "      Sorry !!!",  response.raw().message().toString());
                    showDialoge(SearchItemActivity.this, response.raw().message().toString(), getResources().getString(R.string.not_match));
                   // showdialog();
                    Toast.makeText(SearchItemActivity.this, response.raw().message().toString(), Toast.LENGTH_SHORT).show();
                    Log.i(getClass().getName(), "=========RESPONSE: 404 ");
                    searchItemsAdapter.clear();
                    List<ContentInfo> results =new ArrayList<>();
                    searchItemsAdapter.addAll(results);
                    searchItemsAdapter.notifyDataSetChanged();
                }

                //  tvRetry.setVisibility(View.VISIBLE);
            }





            @Override
            public void onFailure(Call<ContentDTO> call, Throwable t) {
//                hideProgress();
                Toast.makeText(SearchItemActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                showErrorView(t);

            }
        });
    }


    private List<ContentInfo> fetchResults(Response<ContentDTO> response) {

        ContentDTO getlist = response.body();
        return getlist.getContentDetail().getContentInfos();
//        ContentDTO getlist = response.body();
//        return getlist.getContentDetail().getContentInfos();
    }


    public void loadNextPage(){
        Log.i("Last Content first t",""+lastid);

        Call<ContentDTO> call= RetroClient.sdaemonApi().getSearchContent(userid,query1,lastid,count);
//        Call<ContentDTO> call= RetroClient.sdaemonApi().getsearchdata("$2a$10$0aU.MrwoZHUOAadZPnedG.nKrUWy6HJ6nqpMezKR2MHMqAdisL.6.");

        Log.e(getClass().getName(), "===" + call.request().url());

        call.enqueue(new Callback<ContentDTO>() {
            @Override
            public void onResponse(Call<ContentDTO> call, Response<ContentDTO> response) {
//                hideProgress();

//                searchItemsAdapter.removeLoadingFooter();
                if (response.isSuccessful()){
                    searchItemsAdapter.removeLoadingFooter();
                    Log.i("SearchItemActivity","Remove Item");
//                    getcontentInfos = (ArrayList<ContentInfo>) response.body().getContentDetail().getContentInfos();
//                    if (getcontentInfos != null && !getcontentInfos.isEmpty()) {
//                        searchItemsAdapter.removeLoadingFooter();
                        isLoading = false;
                        // Got data. Send it to adapter
                        List<ContentInfo> results = fetchResults(response);
                    Log.i("Content Count ",""+results.size());


                    for (int i=0;i<results.size();i++){
                        itemcount++;
                        if (itemcount==5){
                            lastid=results.get(i).getContentID();
                            Log.i("Last Content id....",""+lastid);
                            itemcount=0;
                        }

                    }
                        searchItemsAdapter.addAll(results);

                        if (currentPage != TOTAL_PAGES)
                            searchItemsAdapter.addLoadingFooter();
                        else
                            isLastPage = true;



//                        if (currentPage != TOTAL_PAGES && results.size() == 4)
//                            searchItemsAdapter.addLoadingFooter();
//                        else
//                            isLastPage = true;
//                    }
                }else {


//                    Toast.makeText(getApplicationContext(),"Error " +response.message(),Toast.LENGTH_LONG).show();
                }
//                    searchItemAdapter = new SearchItemAdapter(contentDetails,SearchItemActivity.this,SearchItemActivity.this);
//        recyclerView.setAdapter(searchItemAdapter);
//                Toast.makeText(getApplicationContext(),"Success" +getcontentInfos.get(0).getCategoryDescription(),Toast.LENGTH_LONG).show();
            }


            @Override
            public void onFailure(Call<ContentDTO> call, Throwable t) {
//                hideProgress();
                searchItemsAdapter.showRetry(true, fetchErrorMessage(t));
                Toast.makeText(getApplicationContext(),"Error :" +t.getMessage()+  ""+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }



    private void SearchDataPrepare() {

         data = new SearchData("PIRATES OF CARABIAN", 25,R.drawable.banner);
        searchDataList.add(data);
        data = new SearchData("EXPANDABLES 2", 25,R.drawable.view);
        searchDataList.add(data);
        data = new SearchData("ANT-MAN", 20,R.drawable.view1);
        searchDataList.add(data);
        data = new SearchData("JUSTICE LEAGUE", 28,R.drawable.view2);
        searchDataList.add(data);
        data = new SearchData("BLACK-PATNER", 15,R.drawable.view3);
        searchDataList.add(data);
        data = new SearchData("MISSION-IMPOSSIBLE", 19,R.drawable.view4);
        searchDataList.add(data);
        data = new SearchData("ANT-MAN", 52,R.drawable.view1);
        searchDataList.add(data);
        data = new SearchData("JUSTICE LEAGUE", 30,R.drawable.view2);
        searchDataList.add(data);
        data = new SearchData("BLACK-PATNER", 28,R.drawable.view3);
        searchDataList.add(data);
        data = new SearchData("Banner Movie", 30,R.drawable.banner);
        searchDataList.add(data);
//        Kabir Singh

    }


//    data = new ArrayList<AndroidVersion>(Arrays.asList(jsonResponse.getAndroid()));
//            adapter.swapData(data);
    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void showProgress() {
        if (progressDialog == null)
            progressDialog = DialogUtils.createProgressDialog(SearchItemActivity.this);
        if (!progressDialog.isShowing())
            progressDialog.show();
    }



    private void showErrorView(Throwable throwable) {

        if ( errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            txtError.setText(fetchErrorMessage(throwable));
        }
    }
    private void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private String fetchErrorMessage(Throwable throwable) {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!Methods.isOnline(SearchItemActivity.this)) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof TimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }



    public void showDialoge(Context context, String title, String msg) {
        String searchAgain=getResources().getString(R.string.search_again);
        String searchCancel=getResources().getString(R.string.cancel_button);

        try {
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(context,R.style.dialoge);
            //            String centerNRed = "<div align:'center' ><span style='color:red' >"+title+"</span></div>";
//            builder.setTitle(Html.fromHtml(centerNRed))
            builder.setTitle(Html.fromHtml("<font color='#D93723'>"+title+"</font>"))
                    .setMessage(msg)
                    .setCancelable(false)
                    .setIcon(R.drawable.ic_error_black_24dp)


             .setPositiveButton(Html.fromHtml("<font color='#9AEB3D'>"+searchAgain+"</font>"), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            query1="";
//                            searchView.clearFocus();
                            searchView.setQuery("", false);
                            getSearchData();
                            dialog.dismiss();
                        }
                    })
            .setNegativeButton(Html.fromHtml("<font color='#9AEB3D'>"+searchCancel+"</font>"), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                    finish();
//                    System.exit(0);
                }
            }).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void doRefresh() {
        progressBar.setVisibility(View.VISIBLE);

        // TODO: Check if data is stale.
        //  Execute network request if cache is expired; otherwise do not update data.
        searchItemsAdapter.getItems().clear();
        searchItemsAdapter.notifyDataSetChanged();
//        Intent i = new Intent(SearchItemActivity.this, SearchItemActivity.class);  //your class
//        startActivity(i);
//        finish();

        query1="";
//                            searchView.clearFocus();
        searchView.setQuery("", false);
        getSearchData();

        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.menu_refresh).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                // Signal SwipeRefreshLayout to start the progress indicator
                swipeRefreshLayout.setRefreshing(true);
                doRefresh();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearchSelected(ContentInfo contentInfo) {
                Toast.makeText(getApplicationContext(), "Selected: " + contentInfo.getContentTitle() , Toast.LENGTH_LONG).show();

    }

    @Override
    public void retryPageLoad() {
        Log.i("retryPageLoad","crash");
        loadNextPage();
    }


    public void promptSpeechInput(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Search for a tv show,movie,genre etc.");

//        Speech prompt
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "speech_not_supported",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQ_CODE_SPEECH_INPUT:
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchView.setQuery(result.get(0),false);
                }
                break;

        }
    }


    //    https://jsonplaceholder.typicode.com/photos
}
