package com.example.baitaplonandroid.ui.SQLHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.baitaplonandroid.ui.Models.Food;
import com.example.baitaplonandroid.ui.Models.User;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FoodHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "restaurant";
    private static final String TABLE_NAME = "food";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";
    private static final String UNIT = "unit";
    private static final String PICTURE = "picture";
    private static final String CATEGORY = "category";

    private static final String CREATEAT = "create_at";
    private static final String UPDATEAT = "update_at";
    private static final String ISDELETE = "is_delete";

    private Context context;

    public FoodHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("myapp", "Go on create");
        String sqlQuery = " CREATE TABLE " + TABLE_NAME + " ( " +
                ID + " INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                NAME + " TEXT," +
                DESCRIPTION + " TEXT," +
                PRICE + " DOUBLE ," +
                UNIT + " TEXT," +
                PICTURE + " TEXT," +
                CATEGORY + " TEXT," +
                CREATEAT + " DATETIME DEFAULT CURRENT_TIMESTAMP , " +
                UPDATEAT + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                ISDELETE + " INTEGER DEFAULT 0 " +
                ")";

        sqLiteDatabase.execSQL(sqlQuery);
        Toast.makeText(context, "Create successfully", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("myapp", "Go on update");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Toast.makeText(context, "Drop successfylly", Toast.LENGTH_SHORT).show();
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


    public void addFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put(ID, category.getId());
        values.put(NAME, food.getName());
        values.put(DESCRIPTION, food.getDescription());
        values.put(PRICE, food.getPrice());
        values.put(UNIT, food.getUnit());
        values.put(PICTURE, food.getPicture());
        values.put(CATEGORY, food.getCategories().toString());
        values.put(CREATEAT, getDateTime());
        values.put(UPDATEAT, getDateTime());
        values.put(ISDELETE, 0);
        db.insert(TABLE_NAME, null, values);
        Toast.makeText(context, "Insert Success", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public Food getFoodById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, NAME, DESCRIPTION, PRICE, UNIT, PICTURE, CATEGORY, CREATEAT, UPDATEAT, ISDELETE}, ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToNext();
        }

        String categoriesWithString = cursor.getString(6);
        String[] categoryIds = categoriesWithString.split(",");
        List<Integer> categoryWithInt = new ArrayList<Integer>() {
        };
        for (String category : categoryIds
        ) {
            categoryWithInt.add(Integer.parseInt(category));
        }
        Food food = new Food(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3), cursor.getString(4), cursor.getString(5), categoryWithInt);
        cursor.close();
        db.close();
        return food;
    }

    public int Update(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put(USERNAME, user.getUsername());
        values.put(NAME, food.getName());
        values.put(DESCRIPTION, food.getDescription());
        values.put(PRICE, food.getPrice());
        values.put(UNIT, food.getUnit());
        values.put(PICTURE, food.getPicture());
        values.put(CATEGORY, food.getCategories().toString());
        values.put(CREATEAT, getDateTime());
        values.put(UPDATEAT, getDateTime());
        values.put(ISDELETE, 0);

        return db.update(TABLE_NAME, values, ID + "=?", new String[]{String.valueOf(food.getId())});
    }

    public List<Food> getAllFood() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE is_delete = 0 ";
        List<Food> foods = new ArrayList<Food>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getInt(6) == 0) {
                    Food food = new Food();
                    food.setId(cursor.getInt(0));
                    food.setName(cursor.getString(1));
                    food.setDescription(cursor.getString(2));
                    food.setPrice(Double.parseDouble(cursor.getString(3)));
                    food.setUnit(cursor.getString(4));
                    food.setPicture(cursor.getString(5));

                    //Convert CategoriesID
                    String categoriesWithString = cursor.getString(6);
                    String[] categoryIds = categoriesWithString.split(",");
                    List<Integer> categoryWithInt = new ArrayList<Integer>() {
                    };
                    try {

                        for (String category : categoryIds
                        ) {
                            categoryWithInt.add(Integer.parseInt(category));
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Category no choose", Toast.LENGTH_SHORT).show();
                    }

                    food.setCategories(categoryWithInt);
                    food.setCreateAt(cursor.getString(7));
                    food.setUpdateAt(cursor.getString(8));
                    food.setIsDelete(cursor.getString(9) == "0" ? false : true);
                    foods.add(food);
                }
            } while (cursor.moveToNext());
        }
        Toast.makeText(context, "Get all success", Toast.LENGTH_SHORT).show();
        cursor.close();
        db.close();
        return foods;
    }

    public void delelteFoodByIDWithHighPermission(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + "=?", new String[]{String.valueOf(id)});
        Toast.makeText(context, "Delete success", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public int deleteFoodById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ISDELETE, "1");
        Toast.makeText(context, id + "", Toast.LENGTH_SHORT).show();
        return db.update(TABLE_NAME, values, ID + " = ?", new String[]{String.valueOf(id)});
    }


    public int getUserCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();
        return cursor.getCount();
    }

    public void deleteFoodTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DROP TABLE " + TABLE_NAME;
        db.execSQL(query);
        db.close();
    }
}
