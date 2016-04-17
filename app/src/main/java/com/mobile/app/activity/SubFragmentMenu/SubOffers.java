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


public class SubOffers extends Fragment {

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
        // TODO: 17/04/16
        TextView tv = new TextView(getActivity());
        tv.setTextSize(25);
        tv.setBackgroundColor(Color.parseColor("#FFA07A"));
        tv.setText("Offers");
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

}
