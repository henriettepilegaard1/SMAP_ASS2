package com.example.assignment1.Database;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assignment1.WordObject;

import java.util.List;


@Dao
public interface WordObjectDAO
{

    @Query("SELECT * FROM wordobject")
    List<WordObject> getAll();

    @Query("SELECT * FROM wordobject WHERE word IN (:word)")
    WordObject getWord(String word);

    @Insert
    void add(WordObject word);

    @Update
    void update(WordObject wordObjects);

    @Delete
    void delete(WordObject wordObject);
}
