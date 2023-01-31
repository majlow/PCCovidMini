package com.example.pccovidmini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.pccovidmini.fragment.EditFragment;
import com.example.pccovidmini.fragment.FindF0Fragment;
import com.example.pccovidmini.fragment.ManagementFragment;
import com.example.pccovidmini.fragment.QrFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Context context;

    private DrawerLayout mDrawerLayout;
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    private ViewPagerAdapter mViewPagerAdapter;
    private static final int FRAGMENT_MANAGEMENT = 0;
    private static final int FRAGMENT_FIND_F0 = 1;
    private static final int FRAGMENT_EDIT = 2;
    private static final int FRAGMENT_QR = 3;

    private int currentFragment = FRAGMENT_MANAGEMENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager2 = findViewById(R.id.view_pager_2);
        mViewPagerAdapter = new ViewPagerAdapter(this);
        mViewPager2.setAdapter(mViewPagerAdapter);

//        set Tab
        new TabLayoutMediator(mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(getString(R.string.nav_management));
                        break;
                    case 1:
                        tab.setText(getString(R.string.nav_find_f0));
                        break;
                    case 2:
                        tab.setText(getString(R.string.nav_edit));
                        break;
                    case 3:
                        tab.setText(getString(R.string.nav_qr_code));
                        break;
                }
            }
        }).attach();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

//        set Fragment when open app
        navigationView.getMenu().findItem(R.id.nav_management).setChecked(true);

//        Lắng nghe sự kiện chuyển page của viewPager2 để xét setItemSelected của navigation_drawer
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        currentFragment = FRAGMENT_MANAGEMENT;
                        navigationView.getMenu().findItem(R.id.nav_management).setChecked(true);
                        break;
                    case 1:
                        currentFragment = FRAGMENT_FIND_F0;
                        navigationView.getMenu().findItem(R.id.nav_find_f0).setChecked(true);
                        break;
                    case 2:
                        currentFragment = FRAGMENT_EDIT;
                        navigationView.getMenu().findItem(R.id.nav_edit).setChecked(true);
                        break;
                    case 3:
                        currentFragment = FRAGMENT_QR;
                        navigationView.getMenu().findItem(R.id.nav_qr).setChecked(true);
                        break;
                }
            }
        });
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_management) {
            if (currentFragment != FRAGMENT_MANAGEMENT) {
                mViewPager2.setCurrentItem(0);
                currentFragment = FRAGMENT_MANAGEMENT;
            }
        } else if (id == R.id.nav_find_f0) {
            if (currentFragment != FRAGMENT_FIND_F0) {
                mViewPager2.setCurrentItem(1);
                currentFragment = FRAGMENT_FIND_F0;
            }
        } else if (id == R.id.nav_edit) {
            if (currentFragment != FRAGMENT_EDIT) {
                mViewPager2.setCurrentItem(2);
                currentFragment = FRAGMENT_EDIT;
            }
        } else if (id == R.id.nav_qr) {
            if (currentFragment != FRAGMENT_QR) {
                mViewPager2.setCurrentItem(3);
                currentFragment = FRAGMENT_QR;
            }
        }
        else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            // start Main Activity
            startActivity(intent);
        }



//        Close Navigation when click item
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //        Close Navigation when click back device
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

