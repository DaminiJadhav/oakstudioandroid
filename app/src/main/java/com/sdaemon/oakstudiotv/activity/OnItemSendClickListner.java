package com.sdaemon.oakstudiotv.activity;

import android.view.View;

public class OnItemSendClickListner implements View.OnClickListener {
    private  int position = 0;
    private OnClicksendCallback onClickCallback;
    private String type="";
    private String name="";
    private String jobposition="";
    private String message="";
    private String email="";
    private String phone="";
    private String file="";


    public OnItemSendClickListner(int position, OnClicksendCallback onClickCallback, String type,String name,String jobposition,String message,String email,String phone,String file){
        this.position = position;
        this.onClickCallback = onClickCallback;
        this.type = type;
        this.name=name;
        this.jobposition=jobposition;
        this.message=message;
        this.email=email;
        this.phone=phone;
        this.file=file;
    }
    @Override
    public void onClick(View view) {
        onClickCallback.onClicked(view,position,type,name,jobposition,message,email,phone,file);


    }
    public interface OnClicksendCallback{
        void onClicked(View view,int position,String type,String name,String jobposition,String message,String email,String phone,String file);
    }
}
