package com.example.simpledictionary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DictionaryAdapter.OnItemClickListener {

    private ArrayList<WordModel> wordList;
    private DictionaryAdapter adapter;
    private int selectedPosition = -1;

    private static final int ADD_WORD_REQUEST = 1;
    private static final int EDIT_WORD_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) { getSupportActionBar().hide(); }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        if (savedInstanceState != null) {
            wordList = (ArrayList<WordModel>) savedInstanceState.getSerializable("MY_WORDS");
        } else {
            wordList = new ArrayList<>();
            wordList.add(new WordModel("Android", "نظام تشغيل هواتف"));
        }

        adapter = new DictionaryAdapter(wordList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            startActivityForResult(intent, ADD_WORD_REQUEST);
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("MY_WORDS", wordList);
    }

    @Override
    public void onItemClick(int position) {
        selectedPosition = position;
        Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
        intent.putExtra("WORD_TO_EDIT", wordList.get(position));
        startActivityForResult(intent, EDIT_WORD_REQUEST);
    }

    @Override
    public void onItemLongClick(int position) {
        wordList.remove(position);
        adapter.notifyItemRemoved(position);
        Toast.makeText(this, "A word is deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            WordModel returnedWord = (WordModel) data.getSerializableExtra("RESULT_WORD");

            if (requestCode == ADD_WORD_REQUEST) {
                wordList.add(returnedWord);
                Toast.makeText(this, "A word is added", Toast.LENGTH_SHORT).show();
            } else if (requestCode == EDIT_WORD_REQUEST && selectedPosition != -1) {
                wordList.set(selectedPosition, returnedWord);
                Toast.makeText(this, "A word is updated", Toast.LENGTH_SHORT).show();
            }
            adapter.notifyDataSetChanged();
        }
    }
}