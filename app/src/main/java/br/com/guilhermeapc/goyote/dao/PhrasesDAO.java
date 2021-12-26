package br.com.guilhermeapc.goyote.dao;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;

public class PhrasesDAO {
    private SQLiteDatabase db;
    public static String COLUMN_AUTHOR="author";
    public static String COLUMN_ID="_id";
    public static String COLUMN_PHRASE="phrase";
    public static final String TABLE_PRODUCTS = "motivational_phrases";
    Context context;
    DatabaseOpenHelper databaseOpenHelper;
    public PhrasesDAO(Context context){
        this.context = context;
        databaseOpenHelper = new DatabaseOpenHelper(context);
    }
    public void close(){
        if(db!=null){
            this.db.close();
        }
    }
    public PhrasesDAO createDatabase() throws SQLException{
        try{
            databaseOpenHelper.createDataBase();
        }catch (IOException IOExceptio){
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }
    public PhrasesDAO open() throws SQLException{
        try{
            databaseOpenHelper.openDataBase();
            databaseOpenHelper.close();
            db = databaseOpenHelper.getReadableDatabase();
        }catch (SQLException SQLException){
            throw SQLException;
        }
        return this;
    }
    public String getAuthor(int randomizedId){
        //Define the variable authorName
        String authorName;
        //That line get the database from the file without have the need of onCreate and onUpgrade methods
        db = this.databaseOpenHelper.getWritableDatabase();
        //That is the database command for research the author name ny the given id
        String sql = "SELECT " + COLUMN_AUTHOR +" from "+TABLE_PRODUCTS+" where "+ COLUMN_ID+"=" +randomizedId;
        //That line will execute the query
        Cursor cursor = db.rawQuery(sql,null);
        //And return the valur for a String
        cursor.move(1);
        if(!cursor.getString(0).isEmpty()){
            authorName = cursor.getString(0);
        } else{
            authorName = "Unknown";
        }
        close();
        return authorName;
    }
    public String getCitation(int randomizedId){
        String citation;
        db = this.databaseOpenHelper.getWritableDatabase();
        String sql = "SELECT " + COLUMN_PHRASE +" from "+TABLE_PRODUCTS+" where "+ COLUMN_ID+"=" +randomizedId;
        Cursor cursor = db.rawQuery(sql,null);
        cursor.move(1);
        citation = cursor.getString(0);
        close();
        return citation;
    }
}
