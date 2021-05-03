package com.example.fridge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseAdapter {
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase database;

    public DataBaseAdapter(Context context) {
        dataBaseHelper = new DataBaseHelper(context.getApplicationContext());
    }

    public DataBaseAdapter open() {
        database = dataBaseHelper.open();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public long getCount() {
        return DatabaseUtils.queryNumEntries(database, DataBaseHelper.TABLE);
    }

    private Cursor getAllElements() {
        String[] columns = new String[]{DataBaseHelper.COLUMN_ID, DataBaseHelper.COLUMN_NAME,
                DataBaseHelper.COLUMN_YEAR};
        return database.query(DataBaseHelper.TABLE, columns, null, null, null, null, null);
    }

    public long insert(Prod prod) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COLUMN_NAME, prod.getName());
        cv.put(DataBaseHelper.COLUMN_YEAR, prod.getDays());
        return database.insert(DataBaseHelper.TABLE, null, cv);
    }

    public long delete(long userId) {
        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(userId)};
        return database.delete(DataBaseHelper.TABLE, whereClause, whereArgs);
    }

    public long update(Prod prod) {
        String whereClause = DataBaseHelper.COLUMN_ID + "=" + String.valueOf(prod.getId());
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COLUMN_NAME, prod.getName());
        cv.put(DataBaseHelper.COLUMN_YEAR, prod.getDays());
        return database.update(DataBaseHelper.TABLE, cv, whereClause, null);
    }

    public List<Prod> getProds() {
        ArrayList<Prod> users = new ArrayList<>();
        Cursor cursor = getAllElements();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_NAME));
            String days1 = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_YEAR));
            int days = Integer.parseInt(days1);
            users.add(new Prod(id, name, days));
        }
        cursor.close();
        return users;
    }
    public Prod getUser(long id){
        Prod user = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DataBaseHelper.TABLE,DataBaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query,new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_NAME));
            String days1 = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_YEAR));
            int days = Integer.parseInt(days1);
            user = new Prod(id, name, days);
        }
        cursor.close();
        return user;
    }
}