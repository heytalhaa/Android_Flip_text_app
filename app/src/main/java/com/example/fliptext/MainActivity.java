package com.example.fliptext;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fliptext.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolBar);

        binding.flipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = binding.input.getText().toString();
                binding.output.setText(input);
                binding.output.setScaleX(-1);
                binding.output.setScaleY(-1);
                binding.output.setTextDirection(view.TEXT_DIRECTION_RTL);
                binding.result.setVisibility(View.VISIBLE);
            }
        });
        binding.clearBtn.setOnClickListener(view -> {
            binding.input.getText().clear();
            binding.output.getText().clear();
            binding.result.setVisibility(View.INVISIBLE);
        });
        binding.copyBtn.setOnClickListener(view -> {
            String flipText = flipText(binding.output.getText().toString());
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copy",flipText);
            clipboardManager.setPrimaryClip(clip);
            Toast.makeText(this, "Copied!", Toast.LENGTH_SHORT).show();
        });

        binding.shareBtn.setOnClickListener(view -> {
            String flippedText = flipText(binding.output.getText().toString());
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, flippedText);
            startActivity(Intent.createChooser(shareIntent, "Share flipped text via"));
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuId = item.getItemId();
        if (menuId == R.id.about){
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }
        return true;
    }

    private String flipText(String input) {
        StringBuilder flippedText = new StringBuilder();
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String flippedCharacters = "ɐqɔpǝɟƃɥᴉɾʞʃɯuodbɹsʇnʌʍxʎz∀qƆpƎℲ⅁HIſʞ⅃WNOԀΌᴚS⊥∩ΛMX⅄Z0ƖᄅƐㄣϛ9ㄥ86";

        for (int i = input.length() - 1; i >= 0; i--) {
            char c = input.charAt(i);
            int index = characters.indexOf(c);
            if (index != -1) {
                flippedText.append(flippedCharacters.charAt(index));
            } else {
                flippedText.append(c);  // Keep characters that are not in the map unchanged
            }
        }
        return flippedText.toString();
    }
}
