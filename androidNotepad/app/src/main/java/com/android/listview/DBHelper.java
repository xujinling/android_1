package com.android.listview;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String INFO_TABLE="create table if not exists Info("
            +"id integer primary key autoincrement,"
            +"title text,"
            +"money text,"
            +"time text)";

    public DBHelper(Context context) {
        super(context, "Info.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(INFO_TABLE); //创建数据表
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * 插入数据
     */
    public void insertData(InfoBean infoBean){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("title",infoBean.getTitle());
        cv.put("money",infoBean.getMoney());
        cv.put("time",infoBean.getTime());
        db.insert("Info",null,cv);
    }

    /**
     *得到cursor对象
     */
    public Cursor getCursor(){
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.query("Info",null,null,null,null,null,null);
        return cursor;
    }


}
