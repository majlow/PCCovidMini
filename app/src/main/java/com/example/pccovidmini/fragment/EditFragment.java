package com.example.pccovidmini.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditFragment extends Fragment {
    private View mView;
    MainActivity mainActivity = (MainActivity) getActivity();
    
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;
    DocumentReference documentReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView btn_save;
    private EditText etName, etDateOfBirth, etAddress, etPhoneNum, etNumOfVaccine;
    private String userID;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_edit, container, false);
        initUi();
        setUserInformation();
//        initListener();

        return mView;
    }
    private void initUi() {
        btn_save = mView.findViewById(R.id.tv_save);
        etName = mView.findViewById(R.id.et_name);
        etDateOfBirth = mView.findViewById(R.id.et_dateOfBirth);
        etAddress = mView.findViewById(R.id.et_address);
        etPhoneNum = mView.findViewById(R.id.et_phoneNum);
        etNumOfVaccine = mView.findViewById(R.id.et_numberVaccine);
    }

//    private void initListener() {
//        btn_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickUpdateProfile();
//            }
//        });
//    }
//
//    private void onClickUpdateProfile() {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if(user == null){
//            return;
//        }
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
//        userID = user.getUid();
//        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User userProfile = snapshot.getValue(User.class);
//                boolean isEdit = false;
//                String name = etName.getText().toString().trim();
//                String DoB = etDateOfBirth.getText().toString().trim();
//                String address = etAddress.getText().toString().trim();
//                String phoneNum = etPhoneNum.getText().toString().trim();
//                String numOfVaccine = etNumOfVaccine.getText().toString().trim();
//                if(userProfile != null){
//                    if(userProfile.fullName != name){
//                        userProfile.fullName = name;
//                        isEdit = true;
//                    }
//                    if(userProfile.dateOfBirth != DoB){
//                        userProfile.dateOfBirth = DoB;
//                        isEdit = true;
//                    }
//                    if(userProfile.address != address){
//                        userProfile.address = address;
//                        isEdit = true;
//                    }
//                    if(userProfile.phoneNum != phoneNum){
//                        userProfile.phoneNum = phoneNum;
//                        isEdit = true;
//                    }
//                    if(userProfile.numOfVaccine != numOfVaccine){
//                        userProfile.numOfVaccine = numOfVaccine;
//                        isEdit = true;
//                    }
//                    if(isEdit){
//                        iUpdateUser.updateUser(userProfile);
//                    }
//                }
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getActivity(), "something wrong happened!", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    private void setUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
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
                Toast.makeText(getActivity(), "something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showInfoUser(User userProfile){
        String fullName = userProfile.fullName;
        String DoB = userProfile.dateOfBirth;
        String address = userProfile.address;
        String phoneNum = userProfile.phoneNum;
        String numOfVaccine = userProfile.numOfVaccine;

        etName.setText(fullName);
        etDateOfBirth.setText(DoB);
        etAddress.setText(address);
        etPhoneNum.setText(phoneNum);
        etNumOfVaccine.setText(numOfVaccine);
    }
}
