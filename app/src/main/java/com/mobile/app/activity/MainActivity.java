package com.mobile.app.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobile.app.R;

public class MainActivity extends AppCompatActivity implements OnClickListener{

   //bottom buttons
    private  ImageView[] imageButtons;
//    private RelativeLayout bot_menu;
//    private RelativeLayout bot_restaurant;
//    private RelativeLayout bot_order;
//    private RelativeLayout bot_user;

    //Fragment manager
    private FragmentManager fragmentManager;

    private Fragment[] fragments;
    private TextView[] textViews;
    private int index;
    private int currentTabIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        fragmentManager = getFragmentManager();

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void initViews() {
        imageButtons = new ImageView[4];
        imageButtons[0] =(ImageView) findViewById(R.id.img_menu);
        imageButtons[1] = (ImageView) findViewById(R.id.img_restaurant);
        imageButtons[2] = (ImageView) findViewById(R.id.img_order);
        imageButtons[3] = (ImageView) findViewById(R.id.img_order);

//        imageButtons[0].setOnClickListener(this);
//        imageButtons[1].setOnClickListener(this);
//        imageButtons[2].setOnClickListener(this);
//        imageButtons[3].setOnClickListener(this);

        //default fragment(menu)
        imageButtons[0].setSelected(true);

        textViews = new TextView[4];
        textViews[0] = (TextView) findViewById(R.id.text_menu);
        textViews[1] = (TextView) findViewById(R.id.text_restaurant);
        textViews[2] = (TextView) findViewById(R.id.text_order);
        textViews[3] = (TextView) findViewById(R.id.text_user);
        textViews[0].setTextColor(0xFF45C01A);


    }

    @Override
    public void onClick(View v) {
        switch ((v.getId())){
            case R.id.bot_menu:
                index = 0;
                break;
            case R.id.bot_restaurant:
                index = 1;
                break;
            case R.id.bot_order:
                index = 2;
                break;
            case R.id.bot_user:
                index = 3;
                break;
            default:
                break;
        }

        if(currentTabIndex != index){
            FragmentTransaction transaction = getFragmentManager()
                    .beginTransaction();
            transaction.hide(fragments[currentTabIndex]);
            if(!fragments[index].isAdded()){
                transaction.add(R.id.fragment_container,fragments[index]);
            }
            else
            transaction.show(fragments[index]).commit();
        }
        imageButtons[currentTabIndex].setSelected(false);
        imageButtons[index].setSelected(true);
        textViews[currentTabIndex].setTextColor(0xFF999999);
        textViews[index].setTextColor(0xFF45C01A);
        currentTabIndex = index;
    }











    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
