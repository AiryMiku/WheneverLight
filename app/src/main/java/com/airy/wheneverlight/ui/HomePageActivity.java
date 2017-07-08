package com.airy.wheneverlight.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.airy.wheneverlight.R;
import com.airy.wheneverlight.fragment.HomePageFragment;
import com.airy.wheneverlight.setting.SettingActivity;

public class HomePageActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initView();

    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_page_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.refresh:
                Toast.makeText(this, "刷新", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    public void initView(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.home_page_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        //

        mNavigationView = (NavigationView) findViewById(R.id.nav);
        mNavigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home_page_drawer:
                    replaceFragment(new HomePageFragment());
                    break;
                case R.id.comment_drawer:
                    //
                    break;
                case R.id.at_weibo_drawer:
                    //
                    break;
                case R.id.at_comment_drawer:
                    //
                    break;
                case R.id.setting_drawer:
                    startActivity(new Intent(HomePageActivity.this, SettingActivity.class));
                    break;
            }
            item.setChecked(true);
            mDrawerLayout.closeDrawers();
            return true;
        });
        
        replaceFragment(new HomePageFragment());
        
    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment,fragment).commit();
    }


}
