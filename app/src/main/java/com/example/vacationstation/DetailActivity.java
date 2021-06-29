package com.example.vacationstation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POKEMON_KEY = "pokemon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        MemoryItem it = getIntent().getParcelableExtra(EXTRA_POKEMON_KEY);
        Toast.makeText(DetailActivity.this, it.getName(), Toast.LENGTH_SHORT).show();
    }
}