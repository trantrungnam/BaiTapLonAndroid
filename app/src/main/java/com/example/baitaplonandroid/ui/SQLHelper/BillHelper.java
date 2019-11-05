package com.example.baitaplonandroid.ui.SQLHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.baitaplonandroid.ui.Models.Bill;
import com.example.baitaplonandroid.ui.Models.Food;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BillHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "restaurant";
    private static final String TABLE_NAME = "bill";
    private static final String ID = "id";
    private static final String TONGTIEN = "tongtien";
    private static final String THANHTOAN = "isPaied";
    private static final String CREATEAT = "create_at";
    private static final String UPDATEAT = "update_at";
    private static final String ISDELETE = "is_delete";

    private Context context;

    public BillHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("myapp", "Go on create");
        String sqlQuery = " CREATE TABLE " + TABLE_NAME + " ( " +
                ID + " INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                TONGTIEN + " DOUBLE  DEFAULT 0 ," +
                THANHTOAN + " INTEGER  DEFAULT 0 ," +
                CREATEAT + " DATETIME DEFAULT CURRENT_TIMESTAMP , " +
                UPDATEAT + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                ISDELETE + " INTEGER DEFAULT 0 " +
                ")";

        sqLiteDatabase.execSQL(sqlQuery);
        Toast.makeText(context, "Create successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

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

    public Bill addBill(Bill bill) {
        SQLiteDatabase db = this.getWritableDatabase();
        String date = getDateTime();
        ContentValues values = new ContentValues();
        values.put(TONGTIEN, bill.getTongTien());
//        values.put(THANHTOAN, bill.getIsPayed());
        values.put(CREATEAT, date);
        values.put(UPDATEAT, date);
        values.put(ISDELETE, 0);
        db.insert(TABLE_NAME, null, values);
        Toast.makeText(context, "Insert Success", Toast.LENGTH_SHORT).show();
        db.close();
        Bill result = getBillByDate(date);
        return result;
    }

    public Bill getBillByDate(String datetime) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE create_at = '" + datetime + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            if (cursor.getInt(5) == 0) {
                Bill bill = new Bill();
                bill.setId(cursor.getInt(0));
                bill.setTongTien(cursor.getDouble(1));
                bill.setIsPayed(cursor.getInt(2));
                bill.setCreateAt(cursor.getString(3));
                bill.setUpdateAt(cursor.getString(4));
                bill.setIsDelete(cursor.getString(5) == "0" ? false : true);
                return bill;
            }
        }

        cursor.close();
        db.close();
        return null;
    }

    public Bill getBillById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = " + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            if (cursor.getInt(5) == 0) {
                Bill bill = new Bill();
                bill.setId(cursor.getInt(0));
                bill.setTongTien(cursor.getDouble(1));
                bill.setIsPayed(cursor.getInt(2));
                bill.setCreateAt(cursor.getString(3));
                bill.setUpdateAt(cursor.getString(4));
                bill.setIsDelete(cursor.getString(5) == "0" ? false : true);
                return bill;
            }
        }

        cursor.close();
        db.close();
        return null;
    }

    public List<Bill> getAllBill() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE is_delete = 0 ";
        List<Bill> bills = new ArrayList<Bill>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getInt(5) == 0) {
                    Bill bill = new Bill();
                    bill.setId(cursor.getInt(0));
                    bill.setTongTien(cursor.getDouble(1));
                    bill.setIsPayed(cursor.getInt(2));
                    bill.setCreateAt(cursor.getString(3));
                    bill.setUpdateAt(cursor.getString(4));
                    bill.setIsDelete(cursor.getString(5) == "0" ? false : true);
                    bills.add(bill);
                }
            } while (cursor.moveToNext());
        }
        Toast.makeText(context, "Get all success", Toast.LENGTH_SHORT).show();
        cursor.close();
        db.close();
        return bills;
    }

    public int updatePayment(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(THANHTOAN, "1");
        Toast.makeText(context, id + "", Toast.LENGTH_SHORT).show();
        return db.update(TABLE_NAME, values, ID + " = ?", new String[]{String.valueOf(id)});
    }

    public int deleteBillById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ISDELETE, "1");
        Toast.makeText(context, id + "", Toast.LENGTH_SHORT).show();
        return db.update(TABLE_NAME, values, ID + " = ?", new String[]{String.valueOf(id)});
    }

}
