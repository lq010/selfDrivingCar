package com.mobile.app.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.mobile.app.R;
import com.mobile.app.Storage;

import java.io.File;

/**
 * Created by lq on 10/04/16.
 */
public class FragmentProfile  extends Fragment {

    private Context mContext;
    private TextView tv_username;
    private TextView tv_userId;
    private TextView tv_wallet;
    private TextView tv_setting;

    private ImageView iv_userPhoto;
    private ImageView iv_Wallte;
    private ImageView iv_Setting;

    private String username;
    private String userId = "0";
    private String userPhoto = "";
    String urlpath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();

    }
    public void initViews(){
        //  userPhoto = new LoadUserAvatar(getActivity(), "/sdcard/restaurant/");
        RelativeLayout re_myinfo = (RelativeLayout) getView().findViewById(
                R.id.re_myInfo);
        re_myinfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(),
                        UserInfoActivity.class),1);
            }

        });
        RelativeLayout re_setting = (RelativeLayout) getView().findViewById(
                R.id.re_setting);
        re_setting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // startActivity(new Intent(getActivity(), SettingActivity.class));// TODO: 13/04/16 change to SettingActivity
            }

        });

//            username = LocalUserInfo.getInstance(getActivity()).getUserInfo("username");
//            userId = LocalUserInfo.getInstance(getActivity()).getUserInfo("userId");
//            userPhoto = LocalUserInfo.getInstance(getActivity()).getUserInfo("userPhoto");

        iv_userPhoto = (ImageView) re_myinfo.findViewById(R.id.profile_photo);

        try {
            urlpath = Storage.getString(this.getActivity(), "url");
        } catch (Exception e) {
            Log.e("Storage",e.toString());
        }
        if (urlpath != null) {
            File f = new File(urlpath);
            if (f.exists()) {
                Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
                iv_userPhoto.setImageBitmap(bmp);
            }

            tv_username = (TextView) re_myinfo.findViewById(R.id.tv_name);
            tv_userId = (TextView) re_myinfo.findViewById(R.id.text_userId);

            try{
                username = Storage.getString(this.getActivity(),"username");
            }catch(Exception e){
                Log.e("Storage",e.toString());
            }

            if(username!=null&&username!="")
                tv_username.setText(username);
            else
                tv_username.setText("username");

            try{
                userId = Storage.getString(this.getActivity(),"userid");
            }catch(Exception e){
                Log.e("Storage",e.toString());
            }
            if (userId.equals("0")) {
                tv_userId.setText("ID: ****");
            } else {
                tv_userId.setText("ID: " + userId);
            }
//            showUserAvatar(iv_userPhoto, userPhoto);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            if(resultCode==-1){
                initViews();
            }
        }
    }
}

