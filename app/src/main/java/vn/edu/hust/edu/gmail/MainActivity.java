package vn.edu.hust.edu.gmail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;

import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import io.bloco.faker.Faker;

public class MainActivity extends AppCompatActivity {
    List<GmailItemModel> items;
    GmailItemAdapter adapter;
    EditText editText;
    boolean isFavorite= true;
    Button favorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        favorite= findViewById(R.id.btn);

        editText = findViewById(R.id.edittext);

        editText.setInputType(InputType.TYPE_NULL);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterFavorite(isFavorite);
                isFavorite= (! isFavorite);
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                editText.setFocusableInTouchMode(true);
            }
        });

        Faker faker = new Faker();

        items = new ArrayList<>();
        for(int i=0; i< 8; i++){
            items.add(new GmailItemModel(faker.name.name(), faker.lorem.sentence(), faker.lorem.paragraph(),"12:00 PM"));
        }

        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new GmailItemAdapter(items);
        recyclerView.setAdapter(adapter);
    }

    private void filter(String text) {
        ArrayList<GmailItemModel> filteredList = new ArrayList<>();
        for(GmailItemModel item : items) {
            if(item.getName().toLowerCase().contains(text.toLowerCase())||item.getSubject().toLowerCase().contains(text.toLowerCase())||item.getContent().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }
    private  void  filterFavorite(boolean isFavorite){
        ArrayList<GmailItemModel> filteredFavorite = new ArrayList<>();
        if(isFavorite==true){
            for(GmailItemModel item : items) {
                if(item.isFavorite()==true) {
                    filteredFavorite.add(item);
                }
            }
        }else{
            filteredFavorite.addAll(items);
        }
        adapter.filterFavorite(filteredFavorite);
    }
}