package com.example.mid_exam_nourhan_al_madhoun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mid_exam_nourhan_al_madhoun.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static ActivityMainBinding mainBinding;

    public static final String EXTRA_COUNTER = "com.example.mid_exam_nourhan_al_madhoun.COUNTER";
    public static final String EXTRA_TITLE = "com.example.mid_exam_nourhan_al_madhoun.TITLE";
    public static final String EXTRA_ARTIST = "com.example.mid_exam_nourhan_al_madhoun.ARTIST";
    public static final String EXTRA_DESCRIPTION = "com.example.mid_exam_nourhan_al_madhoun.DESCRIPTION";
    public static final String EXTRA_IMAGE_RES = "com.example.mid_exam_nourhan_al_madhoun.IMAGE_RES";

    private static final String STATE_INDEX = "current_artwork_index";

    private final int[] artworkImages = {
            R.drawable.art_1,
            R.drawable.art_2,
            R.drawable.art_3,
            R.drawable.art_4,
            R.drawable.art_5,
            R.drawable.art_6
    };

    private int[] artworkTitleIds;
    private int[] artworkArtistIds;
    private int[] artworkDescriptionIds;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(mainBinding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        artworkTitleIds = new int[]{
                R.string.art1_title, R.string.art2_title, R.string.art3_title,
                R.string.art4_title, R.string.art5_title, R.string.art6_title
        };

        artworkArtistIds = new int[]{
                R.string.art1_artist, R.string.art2_artist, R.string.art3_artist,
                R.string.art4_artist, R.string.art5_artist, R.string.art6_artist
        };

        artworkDescriptionIds = new int[]{
                R.string.art1_description, R.string.art2_description, R.string.art3_description,
                R.string.art4_description, R.string.art5_description, R.string.art6_description
        };

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(STATE_INDEX, 0);
        }

        mainBinding.artworkImage.setOnClickListener(this::openArtworkDetails);

        updateUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_INDEX, currentIndex);
    }

    public void previousArtwork(View view) {
        if (currentIndex > 0) {
            currentIndex--;
            updateUI();
        }
    }

    public void nextArtwork(View view) {
        if (currentIndex < artworkImages.length - 1) {
            currentIndex++;
            updateUI();
        }
    }

    public void openArtworkDetails(View view) {
        String counter = (currentIndex + 1) + " / " + artworkImages.length;
        String title = getString(artworkTitleIds[currentIndex]);
        String artist = getString(artworkArtistIds[currentIndex]);
        String description = getString(artworkDescriptionIds[currentIndex]);
        int imageRes = artworkImages[currentIndex];

        Intent intent = new Intent(this, ArtworkDetailActivity.class);
        intent.putExtra(EXTRA_COUNTER, counter);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_ARTIST, artist);
        intent.putExtra(EXTRA_DESCRIPTION, description);
        intent.putExtra(EXTRA_IMAGE_RES, imageRes);
        startActivity(intent);
    }

    public void shareArtwork(View view) {
        String title = getString(artworkTitleIds[currentIndex]);
        String artist = getString(artworkArtistIds[currentIndex]);
        String shareText = title + " - " + artist;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(shareIntent, null));
    }

    private void updateUI() {
        String counter = (currentIndex + 1) + " / " + artworkImages.length;
        mainBinding.counterText.setText(counter);
        mainBinding.artworkImage.setImageResource(artworkImages[currentIndex]);
        mainBinding.artworkTitle.setText(getString(artworkTitleIds[currentIndex]));
        mainBinding.artworkArtist.setText(getString(artworkArtistIds[currentIndex]));

        boolean canGoPrevious = currentIndex > 0;
        boolean canGoNext = currentIndex < artworkImages.length - 1;

        mainBinding.previousButton.setEnabled(canGoPrevious);
        mainBinding.nextButton.setEnabled(canGoNext);

        int activeColor = android.graphics.Color.parseColor("#7A4928");
        int disabledColor = android.graphics.Color.parseColor("#BDBDBD");

        mainBinding.previousButton.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(canGoPrevious ? activeColor : disabledColor)
        );
        mainBinding.nextButton.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(canGoNext ? activeColor : disabledColor)
        );
    }
}