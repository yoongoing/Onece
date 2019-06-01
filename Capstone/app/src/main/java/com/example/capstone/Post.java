package com.example.capstone;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Post {

    public String uid;
    public String author;
    public String title;
    public String body;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }


    @Exclude
    public Map<String, Object> toMap(String fildeName , String value) {
        HashMap<String, Object> result = new HashMap<>();
        result.put(fildeName,value);
        return result;
    }

}