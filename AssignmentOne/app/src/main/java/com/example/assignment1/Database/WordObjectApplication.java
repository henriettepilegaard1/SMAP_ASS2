package com.example.assignment1.Database;

import android.app.Application;
import androidx.room.Room;

//inspiration to this class is from SMAP lecture 4, TheSituationRoom

public class WordObjectApplication extends Application
{
    private WordObjectDatabase db;
    public WordObjectDatabase getDb()
    {
        if (db == null)
        {
            db = Room.databaseBuilder(this, WordObjectDatabase.class, "WordObjects").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return db;
    }
}
