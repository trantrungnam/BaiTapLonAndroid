package com.example.baitaplonandroid.ui.SQLHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.baitaplonandroid.ui.Models.Bill;
import com.example.baitaplonandroid.ui.Models.Bill_Food;
import com.example.baitaplonandroid.ui.Models.Food;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BillFoodHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "restaurant";
    private static final String TABLE_NAME = "bill_food";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String QUANTITY = "quantity";
    private static final String PRICE = "price";
    private static final String BILLID = "bill";
    private static final String CREATEAT = "create_at";
    private static final String UPDATEAT = "update_at";
    private static final String ISDELETE = "is_delete";

    private Context context;

    public BillFoodHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = " CREATE TABLE " + TABLE_NAME + " ( " +
                ID + " INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                NAME + " TEXT," +
                QUANTITY + " INTEGER," +
                PRICE + " DOUBLE," +
                BILLID + " INTEGER," +
                CREATEAT + " DATETIME DEFAULT CURRENT_TIMESTAMP , " +
                UPDATEAT + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                ISDELETE + " INTEGER DEFAULT 0 " +
                ")";

        sqLiteDatabase.execSQL(sqlQuery);
        Toast.makeText(context, "Create successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("myapp", "Go on update");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String addBillFood(Bill_Food bill_food, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String date = getDateTime();
        ContentValues values = new ContentValues();
        values.put(NAME, bill_food.getName());
        values.put(QUANTITY, bill_food.getQuantity());
        values.put(PRICE, bill_food.getPrice());
        values.put(BILLID, id);
        values.put(CREATEAT, date);
        values.put(UPDATEAT, date);
        values.put(ISDELETE, 0);
        db.insert(TABLE_NAME, null, values);
        Toast.makeText(context, "Insert Success", Toast.LENGTH_SHORT).show();
        db.close();
        return date;
    }

    public List<Bill_Food> getAllBill_food_By_ID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE bill = " + id + " and  is_delete = 0 ";
        List<Bill_Food> bill_foods = new ArrayList<Bill_Food>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getInt(6) == 0) {
                    Bill_Food bill_food = new Bill_Food();
                    bill_food.setId(cursor.getInt(0));
                    bill_food.setName(cursor.getString(1));
                    bill_food.setQuantity(Integer.parseInt(cursor.getString(2)));
                    bill_food.setPrice(Double.parseDouble(cursor.getString(3)));
                    bill_food.setCreateAt(cursor.getString(4));
                    bill_food.setUpdateAt(cursor.getString(5));
                    bill_food.setIsDelete(cursor.getString(6) == "0" ? false : true);
                    bill_foods.add(bill_food);
                }
            } while (cursor.moveToNext());
        }
        Toast.makeText(context, "Get all success", Toast.LENGTH_SHORT).show();
        cursor.close();
        db.close();
        return bill_foods;
    }

    public int deleteBill_FoodById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ISDELETE, "1");
        Toast.makeText(context, id + "", Toast.LENGTH_SHORT).show();
        return db.update(TABLE_NAME, values, ID + " = ?", new String[]{String.valueOf(id)});
    }

    public int Update(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, food.getName());
        values.put(QUANTITY, food.getUnit());
        values.put(PRICE, food.getPrice());
        values.put(CREATEAT, getDateTime());
        values.put(UPDATEAT, getDateTime());
        values.put(ISDELETE, 0);

        return db.update(TABLE_NAME, values, ID + "=?", new String[]{String.valueOf(food.getId())});
    }
}
