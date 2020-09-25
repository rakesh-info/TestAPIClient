package com.practice.testapiclient;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
  public static final String USER_TABLE = "tbl_users";
  public static final String COLLUMN_ID = "id";
  public static final String COLLUMN_EMAIL = "email";
  public static final String COLLUMN_MOBILE = "mobile";
  public static final String COLLUMN_PASSWORD = "password";

  public DbHelper(@Nullable Context context) {
    super(context, "user.db", null, 1);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String createStatement = "CREATE TABLE " + USER_TABLE + " (" + COLLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLLUMN_EMAIL + " TEXT, " + COLLUMN_MOBILE + " TEXT, " + COLLUMN_PASSWORD + " TEXT)";

    db.execSQL(createStatement);

  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }


  public boolean addRecord(ModelUser user) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(COLLUMN_EMAIL, user.getEmail());
    values.put(COLLUMN_MOBILE, user.getMobile());
    values.put(COLLUMN_PASSWORD, user.getPassword());
    boolean insert = db.insert(USER_TABLE, null, values) > 0;
    db.close();
    return insert;
  }

  public List<ModelUser> getAllRecords() {
    List<ModelUser> models = new ArrayList<>();

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE, null);

    if (cursor.moveToFirst()) {
      while (!cursor.isAfterLast()) {
        ModelUser model = new ModelUser();
        model.setId(cursor.getInt(cursor.getColumnIndex(COLLUMN_ID)));
        model.setEmail(cursor.getString(cursor.getColumnIndex(COLLUMN_EMAIL)));
        model.setMobile(cursor.getString(cursor.getColumnIndex(COLLUMN_MOBILE)));
        model.setPassword(cursor.getString(cursor.getColumnIndex(COLLUMN_PASSWORD)));
        models.add(model);
        cursor.moveToNext();
      }
    }
    cursor.close();
    db.close();
    return models;
  }

  public ModelUser getUser(String id) {
    ModelUser model = new ModelUser();
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + COLLUMN_ID + " = " + id, null);
    if (cursor.moveToFirst()) {
      while (!cursor.isAfterLast()) {
        model.setId(cursor.getInt(cursor.getColumnIndex(COLLUMN_ID)));
        model.setEmail(cursor.getString(cursor.getColumnIndex(COLLUMN_EMAIL)));
        model.setMobile(cursor.getString(cursor.getColumnIndex(COLLUMN_MOBILE)));
        model.setPassword(cursor.getString(cursor.getColumnIndex(COLLUMN_PASSWORD)));
      }
    }
    cursor.close();
    db.close();
    return model;
  }

  public String userLogin(String username, String password) {
    String id = null;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE
      + " WHERE (" + COLLUMN_EMAIL + " = " + username + " OR " + COLLUMN_MOBILE + " = " + username + ") AND "
      + COLLUMN_PASSWORD + " = " + password, null);
    if (cursor.moveToFirst()) {
      while (!cursor.isAfterLast()) {
        id = String.valueOf(cursor.getInt(cursor.getColumnIndex(COLLUMN_ID)));
      }
    }
    cursor.close();
    db.close();
    return id;
  }

}
