package com.example.pccovidmini;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pccovidmini.fragment.EditFragment;
import com.example.pccovidmini.fragment.FindF0Fragment;
import com.example.pccovidmini.fragment.ManagementFragment;
import com.example.pccovidmini.fragment.QrFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Context context;


    public static final int MY_REQUEST_CODE = 10;

    private DrawerLayout mDrawerLayout;
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    private ViewPagerAdapter mViewPagerAdapter;
    private static final int FRAGMENT_MANAGEMENT = 0;
    private static final int FRAGMENT_FIND_F0 = 1;
    private static final int FRAGMENT_EDIT = 2;
    private static final int FRAGMENT_QR = 3;

    private int currentFragment = FRAGMENT_MANAGEMENT;
    private final ManagementFragment mManagementFragment = new ManagementFragment();

    private NavigationView mNavigationView;
    private ImageView imgAvatar;
    private TextView tvName, tvNationalID;
    private String userID;

    final private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
//        sau khi chon anh tu gallery
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK){
                Intent intent = result.getData();
                if(intent == null){
                    return;
                }
                Uri uri = intent.getData();
                try {
//                    bitmap cua anh chon tu gallery
//                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    mManagementFragment.setBitmapImageView(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager2 = findViewById(R.id.view_pager_2);
        mViewPagerAdapter = new ViewPagerAdapter(this);
        mViewPager2.setAdapter(mViewPagerAdapter);

        initUi();

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

        mNavigationView.setNavigationItemSelectedListener(this);

//        set Fragment when open app
        mNavigationView.getMenu().findItem(R.id.nav_management).setChecked(true);

        setUserInformation();

//        Lắng nghe sự kiện chuyển page của viewPager2 để xét setItemSelected của navigation_drawer
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        currentFragment = FRAGMENT_MANAGEMENT;
                        mNavigationView.getMenu().findItem(R.id.nav_management).setChecked(true);
                        break;
                    case 1:
                        currentFragment = FRAGMENT_FIND_F0;
                        mNavigationView.getMenu().findItem(R.id.nav_find_f0).setChecked(true);
                        break;
                    case 2:
                        currentFragment = FRAGMENT_EDIT;
                        mNavigationView.getMenu().findItem(R.id.nav_edit).setChecked(true);
                        break;
                    case 3:
                        currentFragment = FRAGMENT_QR;
                        mNavigationView.getMenu().findItem(R.id.nav_qr).setChecked(true);
                        break;
                }
            }
        });
    }

    private void initUi(){
        mNavigationView = findViewById(R.id.navigation_view);
        imgAvatar = mNavigationView.getHeaderView(0).findViewById(R.id.img_ava);
        tvName = mNavigationView.getHeaderView(0).findViewById(R.id.tv_name);
        tvNationalID = mNavigationView.getHeaderView(0).findViewById(R.id.tv_nationalID);

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
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            // start Main Activity
            startActivity(intent);
            finish();
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

    public void setUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        Uri photoUrl = user.getPhotoUrl();
        Glide.with(MainActivity.this).load(photoUrl).error(R.drawable.ic_baseline_person_24).into(imgAvatar);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    showInfoUser(userProfile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showInfoUser(User userProfile){
        String fullName = userProfile.fullName;
        String NationalID = userProfile.nationalID;
//                    Uri photoUrl = user.getPhotoUrl();

        tvName.setText(fullName);
        tvNationalID.setText(NationalID);
//                    Glide.with(MainActivity.this).load(photoUrl).error(R.drawable.ic_baseline_person_24).into(imgAvatar);
    }

//    bat dc Action user cho phep hay tu choi Permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }

//    mo gallery chon anh
    public  void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }
}

