package com.example.assignment1.Activities;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.assignment1.Adapters.MyAdapter;
import com.example.assignment1.Database.WordObjectDatabase;
import com.example.assignment1.R;
import com.example.assignment1.UpdateWordObject;
import com.example.assignment1.WordLearnerService;
import com.example.assignment1.WordObject;

import java.util.ArrayList;
import java.util.List;

// 	https://www.youtube.com/watch?v=18VcnYN5_LM&t=1s&fbclid=IwAR2ZJPWSKrUvdxR9Y9uM4jq-YE8_b_JrHfOEmimW4sv3t83Z1Q-3HZikk2I used to make recyclerview
public class MainActivity extends AppCompatActivity implements UpdateWordObject {

    private RequestQueue requestQueue;
    private static final String LOG = "MAIN";

    //used for bound counting service
    private WordLearnerService wordLearnerService;
    private ServiceConnection serviceConnection;
    private boolean bound = false;

    List<WordObject> wordObjectList;

    Button ExitButton, AddButton;

    String myWords[], myPronunciation[], myDescription[];

    RecyclerView animalListView;
    MyAdapter myAdapter;
    SearchView searchView;

    int images[] =
            {
                    R.drawable.lion,
                    R.drawable.leopard,
                    R.drawable.cheetah,
                    R.drawable.elephant,
                    R.drawable.giraffe,
                    R.drawable.kudo,
                    R.drawable.gnu,
                    R.drawable.oryx,
                    R.drawable.camel,
                    R.drawable.shark,
                    R.drawable.crocodile,
                    R.drawable.snake,
                    R.drawable.buffalo,
                    R.drawable.ostrich,
            };

    private void bindToWordLearnerService()
    {
        bindService(new Intent(MainActivity.this, WordLearnerService.class), serviceConnection, Context.BIND_AUTO_CREATE);
        bound = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupConnectionToWordLearnerService();
        bindToWordLearnerService();

        animalListView = findViewById(R.id.animalListMain);

        //myWords = getResources().getStringArray(R.array.my_words);
        //myPronunciation = getResources().getStringArray(R.array.Pronunciation);
        //myDescription = getResources().getStringArray(R.array.Description);

        //https://www.youtube.com/watch?v=TcTgbVudLyQ
        if (savedInstanceState !=  null)
        {
            //wordObjectList = savedInstanceState.getParcelableArrayList("wordObjectList"); //Todo: fix
        }
        else
        {
            //makeListOfWords();
        }

        AddButton = findViewById(R.id.addBtnMain);
        ExitButton = findViewById(R.id.exitBtnMain);

        //Exit method
        ExitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
                System.exit(0);
            }
        });

        //Adds new word to the database
        AddButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String searchText = searchView.getQuery().toString();
                if (searchText != null)
                {
                    wordLearnerService.addWord(searchText);
                }
            }
        });

    }

    /*
    private void makeListOfWords()
    {
        wordObjectList = new ArrayList<>();
        for (int i = 0; i < images.length; i++)
        {
            WordObject wordObject = new WordObject(myWords[i], myPronunciation[i], "","");
            wordObjectList.add(wordObject);
        }
    }
*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11)
        {
            if (resultCode == RESULT_OK && data.hasExtra("wordObject") && data.hasExtra("index"))
            {
                WordObject wordObject = data.getParcelableExtra("wordObject");
                int index = data.getIntExtra("index", 0);
                wordObjectList.set(index, wordObject);
                myAdapter.notifyDataSetChanged();
            }
            if (resultCode == RESULT_CANCELED){
                //do nothing
            }
        }
    }

    @Override
    public void UpdateWordObjectMethod(WordObject wordObject, int index)
    {
        Intent intent = new Intent(MainActivity.this, DescriptionPage.class);
        intent.putExtra("index", index);
        //intent.putExtra("wordObject", wordObject);
        startActivityForResult(intent, 11);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList("wordObjectList", wordObjectList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu .findItem(R.id.search_icon);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    //Methods for connecting to the service
    private void setupConnectionToWordLearnerService()
    {
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service)
            {
                wordLearnerService = ((WordLearnerService.WordLearnerServiceBinder)service).getService();
                Log.d(LOG, "Word learner service connected");
                wordObjectList = wordLearnerService.getAllWords();
                UpdateMainLayout();
            }

            @Override
            public void onServiceDisconnected(ComponentName name)
            {

            }
        };
    }

     public void UpdateMainLayout()
     {
         myAdapter = new MyAdapter(this, wordObjectList, this);
         animalListView.setAdapter(myAdapter);
         animalListView.setLayoutManager(new LinearLayoutManager(this));
     }

}
