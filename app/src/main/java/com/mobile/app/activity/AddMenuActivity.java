package com.mobile.app.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.Toast;

import com.mobile.app.DatabaseUtil;
import com.mobile.app.FileUtil;
import com.mobile.app.R;
import com.mobile.app.SelectPicPopupWindow;
import com.mobile.app.Storage;

import java.io.File;

public class AddMenuActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;

    private ImageView backButton;
    private EditText menuname;
    private EditText price;
    private EditText description;
    private ImageView addMenuImg;
    private Button savebutton;

    private SelectPicPopupWindow menuWindow;
    private String urlpath;

/*


 */
    private static final int REQUESTCODE_PICK = 0;
    private static final int REQUESTCODE_TAKE = 1;
    private static final int REQUESTCODE_CUTTING = 2;

    private static final String IMAGE_FILE_NAME = "menuImg.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        mContext = AddMenuActivity.this;
        initViews();
    }

    private void initViews() {
        backButton = (ImageView) findViewById(R.id.iv_back);

        menuname = (EditText) findViewById(R.id.ed_menuname);
        price = (EditText) findViewById(R.id.ed_myprice);
        description = (EditText) findViewById(R.id.ed_description);
        addMenuImg = (ImageView) findViewById(R.id.iv_addmenu);
        addMenuImg.setOnClickListener(this);
        backButton.setOnClickListener(this);

        savebutton = (Button) findViewById(R.id.mymenubutton);
        savebutton.setOnClickListener(this);
    }

    //popup listener
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
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

    private void storage() {
        Storage.saveString(this, "nemuname", menuname.getText().toString().trim());
        Storage.saveString(this, "price", price.getText().toString().trim());
        Storage.saveString(this, "description", description.getText().toString().trim());
        Toast.makeText(AddMenuActivity.this, "Saved successfully",
                Toast.LENGTH_LONG).show();
        String mname = menuname.getText().toString().trim();
        String mprice = price.getText().toString().trim();
        String mdescription = description.getText().toString().trim();
        if (mname.equals(null) || mname.equals("")) {
            menuname.requestFocus();
            menuname.setSelected(true);
            menuname.setError("please entry a menu");
        }
        if (mprice.equals(null) || mprice.equals("")) {
            price.requestFocus();
            price.setSelected(true);
            price.setError("please entry the price");
        }
        if (mdescription.equals(null) || mdescription.equals("")) {
            description.requestFocus();
            description.setSelected(true);
            description.setError("please entry description");
        }
        DatabaseUtil dbUtil = new DatabaseUtil(this);
        dbUtil.open();
        dbUtil.createMENU(mname, mprice, mdescription, urlpath);
        dbUtil.close();
    }

    @Override
    public void onClick(View v) {
        switch ((v.getId())) {
            case R.id.iv_addmenu:
                menuWindow = new SelectPicPopupWindow(mContext, itemsOnClick);
                menuWindow.showAtLocation(findViewById(R.id.AddMenuLayout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.mymenubutton:
                storage();
                break;
            case R.id.iv_back:

                Intent intent = new Intent();
                intent.setClass(AddMenuActivity.this, FragmentMenu.class);
                intent.putExtra("result", "ok");
                AddMenuActivity.this.setResult(RESULT_OK, intent);

                AddMenuActivity.this.finish();
                break;
            default:
                break;
        }
    }

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

    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            // SDCard path
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(null, photo);

            urlpath = FileUtil.saveFile(mContext, IMAGE_FILE_NAME, photo);
            addMenuImg.setImageDrawable(drawable);

            Storage.saveString(this, "menuImgurl", urlpath);
        }
    }
}