package com.example.mp3app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mp3app.R;


public class Fragment_Profile_Group extends Fragment {
    View view;
    ImageView imgTruong, imgLoc, imgMain;
    TextView txtTruong, txtLoc, txtPosition1, txtPosition2, txtMain;
    Button btnTruong, btnLoc;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_group, container, false);
        imgMain = view.findViewById(R.id.imgMain);
        imgTruong = view.findViewById(R.id.imgDien);
        imgLoc = view.findViewById(R.id.imgPhong);
        txtMain = view.findViewById(R.id.txtMain);
        txtTruong = view.findViewById(R.id.txtDien);
        txtLoc = view.findViewById(R.id.txtPhong);
        txtPosition1 = view.findViewById(R.id.txtPosition1);
        txtPosition2 = view.findViewById(R.id.txtPosition2);
        btnTruong = view.findViewById(R.id.btnTruong);
        btnTruong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Đang làm tiếp....", Toast.LENGTH_LONG).show();
            }
        });
        btnLoc = view.findViewById(R.id.btnLoc);


        return view;
    }
}
