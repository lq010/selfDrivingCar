package com.mobile.app.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.mobile.app.FileUtil;
import com.mobile.app.R;
import com.mobile.app.SelectPicPopupWindow;
import com.mobile.app.Storage;

import java.io.File;

public class UserInfoActivity extends AppCompatActivity implements OnClickListener{

    private Context mContext;
    private RelativeLayout re_avatar;
    private RelativeLayout re_name;
    private RelativeLayout re_userid;

    private EditText myname;
    private TextView tv_userid;
    private ImageView myphpto;
    private Button saveButton;
    private ImageView backButton;

    private SelectPicPopupWindow menuWindow;

    private static final String IMAGE_FILE_NAME = "myPhoto.jpg";

    private String urlpath; //local path
    String mExternalDirectory;//sd card


    private static final int REQUESTCODE_PICK = 0;
    private static final int REQUESTCODE_TAKE = 1;
    private static final int REQUESTCODE_CUTTING = 2;

    private FileUtil fileUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        mContext = UserInfoActivity.this;
        initViews();
    }

    private void initViews() {
        backButton = (ImageView) findViewById(R.id.iv_back);

        myname = (EditText)findViewById(R.id.et_name);
        try {
            String myusername = Storage.getString(this, "username");
            myname.setText(myusername);
        }catch (Exception e){
            //myname.setText("username");
        }
        tv_userid = (TextView) findViewById(R.id.tv_myid);
        try {
            String userid = Storage.getString(this, "userid");
            if(userid=="" || userid == null){
                tv_userid.setText("******");
            }else
            tv_userid.setText(userid);

        }catch (Exception e){
            tv_userid.setText("******");
        }

//        //get sdcard path
//        mExternalDirectory = Environment.getExternalStorageDirectory()
//                .getAbsolutePath();
//        if (android.os.Build.DEVICE.contains("samsung")
//                || android.os.Build.MANUFACTURER.contains("samsung")) {
//            File f = new File(Environment.getExternalStorageDirectory()
//                    .getParent() + "/extSdCard" + "/myDirectory");
//            if (f.exists() && f.isDirectory()) {
//                mExternalDirectory = Environment.getExternalStorageDirectory()
//                        .getParent() + "/extSdCard";
//            } else {
//                f = new File(Environment.getExternalStorageDirectory()
//                        .getAbsolutePath() + "/external_sd" + "/myDirectory");
//                if (f.exists() && f.isDirectory()) {
//                    mExternalDirectory = Environment
//                            .getExternalStorageDirectory().getAbsolutePath()
//                            + "/external_sd";
//                }
//            }
//        }

        try{
            urlpath = Storage.getString(this,"url");
        }catch(Exception e) {
        }


        if(urlpath != null){
            File f = new File(urlpath);
            if(f.exists()){
                myphpto = (ImageView) findViewById(R.id.iv_userPhoto);
                Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
                myphpto.setImageBitmap(bmp);
            }
            else{
                int drawableId = mContext.getResources().getIdentifier("default_userphoto", "drawable", mContext.getPackageName());
                myphpto = (ImageView) findViewById(R.id.iv_userPhoto);
                myphpto.setImageResource(drawableId);
            }

        }else{
            myphpto = (ImageView) findViewById(R.id.iv_userPhoto);
            int drawableId = mContext.getResources().getIdentifier("default_userphoto", "drawable", mContext.getPackageName());
            myphpto.setImageResource(drawableId);
        }

        myphpto.setOnClickListener(this);
        saveButton = (Button) findViewById(R.id.mybutton);
        saveButton.setOnClickListener(this);
        backButton = (ImageView) findViewById(R.id.iv_back);
        backButton.setOnClickListener(this);
    }

    private void storage(){
        Storage.saveString(this,"username", myname.getText().toString().trim());
        Toast.makeText(UserInfoActivity.this, "Saved successfully",
                Toast.LENGTH_LONG).show();
    }
    //popup listener
    private OnClickListener itemsOnClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                // take photos
                case R.id.takePhotoBtn:
                    Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //
                    takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                    startActivityForResult(takeIntent, REQUESTCODE_TAKE);
                    break;
                // choose photos
                case R.id.pickPhotoBtn:
                    Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                    // image/jpeg 、 image/png...
                    pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(pickIntent, REQUESTCODE_PICK);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public void onClick(View v) {
        switch ((v.getId())){
            case R.id.iv_userPhoto:
                menuWindow = new SelectPicPopupWindow(mContext, itemsOnClick);
                menuWindow.showAtLocation(findViewById(R.id.userInfoLayout),
                        Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.mybutton:
                storage();
                break;
            case R.id.iv_back:
                //数据是使用Intent返回
                Intent intent = new Intent();
                intent.setClass(UserInfoActivity.this,FragmentProfile.class);
                //把返回数据存入Intent
                intent.putExtra("result", "ok");
                //设置返回数据
                UserInfoActivity.this.setResult(RESULT_OK, intent);
                //关闭Activity
                UserInfoActivity.this.finish();
                break;
            default:
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUESTCODE_PICK:// get pics from album
                try {
                    startPhotoZoom(data.getData());
                } catch (NullPointerException e) {
                    e.printStackTrace();//
                }
                break;
            case REQUESTCODE_TAKE:// take photos
                File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                startPhotoZoom(Uri.fromFile(temp));
                break;
            case REQUESTCODE_CUTTING:// get the pic
                if (data != null) {

                    setPicToView(data);

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * cut pic
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop=true
        intent.putExtra("crop", "true");
        // aspectX aspectY height width
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }


    /**
     * 保存裁剪之后的图片数据
     * @param picdata
     */
    private void setPicToView(Intent picdata)  {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            // SDCard path
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(null, photo);

            urlpath = FileUtil.saveFile(mContext,IMAGE_FILE_NAME, photo);
            myphpto.setImageDrawable(drawable);

            Storage.saveString(this,"url",urlpath);

//            SaveImage(photo);

        }
    }
}
