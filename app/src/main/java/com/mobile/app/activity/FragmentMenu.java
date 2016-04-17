package com.mobile.app.activity;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.FrameLayout;
import android.widget.ImageView;

import android.widget.RelativeLayout;

import com.mobile.app.PagerSlidingTabStrip;
import com.mobile.app.R;
import com.mobile.app.activity.SubFragmentMenu.SubMenu;
import com.mobile.app.activity.SubFragmentMenu.SubOffers;
import com.mobile.app.activity.SubFragmentMenu.SubPlates;
import com.mobile.app.activity.SubFragmentMenu.SubDeals;


public class FragmentMenu extends Fragment implements OnClickListener {

    private RelativeLayout sub_menu;
    private RelativeLayout sub_plates;
    private RelativeLayout sub_deals;
    private RelativeLayout sub_offers;
    private ImageView mTabLine;
    private int mScreen1_4;
    
    private Fragment subMenu;
    private Fragment subPlates;
    private Fragment subDeals;
    private Fragment subOffers;
    private FrameLayout frameLayout;

    private PagerSlidingTabStrip tabs;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (null != bundle) {
            //
        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu, null);

        initView(view);
        initDate();
        setLinstener();
        return view;

    }

    private void setLinstener() {
        sub_menu.setOnClickListener(this);
        sub_plates.setOnClickListener(this);
        sub_deals.setOnClickListener(this);
        sub_offers.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sub_menu:
                setSubFragment(0);
                setmTabline(0);
                break;
            case R.id.sub_plates:
                setSubFragment(1);
                setmTabline(1);
                break;
            case R.id.sub_deals:
                setSubFragment(2);
                setmTabline(2);
                break;
            case R.id.sub_offers:
                setSubFragment(3);
                setmTabline(3);
                break;
            default:
                break;
        }
    }

    protected void initDate() {
        // Display display = getWindow().getWindowManager().getDefaultDisplay();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        mScreen1_4 = outMetrics.widthPixels / 4;
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mTabLine.getLayoutParams();
        lp.width = mScreen1_4;
        mTabLine.setLayoutParams(lp);

        
        setSubFragment(0);
        setmTabline(0);

    }

    private void setmTabline(int i) {
        RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) mTabLine
                .getLayoutParams();
        lp.leftMargin = i * mScreen1_4;
        mTabLine.setLayoutParams(lp);
    }

    private void setSubFragment(int i) {
        FragmentTransaction transaction =getFragmentManager().beginTransaction();

        if(0 == i ){
            subMenu = (subMenu == null ? new SubMenu():subMenu);
            transaction.replace(R.id.FramePager,subMenu);
            //	transaction.addToBackStack(null);
            transaction.commit();

        }else if(1 == i ){
            subPlates = (subPlates == null ? new SubPlates():subPlates);
            transaction.replace(R.id.FramePager,subPlates);
            //	transaction.addToBackStack(null);
            transaction.commit();
        }else if(2 == i ){
            subDeals = (subDeals == null ? new  SubDeals():subDeals);
            transaction.replace(R.id.FramePager,subDeals);
            //	transaction.addToBackStack(null);
            transaction.commit();
        }else if(3 == i ){
             subOffers= (subOffers == null ? new SubOffers():subOffers);
            transaction.replace(R.id.FramePager,subOffers);
            //	transaction.addToBackStack(null);
            transaction.commit();
        }

    }

    private void initView(View view) {
        sub_menu = (RelativeLayout) view.findViewById(R.id.sub_menu);
        sub_plates = (RelativeLayout) view.findViewById(R.id.sub_plates);
        sub_deals  = (RelativeLayout) view.findViewById(R.id.sub_deals);
        sub_offers =(RelativeLayout) view.findViewById(R.id.sub_offers);
        mTabLine = (ImageView) view.findViewById(R.id.imv_tabline);
        frameLayout = (FrameLayout) view.findViewById(R.id.FramePager);
    }





}

