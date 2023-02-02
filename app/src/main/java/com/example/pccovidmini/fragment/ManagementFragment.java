package com.example.pccovidmini.fragment;

import static com.example.pccovidmini.MainActivity.MY_REQUEST_CODE;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.pccovidmini.MainActivity;
import com.example.pccovidmini.R;
import com.example.pccovidmini.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManagementFragment extends Fragment {

    private View mView;
    private ImageView imgAvatar;
    private TextView tvName, tvSocialInsurance, tvDateOfBirth,
            tvNationalID, tvPhoneNum, tvAddress, tvNumOfVaccine;
    private String userID;

    DatabaseReference reference;
    Bitmap bitmap;
    Uri filePath;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_personal_management, container, false);
        initUi();
        setUserInformation();
        initListener();
        return mView;
    }

//    click vao anh
    private void initListener() {
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });
    }

    private void onClickRequestPermission() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if(mainActivity == null){
            return;
        }

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
//            tu android tro xuong k can requestPermission
            mainActivity.openGallery();
            return;
        }
        if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
//            user da cho phep dung quyen truy cap thu vien
            mainActivity.openGallery();
        }else {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permissions, MY_REQUEST_CODE);
        }
    }

    private void initUi(){
        tvName = mView.findViewById(R.id.tv_name_management);
        tvSocialInsurance = mView.findViewById(R.id.tv_bhyt_management);
        tvDateOfBirth = mView.findViewById(R.id.tv_dob_management);
        tvNationalID = mView.findViewById(R.id.tv_cccd_management);
        tvPhoneNum = mView.findViewById(R.id.tv_phone_management);
        tvAddress = mView.findViewById(R.id.tv_address_management);
        tvNumOfVaccine = mView.findViewById(R.id.tv_numOfVaccine_management);
        imgAvatar = mView.findViewById(R.id.img_ava);

    }

    public void setUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        Glide.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.ic_baseline_person_24).into(imgAvatar);
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    showInfoUser(userProfile);
//                    Glide.with(getActivity()).load(photoUrl).error(R.drawable.ic_baseline_person_24).into(imgAvatar);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void showInfoUser(User userProfile){
        String fullName = userProfile.fullName;
        String socialInsurance = userProfile.socialInsurance;
        String DoB = userProfile.dateOfBirth;
        String NationalID = userProfile.nationalID;
        String phoneNum = userProfile.phoneNum;
        String address = userProfile.address;
        String numOfVaccine = userProfile.numOfVaccine;
//                    Uri photoUrl = user.getPhotoUrl();

        tvName.setText(fullName);
        tvSocialInsurance.setText(socialInsurance);
        tvDateOfBirth.setText(DoB);
        tvNationalID.setText(NationalID);
        tvPhoneNum.setText(phoneNum);
        tvAddress.setText(address);
        tvNumOfVaccine.setText(numOfVaccine);
    }

    public void setBitmapImageView(Bitmap bitmapImageView ){
        imgAvatar.setImageBitmap(bitmapImageView);
    }
}
