package com.example.movieapp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<Movie> loadMovies(Context context) {
        List<Movie> movies = new ArrayList<>();

        try {
            InputStream is = context.getAssets().open("movies.json");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");
            JSONArray array = new JSONArray(json);

            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject obj = array.getJSONObject(i);

                    String title = obj.optString("title", "Unknown");

                    int year;
                    try {
                        year = obj.getInt("year");
                    } catch (Exception e) {
                        year = 0;
                    }

                    String genre = obj.optString("genre", "Unknown");
                    String poster = obj.optString("poster", "default");

                    movies.add(new Movie(title, year, genre, poster));

                } catch (Exception e) {
                    // skip bad data
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }
}