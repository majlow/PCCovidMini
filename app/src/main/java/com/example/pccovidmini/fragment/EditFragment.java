package com.example.pccovidmini.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pccovidmini.HealthDeclarationActivity;
import com.example.pccovidmini.R;

public class EditFragment extends Fragment {

    Button btnKBYT;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        btnKBYT = view.findViewById(R.id.btn_kbyt);
        btnKBYT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HealthDeclarationActivity.class);
                startActivity(intent);
            }
        });
        return view;



    }

}
