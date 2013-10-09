package UtilsDB;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "database.db";
	private static final String DB_PATH = "/data/data/com.example.corso/db/";
	private static final int DB_VERSION = 1;
	
	private static final String DB_CREATE = "create table contatti(_id integer primary key autoincrement," +
            " nome text not null, cognome text not null, ddn text not null, " +
            "numero text not null);";
	
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase) {
		// nel caso in cui non esista il DB, permette la creazione
		// non funziona proprio come onCreate dell'activity
		sqliteDatabase.execSQL(DB_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase, int arg1, int arg2) {
		// distruggi e ricrea
		sqliteDatabase.execSQL("DROP TABLE IF EXISTS contatti");
		onCreate(sqliteDatabase);
	}

}
