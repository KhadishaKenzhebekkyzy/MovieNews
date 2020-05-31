//package com.example.thousand_it_company.database;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.example.thousand_it_company.data.MovieAdapter;
//import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
//
//public class Database extends SQLiteAssetHelper {
//    private static final String DB = 'MovieDb.db';
//    private static final int DB_V = 1;
//
//    public Database(Context context) {
//        super(context, DB, null, DB_V);
//    }
//
//    public void addToFavorites(String movieId){
//        SQLiteDatabase db = getReadableDatabase();
//        String query = String.format("INSERT INTO Favorites(MovieId) VALUES('&s');", movieId);
//        db.execSQL(query);
//    }
//
//    public void removeFromFavorites(String movieId){
//        SQLiteDatabase db = getReadableDatabase();
//        String query = String.format("DELETE FROM Favorites WHERE MovieId='&s';", movieId);
//        db.execSQL(query);
//    }
//
//    public boolean isFavorite(String movieId){
//        SQLiteDatabase db = getReadableDatabase();
//        String query = String.format("SELECT * FROM Favorites WHERE MovieId='&s';", movieId);
//        Cursor cursor = db.rawQuery(query,null);
//        if (cursor.getCount() <= 0){
//            cursor.close();
//            return false;
//        }
//        cursor.close();
//        return true;
//    }
//}


