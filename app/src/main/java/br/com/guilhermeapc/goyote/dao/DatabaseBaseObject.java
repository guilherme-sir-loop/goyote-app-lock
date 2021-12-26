package br.com.guilhermeapc.goyote.dao;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.guilhermeapc.goyote.services.ApplicationWatcher;
import br.com.guilhermeapc.goyote.gui.UnifiedDefinition;

/**
 * Created by Guilherme on 27/12/2018.
 */

public class DatabaseBaseObject extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DatabaseAcessObject.db";
    public static final String TABLE_PRODUCTS = "database_config";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PASSWORD = "_encryptedpassword";
    public static final String COLUMN_PACKAGES = "_packagelist";
    public static final String COLUMN_RANDOM = "_random_number";
    public static final String COLUMN_METHOD = "_password_method";
    public DatabaseAcessObject databaseAcessObject;
    //Tag do log
    private static final String DB = "myapplocker.Database";

    private EncryptionMethod algorithm = new EncryptionMethod();
    //Application launcher intent declared
    Intent launchIntent;
    //Application laucher context iniciliazed
    Context contextLauncher;
    ApplicationWatcher applicationWatcher = new ApplicationWatcher();
    public DatabaseBaseObject(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,DATABASE_NAME,factory,DATABASE_VERSION);
        contextLauncher = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String query = "CREATE TABLE if not exists " + TABLE_PRODUCTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_PACKAGES + " TEXT, " +
                COLUMN_RANDOM + " TEXT, " +
                COLUMN_METHOD + " TEXT " +
                ");";
        db.execSQL(query);

        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
         onCreate(db);
    }

    public void verifyOnDB(String password){
        launchIntent = new Intent();
        String dbString = "";
        String sql = "SELECT " + COLUMN_PASSWORD + "  FROM " + TABLE_PRODUCTS;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql,null);
        c.move(1);
        dbString = c.getString(0);
        if(algorithm.descrypt(dbString).equals(password)){
           applicationWatcher.openLastApp(contextLauncher);
        }else{
            launchIntent = new Intent(contextLauncher, UnifiedDefinition.class);
            contextLauncher.startActivity(launchIntent);
        }

    }
    public void subscribeOnDB(String passwordValue){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, algorithm.encrypt(passwordValue));
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_PRODUCTS,values,"_id=1",null);
        db.close();

    }
    public String viewApplicationsColumns(){
        String dbString =  "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_PACKAGES + " FROM " + TABLE_PRODUCTS;
        //That cursor going to cast the value of query into the string
        Cursor c = db.rawQuery(query,null);
        //In that line is moving to the first record
        c.move(1);
        //In that line is getting the first record string
        dbString = c.getString(0);
        //In that line is closing the cursor
        c.close();
        return dbString;
    }
    public void insertNewApplicationToTheList(String newApp){
        SQLiteDatabase db = getWritableDatabase();
        //That line do a research in the database
        String sql = "SELECT " + COLUMN_PACKAGES + " FROM " + TABLE_PRODUCTS;
        //The cursor recieve the value of query
        Cursor cursor = db.rawQuery(sql,null);
        //That line convert the result of query in a string
        cursor.move(1);
        String queryResult= cursor.getString(0);

        //And add to the database update
        String query = "UPDATE " + TABLE_PRODUCTS + " SET " + COLUMN_PACKAGES +"="+"\""+ queryResult + "," + newApp+"\"" + " WHERE " + COLUMN_ID + "=1";
        db.execSQL(query);


    }
    public void insertFirstApplication(String newApp){
        SQLiteDatabase db = getWritableDatabase();
        String sql ="UPDATE " + TABLE_PRODUCTS +" SET "+COLUMN_PACKAGES+"="+"'"+newApp +"'"+" WHERE " + COLUMN_ID+"=1";
        db.execSQL(sql);
        db.close();

    }
    public void removeApplicationList(){
        SQLiteDatabase db = getWritableDatabase();
        //Remove the entire application list
        String query = "UPDATE " + TABLE_PRODUCTS + " SET " + COLUMN_PACKAGES +"="+ "NULL" + " WHERE " + COLUMN_ID + "=1";
        db.execSQL(query);
        db.close();
    }
    public boolean getRandomNumber(){
        boolean random = false;
        String dbString =  "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_RANDOM + " FROM " + TABLE_PRODUCTS;
        //That cursor going to cast the value of query into the string
        Cursor c = db.rawQuery(query,null);
        //In that line is moving to the first record
        c.move(1);
        //In that line is getting the first record string
        if(c.getString(0)!=null) {
            dbString = c.getString(0);
        }

        //In that line is closing the cursor
        c.close();
        if(dbString.equals("true")){
            random = true;
        }else if(dbString.equals("false")){
            random = false;
        }

        return random;

    }
    public void setRandomNumber(boolean b){
        ContentValues values = new ContentValues();
        values.put(COLUMN_RANDOM,String.valueOf(b));
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_PRODUCTS,values,"_id=1",null);
        db.close();
    }
    public void setAutenticationMethod(String method){
        SQLiteDatabase db = getWritableDatabase();
        String query = "INSERT INTO " + TABLE_PRODUCTS + "(" + COLUMN_METHOD +")"+"values ('"+method+"')";
        db.execSQL(query);
        db.close();

    }
    public String getAutenticationMethod(){
        String dbString;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_METHOD + " FROM " + TABLE_PRODUCTS;
        //That cursor going to cast the value of query into the string
        Cursor c = db.rawQuery(query,null);
        //In that line is moving to the first record
        c.move(1);
        //In that line is getting the first record string
        dbString = c.getString(0);
        //In that line is closing the cursor
        c.close();
        db.close();

        return dbString;

    }

    /**
     * Run tests on the database, inserting the value of password
     */
    public void testValues() throws SQLException {
        /**
         *
         *         ContentValues values = new ContentValues();
         *
         *         values.put(COLUMN_PASSWORD,"AY!kDX$nFS&pGT*qJM3t");
         *         values.put(COLUMN_PACKAGES,"com.google.android.youtube");
         *         SQLiteDatabase db = getWritableDatabase();
         *         db.insert(TABLE_PRODUCTS, null, values);
         *         db.close();
         */

        SQLiteDatabase dbWriter = getWritableDatabase();
        String query = new StringBuilder().append("INSERT into ").append(TABLE_PRODUCTS).append("(").append(COLUMN_PASSWORD).append(",").append(COLUMN_PACKAGES).append(") ").append(" values ('AY!kDX$nFS&pGT*qJM3t','com.google.android.youtube');").toString();
        dbWriter.execSQL(query);
        dbWriter.close();


    }

}
