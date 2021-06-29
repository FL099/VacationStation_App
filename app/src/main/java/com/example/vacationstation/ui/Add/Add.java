package com.example.vacationstation.ui.Add;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.vacationstation.MemoryItem;
import com.example.vacationstation.R;


public class    Add extends Fragment {

    public static Button add_btn;
    public static TextView add_name;
    public static TextView add_tags;
    public static TextView add_comment;
    public static TextView add_loc;
    public static TextView check;

    public Add() {
        // Required empty public constructor
    }


    public static Add newInstance(String param1, String param2) {
        Add fragment = new Add();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        add_btn = view.findViewById(R.id.add_btn);
        add_name = view.findViewById(R.id.add_name);
        add_tags = view.findViewById(R.id.add_tags);
        add_comment = view.findViewById(R.id.add_comment);
        add_loc = view.findViewById(R.id.add_loc);
        check = view.findViewById(R.id.check);


        add_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String[] temp;
                Double n1 = 0.0;
                Double n2 = 0.0;
                if (!add_loc.getText().toString().equals("")){
                    temp = add_loc.getText().toString().split(" ");
                    try {
                        n1 = Double.parseDouble(temp[0]);
                        if (!temp[1].equals("N,")){
                            n1 = n1*-1;
                        }
                        n2 = Double.parseDouble(temp[2]);
                        if (!temp[3].equals("E")){
                            n2 = n2*-1;
                        }
                    }catch (Exception e){
                        System.out.println("error while parsing");
                    }

                }
                MemoryItem mI = new MemoryItem(
                        add_name.getText().toString(), //
                        add_tags.getText().toString(), //
                        add_comment.getText().toString(), //
                        n1, //
                        n2,  //
                        add_name.getText().toString());

                check.setText(mI.getName());
            }
        });

        return view;
    }
}