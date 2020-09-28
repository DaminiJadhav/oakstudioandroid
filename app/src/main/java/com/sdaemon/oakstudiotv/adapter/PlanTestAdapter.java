package com.sdaemon.oakstudiotv.adapter;

import android.content.Context;

import com.sdaemon.oakstudiotv.model.Datum;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.util.List;

public class PlanTestAdapter {
    private List<Datum> list;
        private Context context;
        private OnItemClickListner.OnClickCallback onClickCallback;

        public PlanTestAdapter(Context context,List<Datum> list,  OnItemClickListner.OnClickCallback onClickCallback){
                this.context=context;
                this.list=list;
                this.onClickCallback=onClickCallback;
        }
}
