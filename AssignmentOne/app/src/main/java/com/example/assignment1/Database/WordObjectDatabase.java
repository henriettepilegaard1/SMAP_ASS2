package com.example.assignment1.Database;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.assignment1.WordObject;

//inspiration to this class is from SMAP lecture 4, TheSituationRoom
@Database(entities = {WordObject.class}, exportSchema = false, version = 1)
@TypeConverters({DefinitionConverter.class})
public abstract class WordObjectDatabase extends RoomDatabase
{
   public abstract WordObjectDAO wordObjectDAO();
}
