package com.example.vacationstation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {


    private ImageView img;
    private TextView img_title;
    private TextView img_tags;
    private TextView img_comment;
    private TextView img_coord;
    public static final String EXTRA_MEMORY_KEY = "resultText";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        img = findViewById(R.id.img_img);
        img_title = findViewById(R.id.img_name);
        img_tags = findViewById(R.id.img_tags);
        img_comment = findViewById(R.id.img_comment);
        img_coord = findViewById(R.id.img_coord);

        MemoryItem it = getIntent().getParcelableExtra(EXTRA_MEMORY_KEY);

        img.setImageResource(R.drawable.card1);//TODO ändern auf jeweiliges Bild

        img_title.setText(it.getName());
        img_title.setTextSize(30);
        img_tags.setText("tags: " +it.getTags());
        img_comment.setText("Your comment: "+it.getComment());
        img_coord.setText("coordinates: "+it.getCoordLat()+" "+ it.getCoordLon());

        Toast.makeText(DetailActivity.this, it.getName(), Toast.LENGTH_SHORT).show();
    }
}