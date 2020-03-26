package com.example.assignment1.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment1.R;
import com.example.assignment1.WordObject;

import static com.example.assignment1.R.id.nameOfAnimalTxtDescription;

// https://www.youtube.com/watch?v=xgpLYwEmlO0 used to make Recyclerview and second activity
public class DescriptionPage extends AppCompatActivity {

    ImageView picOfAnimal;
    TextView description, notes, name, pron, rating;
    Button cancel, edit;
    WordObject wordObject;

    int index;

    @Override
    //https://www.youtube.com/watch?v=W1MSs8rBtB4 used to make buttons
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_page);

        picOfAnimal = findViewById(R.id.PicOfAnimalDescriptionPage);
        description = findViewById(R.id.descriptionTxtDescription);
        notes = findViewById(R.id.noteFieldTxtDescription);
        name = findViewById(nameOfAnimalTxtDescription);
        pron = findViewById(R.id.pronunciationTxtDescription);
        rating = findViewById(R.id.animalScoreTxtDescription);
        cancel = findViewById(R.id.cancelBtnDescription);
        edit = findViewById(R.id.editBtnDescription);

        getAnimalData();
        setAnimalData();

        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(DescriptionPage.this, EditActivity.class);
                //intent.putExtra("wordObject", wordObject); //Todo:fix
                startActivityForResult(intent, 10);
            }
        });
    }

    private void getAnimalData()
    {
        if(getIntent().hasExtra("wordObject") && getIntent().hasExtra("index"))
        {
            wordObject = getIntent().getParcelableExtra("wordObject");
            index = getIntent().getIntExtra("index", 0);
        }
        else
        {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAnimalData()
    {
        name.setText(wordObject.getWord());
        pron.setText(wordObject.getPronunciation());
        //picOfAnimal.setImageResource(wordObject.getImage());
        description.setText(wordObject.getDefinitions().get(0).getDefinition());
        notes.setText(wordObject.getNotes());
        rating.setText(wordObject.getRating());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10)
        {
            if (resultCode == RESULT_OK && data.hasExtra("wordObject"))
            {
                wordObject = data.getParcelableExtra("wordObject");
                Intent intent = new Intent();
                //intent.putExtra("wordObject", wordObject); //Todo:fix
                intent.putExtra("index", index);
                setResult(RESULT_OK, intent);
                finish();
            }
            if (resultCode == RESULT_CANCELED){
                //do nothing
            }
        }
    }
}
