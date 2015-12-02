package br.com.twautomacao.safetycontrolsms.connection;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Ricardo on 18/08/2014.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME="smscontrol";
    private static final String CREATEFILE = "create.sql";
    Context context;

    public DatabaseHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    private void createTables(SQLiteDatabase db) {
        context.getAssets();

        InputStream inputStream = null;
        BufferedReader reader = null;

        try {
            //BufferedReader reader = new BufferedReader( new InputStreamReader(  ) ) );
            //inputStream = manager.open(SQL_DIR + File.separator + CREATEFILE);

            reader = new BufferedReader(new InputStreamReader(context.getAssets().open( CREATEFILE , AssetManager.ACCESS_STREAMING)));

            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            String[] sqls = stringBuilder.toString().split(";");

            for (String sql : sqls) {
                Log.e(sql,"sql");
                db.execSQL(sql);
            }

        } catch (IOException e) {
            Log.e(e.getMessage(),"erro");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                Log.e(e.getMessage(),"erro");
            }
        }
    }
}
