package com.example.baitaplonandroid.ui.SQLHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baitaplonandroid.ui.Models.Category;
import com.example.baitaplonandroid.ui.Models.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "restaurant";
    private static final String TABLE_NAME = "user";
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ROLE = "role";
    private static final String CREATEAT = "create_at";
    private static final String UPDATEAT = "update_at";
    private static final String ISDELETE = "is_delete";

    private Context context;

    public UserHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("myapp", "Go on create");
        String sqlQuery = " CREATE TABLE " + TABLE_NAME + " ( " +
                ID + " INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                USERNAME + " TEXT," +
                PASSWORD + " TEXT," +
                ROLE + " TEXT DEFAULT 'NV' ," +
                CREATEAT + " DATETIME DEFAULT CURRENT_TIMESTAMP , " +
                UPDATEAT + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                ISDELETE + " INTEGER DEFAULT 0 " +
                ")";
        sqLiteDatabase.execSQL(sqlQuery);
        Toast.makeText(context, "Create successfully", Toast.LENGTH_SHORT).show();

    }

//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        String sqlQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
//                ID + "INT  INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL," +
//                NAME + "TEXT" +
//                DESCRIPTION + "TEXT," +
//                CREATEAT + "DATETIME," +
//                UPDATEAT + "DATETIME," +
//                ISDELETE + "INTEGER" +
//                ")";
//        sqLiteDatabase.execSQL(sqlQuery);
//        Toast.makeText(context, "Create successfully", Toast.LENGTH_SHORT).show();
//    }

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


    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put(ID, category.getId());
        values.put(USERNAME, user.getUsername());
        //HANDLE PASSSWORD BEFORE INSERT.
        String password = md5(user.getUsername() + user.getPassword());
        //AFTER INSERT.
        values.put(PASSWORD, password);
//        values.put(ROLE, user.getRole());
//        values.put(CREATEAT , getDateTime());
//        values.put(UPDATEAT, getDateTime());
        values.put(ISDELETE, 0);
        db.insert(TABLE_NAME, null, values);
        Toast.makeText(context, "Insert Success", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public User getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, USERNAME, PASSWORD, ROLE, CREATEAT, UPDATEAT, ISDELETE}, ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToNext();
        }

        User user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), (cursor.getString(6) == "1") ? true : false);
        cursor.close();
        db.close();
        return user;
    }

    public int Update(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put(USERNAME, user.getUsername());
        String password = md5(user.getUsername() + user.getPassword());
        values.put(PASSWORD, password);
        values.put(UPDATEAT, getDateTime());
        values.put(ROLE, user.getRole());
        return db.update(TABLE_NAME, values, ID + "=?", new String[]{String.valueOf(user.getId())});
    }

    public int UpdateRole(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ROLE, user.getRole());
        return db.update(TABLE_NAME, contentValues, ID + "= ?", new String[]{String.valueOf(user.getId())});
    }

    public int UpdatePassword(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String password = md5(user.getUsername() + user.getPassword());
        contentValues.put(PASSWORD, password);
        return db.update(TABLE_NAME, contentValues, ID + "= ?", new String[]{String.valueOf(user.getId())});
    }

    public List<User> getAllUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        List<User> users = new ArrayList<User>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getInt(6) == 0) {
                    User user = new User();
                    user.setId(cursor.getInt(0));
                    user.setUsername(cursor.getString(1));
                    user.setPassword(cursor.getString(2));
                    user.setRole(cursor.getString(3));
                    user.setCreateAt(cursor.getString(4));
                    user.setUpdateAt(cursor.getString(5));
                    user.setIsDelete(cursor.getString(6) == "0" ? false : true);
                    users.add(user);
                }
            } while (cursor.moveToNext());
        }
        Toast.makeText(context, "Get all success", Toast.LENGTH_SHORT).show();
        cursor.close();
        db.close();
        return users;
    }

    public void delelteUserByIDWithHighPermission(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + "=?", new String[]{String.valueOf(id)});
        Toast.makeText(context, "Delete success", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public int deleteUserById(int id) {
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

    public void deleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DROP TABLE " + TABLE_NAME;
        db.execSQL(query);
        db.close();
    }

    public User getUserInfoByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.query(TABLE_NAME, new String[]{ID, USERNAME, PASSWORD, ROLE, ISDELETE}, USERNAME + "= ?", new String[]{username}, null, null, null, null);
        if (cs.moveToFirst()) {
            User user = new User(cs.getInt(0), cs.getString(1), cs.getString(2), cs.getString(3), cs.getString(4) == "1" ? true : false);
//            md5(user.getUsername() + user.getPassword());
            return user;
        }
        return null;
    }

    public User loginUser(String username, String password) {
        User user = getUserInfoByUsername(username);
        // CHECK USER IS EXISTS ON DATABSE OR NOT.
        if (user != null) {
            // HASH USERNAME AND PASSWORD WITH MD5.
            String valueHash = md5(username + password);
            if (valueHash.equals(user.getPassword())) {
                user.setPassword("");
                return user;
            }
        }
        return null;
    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
