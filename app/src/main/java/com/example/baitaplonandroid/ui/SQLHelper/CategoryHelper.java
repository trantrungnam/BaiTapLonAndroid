package com.example.baitaplonandroid.ui.SQLHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baitaplonandroid.ui.Models.Category;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CategoryHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "restaurant";
    private static final String TABLE_NAME = "category";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String CREATEAT = "create_at";
    private static final String UPDATEAT = "update_at";
    private static final String ISDELETE = "is_delete";

    private Context context;

    public CategoryHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + "INT INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL," +
                NAME + "TEXT," +
                DESCRIPTION + "TEXT," +
                CREATEAT + "DATETIME DEFAULT CURRENT_TIMESTAMP ," +
                UPDATEAT + "DATETIME DEFAULT CURRENT_TIMESTAMP," +
                ISDELETE + "INTEGER" +
                ")";
        sqLiteDatabase.execSQL(sqlQuery);
        Toast.makeText(context, "Create successfully", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sqlQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                ID + "INT  INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL," +
                NAME + "TEXT" +
                DESCRIPTION + "TEXT," +
                CREATEAT + "DATETIME," +
                UPDATEAT + "DATETIME," +
                ISDELETE + "INTEGER" +
                ")";
        sqLiteDatabase.execSQL(sqlQuery);
        Toast.makeText(context, "Create successfully", Toast.LENGTH_SHORT).show();
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


    public void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, category.getId());
        values.put(NAME, category.getName());
        values.put(DESCRIPTION, category.getDescription());
//        values.put(CREATEAT , getDateTime());
//        values.put(UPDATEAT, getDateTime());
        values.put(ISDELETE, 0);
        db.insert(TABLE_NAME, null, values);
        Toast.makeText(context, "Insert Success", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public Category getCategoryById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_NAME, new String[]{ID, NAME, DESCRIPTION, CREATEAT, UPDATEAT, ISDELETE}, ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToNext();
        }

        Category category = new Category(cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), (cursor.getString(6) == "1") ? true : false);
        cursor.close();
        db.close();
        return category;
    }

    public int Update(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, category.getName());
        values.put(DESCRIPTION, category.getDescription());
        values.put(UPDATEAT, getDateTime());
        return db.update(TABLE_NAME, values, ID + "=?", new String[]{String.valueOf(category.getId())});
    }

    public List<Category> getAllCategory() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        List<Category> categories = new ArrayList<Category>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(cursor.getInt(1));
                category.setName(cursor.getString(2));
                category.setDescription(cursor.getString(3));
                category.setCreateAt(cursor.getString(4));
                category.setUpdateAt(cursor.getString(5));
                category.setIsDelete(cursor.getString(6) == "0" ? false : true);
                categories.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categories;
    }

    public void delelteCategoryByIDWithHighPermission(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + "=?", new String[]{String.valueOf(id)});
        Toast.makeText(context, "Delete success", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public int deleteCategoryById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ISDELETE, 1);
        return db.update(TABLE_NAME, values, ID + "=?", new String[] {String.valueOf(id)});

    }


    public int getCategoriesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();
        return cursor.getCount();
    }
}
