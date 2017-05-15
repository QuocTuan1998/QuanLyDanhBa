package com.example.quoctuan.quanlydanhba;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    String DATABASE_NAME = "dbContact.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handleCopyDatabaseFromAssetIntoSystemMobile();
    }

    private void handleCopyDatabaseFromAssetIntoSystemMobile() {

        File dbfile = getDatabasePath(DATABASE_NAME);
        if (!dbfile.exists()){
            try {
                copyDatabaseFromAsset();
                Toast.makeText(this,"Success!",Toast.LENGTH_LONG).show();

            }catch (Exception e){
                Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
            }
        }

    }

    private void copyDatabaseFromAsset() {
        try {
//          open file from asset
            InputStream myInput = getAssets().open(DATABASE_NAME);
//            get DB_PATH
            String outFileName = getDB_PATH_SUFFIX();
//            get File
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
//          if file no exist create
            if (!f.exists()){
                f.mkdir();
            }
//            ceate to copy
            OutputStream myOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0){
                myOutput.write(buffer,0,length);
            }
//            close file
            myOutput.flush();
            myOutput.close();
            myInput.close();


        }catch (Exception ex){
            Log.e("Error_Copy",ex.toString());
        }

    }

    private String getDB_PATH_SUFFIX (){
//        return ours pakages conect with DATABASE
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }
}
