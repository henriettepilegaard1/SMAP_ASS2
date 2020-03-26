package com.example.assignment1;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.assignment1.Database.DefinitionConverter;

import java.util.List;

@Entity
public class WordObject {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String word;

    private String pronunciation;
    private String rating;
    private String notes;

    @TypeConverters(DefinitionConverter.class)
    private List<Definition> definitions;

    public WordObject(){}

    @Ignore
    public WordObject(String word, String pronunciation, String rating, String notes) {
        this.word = word;
        this.pronunciation = pronunciation;
        this.rating = rating;
        this.notes = notes;
        this.definitions = definitions;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    public class Definition {
        private String type;
        private String definition;
        private String example;
        private String image_url;
        private String emoji;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDefinition() {
            return definition;
        }

        public void setDefinition(String definition) {
            this.definition = definition;
        }

        public String getExample() {
            return example;
        }

        public void setExample(String example) {
            this.example = example;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getEmoji() {
            return emoji;
        }

        public void setEmoji(String emoji) {
            this.emoji = emoji;
        }
    }
}
