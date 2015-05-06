package com.qingluan.darkh.oldhelper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by darkh on 2/14/15.
 */
public class LoginDataBase extends SQLiteOpenHelper {
    private Context context;
    private static String DatabaseName = "acount.db";
    private  String ARG_ID ="id";
    private static String TableName = "LoginAccount";
    public static String NAME_ID = "name_id";


    public SQLiteDatabase localdb = null;
    public final  String CreateTableString = "create table " + TableName +
            "("+
            "id integer primary key autoincrement ,"+
            "name text  ,"+
            "community text ,"+
            "identity text ,"+
            "password text ,"+
            "name_id text ,"+
            "address text ,"+
            "version_id text ,"+
            "auto_login text  default '0' "+
            ")";




    public LoginDataBase(Context context){
        super(context,DatabaseName,null,5);
        this.context = context;
    }
    public LoginDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        localdb = sqLiteDatabase;
        ContentValues values = new ContentValues();
        values.put("name","Qingluan");
        values.put("password","123");
        values.put("name_id","123456789");
        values.put("version_id","0");
        values.put("auto_login","0");
        localdb.execSQL(CreateTableString);
        localdb.insert(TableName,null,values);

    }



    private Cursor query(String tableName){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(tableName,null,null,null,null,null,null,null);
    }

//    public String search( int queryArg,String columnName,String resultType){
////        localdb = getReadableDatabase();
//        Cursor c = this.query(TableName);
//        c.moveToFirst();
//        if (c.getCount() == 0){
//            c.close();
//            localdb.close();
//            return null;
//        }
//
//        if (c.getInt(c.getColumnIndex(columnName)) == queryArg){
//            String value = c.getString(c.getColumnIndex(resultType));
//            c.close();
//            this.localdb.close();
//
//            Log.d("value", "found : " + value);
//            return value;
//        }
//
//        String value = null;
//        while (c.moveToNext()){
//
//            if (c.getInt(c.getColumnIndex(columnName))==queryArg){
//                value = c.getString(c.getColumnIndex(resultType));
//                c.close();
//                this.localdb.close();
//
//            }
//        }
//
//        return value;
//
//
//    }

    public String search( String queryArg,String columnName,String resultType){
        localdb = getReadableDatabase();
        Cursor c = this.query(TableName);
        c.moveToFirst();

        Log.d("database count",String.valueOf(c.getCount()));

        if (c.getCount() == 0){

            c.close();
            localdb.close();
            return null;
        }
        String value = null;
        do{
            if (c.getString(c.getColumnIndex(columnName)).equals(queryArg)){
                value = c.getString(c.getColumnIndex(resultType));
                c.close();
                this.localdb.close();

            }
        }while (c.moveToNext());
//        if (c.getString(c.getColumnIndex(columnName)).equals(queryArg)){
//            String value = c.getString(c.getColumnIndex(resultType));
//            c.close();
//            this.localdb.close();
//
//            Log.d("value", "found : " + value);
//            return value;
//        }
//
//        String value = null;
//        while (c.moveToNext()){
//
//            if (c.getString(c.getColumnIndex(columnName)).equals(queryArg)){
//                value = c.getString(c.getColumnIndex(resultType));
//                c.close();
//                this.localdb.close();
//
//            }
//        }

        return value;


    }

    public Boolean update(String name_id  ,ContentValues updateValue){
        this.localdb = getWritableDatabase();
        return this.localdb.update(LoginDataBase.TableName,updateValue,NAME_ID + "=" +name_id,null) >0;
    }

    public String search( int queryArg,String resultType){
        Cursor c = this.query(TableName);

        if (c.getCount() == 0){
            c.close();
//            this.public_db.close();
            return null;

        }
        c.moveToFirst();

        if (c.getInt(c.getColumnIndex(ARG_ID)) == queryArg){
            String value = c.getString(c.getColumnIndex(resultType));
//            this.public_db.close();
            return value;
        }

        String value = null;
        while (c.moveToNext()){

            if (c.getInt(c.getColumnIndex(ARG_ID)) == queryArg ){
                value = c.getString(c.getColumnIndex(resultType));
                c.close();
//                this.public_db.close();
                return value;
            }
        }

        return value;


    }


    public String searchById(int id,String resultType){
//        Log.d("MainActivity",this.search(2,"id","content"));
        return this.search(id,resultType);
    }

    public int getId(String arg,String queryType){
        return Integer.valueOf( this.search(arg,queryType,DbHelper.ARG_ID) );
    }

    public int getCount(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = this.query(TableName);
        int size = c.getCount();
        c.close();
        db.close();
        return  size;
    }


    public void UpdateById (String obj_id ,String typeString  ,String value){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("update "+TableName+" set "+typeString+" =     '"+value+"' where id = "+obj_id+"; ");
        //int result = db.execSQL(TBL_NAME_MISSION, values, "mission    _object_id='"+obj_id+"'", null);
        db.close();
        //return result;
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        SQLiteDatabase db = getWritableDatabase();
        db.needUpgrade(5);

    }

    public boolean insert(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        if (db.insertOrThrow(TableName,null,values) != -1 ){
            return  true;
        }
        return  false;
    }
}
