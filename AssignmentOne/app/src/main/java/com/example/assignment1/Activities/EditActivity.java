package com.example.assignment1.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment1.R;
import com.example.assignment1.WordObject;

public class EditActivity extends AppCompatActivity
{
    Button cancel, save;
    TextView rating, name, notes;
    SeekBar rateBar;
    WordObject wordObject;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        cancel = findViewById(R.id.cancelBtnEditPage);
        save = findViewById(R.id.saveBtnEditPage);
        rating = findViewById(R.id.ratingnTxtEditPage);
        rateBar = findViewById(R.id.coolnessBar);
        name = findViewById(R.id.NameOfAnimalTxtEditPage);
        notes = findViewById(R.id.noteFieldEditPage);

        getAnimalData();
        setAnimalData();
        rateBar.setProgress((int)Double.parseDouble(wordObject.getRating())*10);

        //https://www.youtube.com/watch?v=G03ZR0jKtVs used to figure out the seekbar
        rateBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                Double progressDouble = progress/10.0;
                rating.setText("" + progressDouble);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

        });

        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                wordObject.setRating(rating.getText().toString());
                wordObject.setNotes(notes.getText().toString());
                Intent intent = new Intent(EditActivity.this, DescriptionPage.class);
                //intent.putExtra("wordObject", wordObject);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private void getAnimalData()
    {
        if(getIntent().hasExtra("wordObject"))
        {
            wordObject = getIntent().getParcelableExtra("wordObject");
        }
        else
        {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAnimalData()
    {
        name.setText(wordObject.getWord());
        notes.setText(wordObject.getNotes());
        rating.setText(wordObject.getRating());
    }
}
