package edu.jsu.mcis.cs408.memopad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mydatabase.db";
    public static final String TABLE_MEMOS = "memos";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MEMO = "memo";



    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_MEMO_TABLE = "CREATE TABLE memos (_id integer primary key autoincrement, memo text)";
        sqLiteDatabase.execSQL(CREATE_MEMO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMOS);
    onCreate(sqLiteDatabase);
    }

    public void addMemo(Memo memo){
        ContentValues values = new ContentValues();
        values.put(COLUMN_MEMO, memo.getMemo());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_MEMOS, null, values);
        db.close();
    }

    public Memo getMemo(int id){
        String query = "SELECT * FROM " + TABLE_MEMOS + " WHERE " + COLUMN_ID + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Memo m = null;

        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            int newId = cursor.getInt(0);
            String newMemo = cursor.getString(1);
            cursor.close();
            m = new Memo(newId, newMemo);
        }

        db.close();
        return m;

    }

    public String getAllMemos(){
        String query = "SELECT * FROM " + TABLE_MEMOS;

        StringBuilder s = new StringBuilder();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(0);
                s.append(getMemo(id)).append("\n");
            }
            while (cursor.moveToNext());
        }

        db.close();
        return s.toString();
    }

    public ArrayList<Memo> getAllMemosAsList(){

        String query = "SELECT * FROM " + TABLE_MEMOS;

        ArrayList<Memo> allMemos = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            do {
                int newId = cursor.getInt(0);
                String newMemo = cursor.getString(1);
                allMemos.add(new Memo(newId, newMemo));
            }
            while (cursor.moveToNext());
        }

        db.close();
        return allMemos;
    }

    public boolean deleteMemo(int id){
        String query = "DELETE FROM " + TABLE_MEMOS + " WHERE " + COLUMN_ID + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }
}
