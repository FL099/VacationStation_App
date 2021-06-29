package com.example.vacationstation.ui.Overview;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.vacationstation.DetailActivity;
import com.example.vacationstation.ListAdapter;
import com.example.vacationstation.MainActivity;
import com.example.vacationstation.MemoryItem;
import com.example.vacationstation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Overview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Overview extends Fragment {

    public ListAdapter mAdapter;

    Button btn_swap;
    private List<MemoryItem> lst_memories;
    private List<String> lst_places;


    public Overview() {
        // Required empty public constructor
    }

    public static Overview newInstance(String param1, String param2) {
        Overview fragment = new Overview();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        RecyclerView list = view.findViewById(R.id.rv_list);

        /*LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);
        list.setHasFixedSize(true);
        MainActivity.mAdapter = new ListAdapter(MainActivity.getContentFromDB());
        list.setAdapter(MainActivity.mAdapter);*/


        //lst_memories = generateContent();
        lst_memories = MainActivity.generateContent(); //TODO
        lst_places = MainActivity.lst_places;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);
        list.setHasFixedSize(true);
        mAdapter = new ListAdapter(lst_memories);
        list.setAdapter(mAdapter);

        mAdapter.setOnListItemClickListener(new ListAdapter.ListItemClickListener() {
            @Override
            public void onListItemClick(MemoryItem item) {
                //deleteInput(item);
                //upperCaseInput(item);
                Intent i = new Intent(getContext(),DetailActivity.class);
                i.putExtra(DetailActivity.EXTRA_MEMORY_KEY, item);
                startActivity(i);
            }
        });

        return view;
    }

    /*private List<MemoryItem> generateContent() {
        List<MemoryItem> data = new LinkedList<>();
        data.add(new MemoryItem("vienna1", "austria, flower, summer", "awesome",48.190498, 16.400408,  "card1.png"));
        data.add(new MemoryItem("vienna2", "austria, ancient, summer", "awesome", 48.190998, 16.420408, "card2.png"));
        data.add(new MemoryItem("vienna5", "austria, old, summer", "awesome", 48.190098, 16.395408, "card3.png"));
        data.add(new MemoryItem("vienna10", "austria, flower, summer", "awesome", 48.191018, 16.401408, "card2.png"));
        return data;
    }*/
}