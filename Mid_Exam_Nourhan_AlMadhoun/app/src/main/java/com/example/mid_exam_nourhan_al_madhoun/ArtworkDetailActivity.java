package com.example.mid_exam_nourhan_al_madhoun;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ArtworkDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork_detail);

        TextView counterText = findViewById(R.id.detail_counter_text);
        ImageView artworkImage = findViewById(R.id.detail_artwork_image);
        TextView titleText = findViewById(R.id.detail_artwork_title);
        TextView artistText = findViewById(R.id.detail_artwork_artist);
        TextView descriptionText = findViewById(R.id.detail_artwork_description);

        Intent intent = getIntent();
        String counter = intent.getStringExtra(MainActivity.EXTRA_COUNTER);
        String title = intent.getStringExtra(MainActivity.EXTRA_TITLE);
        String artist = intent.getStringExtra(MainActivity.EXTRA_ARTIST);
        String description = intent.getStringExtra(MainActivity.EXTRA_DESCRIPTION);
        int imageRes = intent.getIntExtra(MainActivity.EXTRA_IMAGE_RES, R.drawable.art_6);

        if (counter != null) counterText.setText(counter);
        if (title != null) titleText.setText(title);
        if (artist != null) artistText.setText(artist);
        if (description != null) descriptionText.setText(description);
        artworkImage.setImageResource(imageRes);

        Button visitWebsiteButton = findViewById(R.id.visit_website_button);
        visitWebsiteButton.setOnClickListener(v -> {
            String url = getString(R.string.artwork_website_url);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });
    }
}