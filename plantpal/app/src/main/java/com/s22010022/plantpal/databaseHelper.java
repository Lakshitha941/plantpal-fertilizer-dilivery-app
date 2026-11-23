package com.s22010022.plantpal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "plantPalDB.db";
    public static final int DATABASE_VERSION = 2;

    // User Credentials Table
    public static final String TABLE_USERS = "userCredentials";
    public static final String COL_USER_ID = "ID";
    public static final String COL_USERNAME = "userName";
    public static final String COL_EMAIL = "email";
    public static final String COL_NIC = "nic";
    public static final String COL_PASSWORD = "password";
    public static final String COL_PHONE = "phone";
    public static final String COL_ADDRESS = "address";

    // Cart Table
    public static final String TABLE_CART = "cart";
    public static final String COL_CART_ID = "cartId";
    public static final String COL_CART_USER = "userName";
    public static final String COL_CART_PRODUCT = "productName";
    public static final String COL_CART_CATEGORY = "category";
    public static final String COL_CART_PRICE = "price";
    public static final String COL_CART_QTY = "quantity";
    public static final String COL_CART_IMAGE = "imageResId";

    // Orders Table
    public static final String TABLE_ORDERS = "orders";
    public static final String COL_ORDER_ID = "orderId";
    public static final String COL_ORDER_USER = "userName";
    public static final String COL_ORDER_FULLNAME = "fullName";
    public static final String COL_ORDER_ADDRESS = "address";
    public static final String COL_ORDER_CITY = "city";
    public static final String COL_ORDER_PHONE = "phone";
    public static final String COL_ORDER_TOTAL = "totalAmount";
    public static final String COL_ORDER_DATE = "orderDate";

    public databaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " ("
                + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_USERNAME + " TEXT UNIQUE, "
                + COL_EMAIL + " TEXT, "
                + COL_NIC + " TEXT, "
                + COL_PASSWORD + " TEXT, "
                + COL_PHONE + " TEXT, "
                + COL_ADDRESS + " TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CART + " ("
                + COL_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_CART_USER + " TEXT, "
                + COL_CART_PRODUCT + " TEXT, "
                + COL_CART_CATEGORY + " TEXT, "
                + COL_CART_PRICE + " REAL, "
                + COL_CART_QTY + " INTEGER, "
                + COL_CART_IMAGE + " INTEGER)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ORDERS + " ("
                + COL_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ORDER_USER + " TEXT, "
                + COL_ORDER_FULLNAME + " TEXT, "
                + COL_ORDER_ADDRESS + " TEXT, "
                + COL_ORDER_CITY + " TEXT, "
                + COL_ORDER_PHONE + " TEXT, "
                + COL_ORDER_TOTAL + " REAL, "
                + COL_ORDER_DATE + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Upgrade existing table to add phone and address if they don't exist
            try {
                db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COL_PHONE + " TEXT");
            } catch (Exception ignored) {}
            try {
                db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COL_ADDRESS + " TEXT");
            } catch (Exception ignored) {}

            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CART + " ("
                    + COL_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_CART_USER + " TEXT, "
                    + COL_CART_PRODUCT + " TEXT, "
                    + COL_CART_CATEGORY + " TEXT, "
                    + COL_CART_PRICE + " REAL, "
                    + COL_CART_QTY + " INTEGER, "
                    + COL_CART_IMAGE + " INTEGER)");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ORDERS + " ("
                    + COL_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_ORDER_USER + " TEXT, "
                    + COL_ORDER_FULLNAME + " TEXT, "
                    + COL_ORDER_ADDRESS + " TEXT, "
                    + COL_ORDER_CITY + " TEXT, "
                    + COL_ORDER_PHONE + " TEXT, "
                    + COL_ORDER_TOTAL + " REAL, "
                    + COL_ORDER_DATE + " TEXT)");
        }
    }

    // --- User Auth Methods ---
    public boolean signUpInsert(String userName, String email, String nic, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, userName.trim());
        contentValues.put(COL_EMAIL, email.trim());
        contentValues.put(COL_NIC, nic.trim());
        contentValues.put(COL_PASSWORD, password);
        contentValues.put(COL_PHONE, "");
        contentValues.put(COL_ADDRESS, "");
        long result = db.insertWithOnConflict(TABLE_USERS, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        return result != -1;
    }

    public boolean checkUser(String userName, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?",
                new String[]{userName.trim(), password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Cursor getUserDetails(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COL_USERNAME + " = ?", new String[]{userName.trim()});
    }

    public boolean updateUserProfile(String userName, String email, String phone, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL, email);
        values.put(COL_PHONE, phone);
        values.put(COL_ADDRESS, address);
        int rows = db.update(TABLE_USERS, values, COL_USERNAME + " = ?", new String[]{userName.trim()});
        return rows > 0;
    }

    public boolean updatePassword(String userName, String nic, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COL_USERNAME + " = ? AND " + COL_NIC + " = ?",
                new String[]{userName.trim(), nic.trim()});
        if (cursor.getCount() > 0) {
            cursor.close();
            ContentValues values = new ContentValues();
            values.put(COL_PASSWORD, newPassword);
            int rows = db.update(TABLE_USERS, values, COL_USERNAME + " = ?", new String[]{userName.trim()});
            return rows > 0;
        }
        cursor.close();
        return false;
    }

    // --- Cart Methods ---
    public boolean addToCart(String userName, String productName, String category, double price, int quantity, int imageResId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Check if item already exists in cart for this user
        Cursor cursor = db.rawQuery("SELECT " + COL_CART_ID + ", " + COL_CART_QTY + " FROM " + TABLE_CART
                        + " WHERE " + COL_CART_USER + " = ? AND " + COL_CART_PRODUCT + " = ?",
                new String[]{userName.trim(), productName});

        if (cursor.moveToFirst()) {
            int cartId = cursor.getInt(0);
            int existingQty = cursor.getInt(1);
            cursor.close();
            ContentValues values = new ContentValues();
            values.put(COL_CART_QTY, existingQty + quantity);
            return db.update(TABLE_CART, values, COL_CART_ID + " = ?", new String[]{String.valueOf(cartId)}) > 0;
        } else {
            cursor.close();
            ContentValues values = new ContentValues();
            values.put(COL_CART_USER, userName.trim());
            values.put(COL_CART_PRODUCT, productName);
            values.put(COL_CART_CATEGORY, category);
            values.put(COL_CART_PRICE, price);
            values.put(COL_CART_QTY, quantity);
            values.put(COL_CART_IMAGE, imageResId);
            return db.insert(TABLE_CART, null, values) != -1;
        }
    }

    public Cursor getCartItems(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CART + " WHERE " + COL_CART_USER + " = ?", new String[]{userName.trim()});
    }

    public boolean updateCartQuantity(int cartId, int newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (newQuantity <= 0) {
            return deleteCartItem(cartId);
        }
        ContentValues values = new ContentValues();
        values.put(COL_CART_QTY, newQuantity);
        return db.update(TABLE_CART, values, COL_CART_ID + " = ?", new String[]{String.valueOf(cartId)}) > 0;
    }

    public boolean deleteCartItem(int cartId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CART, COL_CART_ID + " = ?", new String[]{String.valueOf(cartId)}) > 0;
    }

    public void clearCart(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, COL_CART_USER + " = ?", new String[]{userName.trim()});
    }

    public double getCartTotal(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COL_CART_PRICE + " * " + COL_CART_QTY + ") FROM " + TABLE_CART + " WHERE " + COL_CART_USER + " = ?",
                new String[]{userName.trim()});
        double total = 0.0;
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        return total;
    }

    // --- Order Methods ---
    public long insertOrder(String userName, String fullName, String address, String city, String phone, double totalAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ORDER_USER, userName.trim());
        values.put(COL_ORDER_FULLNAME, fullName);
        values.put(COL_ORDER_ADDRESS, address);
        values.put(COL_ORDER_CITY, city);
        values.put(COL_ORDER_PHONE, phone);
        values.put(COL_ORDER_TOTAL, totalAmount);
        values.put(COL_ORDER_DATE, java.text.DateFormat.getDateTimeInstance().format(new java.util.Date()));
        long orderId = db.insert(TABLE_ORDERS, null, values);
        if (orderId != -1) {
            clearCart(userName);
        }
        return orderId;
    }
}
