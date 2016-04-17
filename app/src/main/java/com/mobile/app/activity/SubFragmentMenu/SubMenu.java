package com.mobile.app.activity.SubFragmentMenu;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mobile.app.DatabaseUtil;
import com.mobile.app.ImageSimpleAdapter;
import com.mobile.app.R;
import com.mobile.app.Storage;
import com.mobile.app.activity.AddMenuActivity;
import com.mobile.app.activity.UserInfoActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


public class SubMenu extends Fragment implements OnClickListener {

    private RelativeLayout add1;
    private RelativeLayout add2;
    private RelativeLayout add3;

    private ImageView img1;
    private ImageView img2;
    private ImageView img3;

    private TextView text1;
    private TextView text2;
    private TextView text3;

    private ListView listview;

    private String menuImgPath ;
    private String menuname;
    private String price;
    private String description;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menus_list, container, false);
    }
    @Nullable
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();


    }

    private void initView() {
        add1 = (RelativeLayout)getView().findViewById(R.id.add1);
        add2 = (RelativeLayout)getView().findViewById(R.id.add2);
        add3 = (RelativeLayout)getView().findViewById(R.id.add3);
        add1.setOnClickListener(this);
        add2.setOnClickListener(this);
        add3.setOnClickListener(this);

        img1 = (ImageView)getView().findViewById(R.id.img1);
        img2 = (ImageView)getView().findViewById(R.id.img2);
        img3 = (ImageView)getView().findViewById(R.id.img3);
        img1.setImageResource(R.drawable.shapes);
        img2.setImageResource(R.drawable.nation);
        img3.setImageResource(R.drawable.party);

        text1 = (TextView)getView().findViewById(R.id.text1);
        text2 = (TextView)getView().findViewById(R.id.text2);
        text3 = (TextView)getView().findViewById(R.id.text3);
        text1.setText(R.string.addmenu);
        text2.setText(R.string.isselling);
        text3.setText(R.string.search);

        listview = (ListView) getView().findViewById(R.id.list);
        SimpleAdapter adapter = new ImageSimpleAdapter(this.getActivity(), getDatas() ,R.layout.activity_show_menu_list
                , new String[]{"menuImg","menu","price","description"},
                new int[]{R.id.iv_show_menu,R.id.show_menu,R.id.shoe_price,R.id.tv_showdescription});
//        View headerView = this.getActivity().getLayoutInflater().inflate()
//        this.listview.addHeaderView();
//        RelativeLayout relativeLayout = (RelativeLayout) getView().findViewById(R.id.add);
//        listview.addHeaderView(LayoutInflater.from(this.getActivity()).inflate(R.layout., null));
        listview.setAdapter(adapter);
        // TODO: 17/04/16  listView set OnClickListener
    }




//        listview.addHeaderView(add1);
//        listview.addHeaderView(add2);
//        listview.addHeaderView(add3);
        // TODO: 17/04/16 addlist


    private List<Map<String,Object>> getDatas(){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

        DatabaseUtil dbUtil = new DatabaseUtil(this.getActivity());
        dbUtil.open();
        Cursor cursor = dbUtil.fetchAllMenus();
        if(cursor != null){
            while(cursor.moveToNext()){
                String name = cursor.getString(1);
                String price = cursor.getString(2);
                String description = cursor.getString(3);
                String imgpath = cursor.getString(4);
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("menuImg",imgpath);
                map.put("menu",name);
                map.put("price",price);
                map.put("description",description);
                list.add(map);
            }
        }
        dbUtil.close();
        return list;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add1:
                startActivityForResult(new Intent(getActivity(),
                        AddMenuActivity.class),1);
                break;
            case R.id.add2:
                // TODO: 17/04/16
                break;
            case R.id.add3:
                // TODO: 17/04/16
                break;
            default:
                break;
        }
    } public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            if(resultCode==-1){
                initView();
            }
        }
    }


}
