package com.example.assignment1.Database;

import androidx.room.TypeConverter;

import com.example.assignment1.WordObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

//inspiration for this class found in https://stackoverflow.com/questions/54523558/how-to-make-typeconverters-work-with-list-of-custom-class
public class DefinitionConverter
{
    @TypeConverter
    public static List<WordObject.Definition> fromString(String value)
    {
        Type listType = new TypeToken<List<WordObject.Definition>>() {}.getType();
        List<WordObject.Definition> definitions = new Gson().fromJson(value, listType);
        return definitions;
    }

    @TypeConverter
    public static String listToString(List<WordObject.Definition> list)
    {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
