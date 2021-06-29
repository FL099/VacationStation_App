package com.example.vacationstation.ui.home;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vacationstation.MainActivity;
import com.example.vacationstation.MemoryItem;
import com.example.vacationstation.R;

import org.w3c.dom.Text;

import java.util.List;


public class HomeFragment extends Fragment {


    private static CardView card;
    private static TextView cardText;
    private static TextView cardDesc;
    private int curr;

    private static List<MemoryItem> lst_memories;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        lst_memories = MainActivity.generateContent();
        int len_mem = lst_memories.size();

        card = view.findViewById(R.id.card_view);
        cardText = view.findViewById(R.id.card_text);
        cardText.setText("Paris");
        cardDesc = view.findViewById(R.id.card_text_desc);
        cardDesc.setText("Lange nicht mehr so viel Wein getrunken");

        card.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (curr < len_mem-1) {
                    curr++;
                }
                else {
                    curr = 0;
                }
                MemoryItem temp = lst_memories.get(curr);
                cardText.setText(temp.getName());
                cardDesc.setText(temp.getComment());

            }
        });


        return view;
    }
}