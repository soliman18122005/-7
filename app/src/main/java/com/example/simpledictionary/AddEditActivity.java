package com.example.simpledictionary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditActivity extends AppCompatActivity {

    private EditText etWord, etMeaning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etWord = findViewById(R.id.etWord);
        etMeaning = findViewById(R.id.etMeaning);
        Button btnSave = findViewById(R.id.btnSave);

        if (getIntent().hasExtra("WORD_TO_EDIT")) {
            WordModel item = (WordModel) getIntent().getSerializableExtra("WORD_TO_EDIT");
            if (item != null) {
                etWord.setText(item.getWord());
                etMeaning.setText(item.getMeaning());
            }
        }

        btnSave.setOnClickListener(v -> {
            String word = etWord.getText().toString().trim();
            String meaning = etMeaning.getText().toString().trim();

            if (!word.isEmpty() && !meaning.isEmpty()) {
                WordModel resultWord = new WordModel(word, meaning);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("RESULT_WORD", resultWord);
                setResult(RESULT_OK, returnIntent);
                finish(); // إغلاق الشاشة والرجوع للرئيسية
            } else {
                Toast.makeText(this, "Please enter both word and meaning", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}