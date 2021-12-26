package br.com.guilhermeapc.goyote.dao;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.SQLException;
import android.os.Build;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    //Source code inspired on StackOverFlow article:
    // https://stackoverflow.com/questions/9109438/how-to-use-an-existing-database-with-an-android-application
    private static final String DATABASE_NAME="english_database" +
            ".sqlite";
    private static final int DATABASE_VERSION=1;
    private static String DATABASE_PATH="";
    private SQLiteDatabase mDataBase;
    private Context mContext = null;
    public static String COLUMN_AUTHOR="author";
    public static String COLUMN_ID="_id";
    public static String COLUMN_PHRASE="phrase";
    public static final String TABLE_PRODUCTS = "motivational_phrases";
    public DatabaseOpenHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        mContext = context;
        if(Build.VERSION.SDK_INT >= 17 && Build.VERSION.SDK_INT < 21){
            DATABASE_PATH = mContext.getApplicationContext().getApplicationInfo().dataDir + "/databases/";
        }else if(Build.VERSION.SDK_INT >= 21){
            DATABASE_PATH =  "/data/data/0/" + mContext.getDataDir().getName() + "/databases/";
        }else {
            DATABASE_PATH = "/data/data/0/" + mContext.getPackageName() + "/databases/";
        }
      }
    public  void createDataBase() throws IOException{
        boolean mDatabaseExist = checkDataBase();
        if(!mDatabaseExist){
            this.getReadableDatabase();
            this.close();
            try{
                //Copy database from assets
                copyDataBase();
            }catch (IOException mIOException){
                throw new Error("ErrorCopyingDatabase");
            }
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //To avoid any error while debugging
        try {
            createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean checkDataBase(){
        File dbFile = new File(DATABASE_PATH+DATABASE_NAME);
        return dbFile.exists();
    }
    private void copyDataBase() throws IOException{
        InputStream mInput = mContext.getAssets().open(DATABASE_NAME);
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while((mLength = mInput.read(mBuffer))>0){
            mOutput.write(mBuffer,0,mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }
    public boolean openDataBase()throws SQLException {
        String mPath = DATABASE_PATH + DATABASE_NAME;
        mDataBase = SQLiteDatabase.openDatabase(mPath,null,SQLiteDatabase.CREATE_IF_NECESSARY);
        //internalUnitTest.setCursorColumnStringTest("Integridade:"+String.valueOf(mDataBase.isDatabaseIntegrityOk())+mDataBase.getPath());
        return mDataBase != null;
    }
    public void close(){
        if(mDataBase != null){
            mDataBase.close();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
