package com.mobile.app.activity.SubFragmentMenu;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobile.app.R;


public class SubPlates extends Fragment implements OnClickListener {

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

        img1 = (ImageView)getView().findViewById(R.id.img1);
        img2 = (ImageView)getView().findViewById(R.id.img2);
        img3 = (ImageView)getView().findViewById(R.id.img3);
        img1.setImageResource(R.drawable.shapes);
        img2.setImageResource(R.drawable.nation);
        img3.setImageResource(R.drawable.party);

        text1 = (TextView)getView().findViewById(R.id.text1);
        text2 = (TextView)getView().findViewById(R.id.text2);
        text3 = (TextView)getView().findViewById(R.id.text3);
        text1.setText(R.string.addplate);
        text2.setText(R.string.isselling);
        text3.setText(R.string.search);

        listview = (ListView) getView().findViewById(R.id.list);
//        listview.addHeaderView(add1);
//        listview.addHeaderView(add2);
//        listview.addHeaderView(add3);
        // TODO: 17/04/16 addlist
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add1:
                // TODO: 17/04/16
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
    }
}
