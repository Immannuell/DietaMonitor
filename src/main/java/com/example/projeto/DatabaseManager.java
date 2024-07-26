package com.example.projeto;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long insertData(int calorias, int proteinas) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("calorias", calorias);
        contentValues.put("proteinas", proteinas);
        return database.insert("example_table", null, contentValues);
    }

    public Cursor getData() {
        return database.query("example_table", null, null, null, null, null, null);
    }

    public int updateData(int id, int calorias, int proteinas) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("calorias", calorias);
        contentValues.put("proteinas", proteinas);
        return database.update("example_table", contentValues, "id = ?", new String[]{String.valueOf(id)});
    }

    public int deleteData(int id) {
        return database.delete("example_table", "id = ?", new String[]{String.valueOf(id)});
    }
}
