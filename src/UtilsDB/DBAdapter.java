package UtilsDB;

import java.sql.SQLException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DBAdapter {

	private final Context context;
	private DBHelper dbHelper;
	private SQLiteDatabase database;
	
	private static final String NOME_TABELLA = "contatti";
	
	public static final String KEY_IDCONTATTO = "_id";
	public static final String KEY_NOME = "nome";
	public static final String KEY_COGNOME = "cognome";
	public static final String KEY_DDN = "ddn";
	public static final String KEY_NUMERO = "numero";
	
	public DBAdapter(Context context) {
		this.context = context;
	}
	
	public DBAdapter open() throws SQLException {
		dbHelper = new DBHelper(this.context);
		database = dbHelper.getWritableDatabase();
		
		/*rende il database sicuramente utilizzabile*/
		database.rawQuery("SELECT 1;", null);
		
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
	private ContentValues createContattoContentValues(String nome, String cognome, String ddn, String numero) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("nome", nome);
		contentValues.put("cognome", cognome);
		contentValues.put("ddn", ddn);
		contentValues.put("numero", numero);
		
		return contentValues;
	}
	
	public long creaContatto(String nome, String cognome, String ddn, String numero) {
		ContentValues valoriContatto = createContattoContentValues(nome, cognome, ddn, numero);
		
		long risultato = -1;
		
		try {
			risultato = database.insertOrThrow(NOME_TABELLA, null, valoriContatto);
		} catch (android.database.SQLException e) {
			e.printStackTrace();
			return risultato;
		}
		
		return risultato;
	}
	
	public boolean deleteContatto(long idContatto) {
		return database.delete(NOME_TABELLA, KEY_IDCONTATTO + "=" + idContatto, null) > 0;
	}
	
	public boolean updateContatto(long idContatto,String nome,String cognome,
			String ddn,String numero) {
		ContentValues valoriUpdateContatto = createContattoContentValues(nome,cognome,ddn,numero);
		String clausolaWhere = KEY_IDCONTATTO + "=" + idContatto;
		return database.update(NOME_TABELLA,valoriUpdateContatto,clausolaWhere,null) > 0;
	}
	
	public Cursor fetchAll() {
		String[] colonne = { KEY_IDCONTATTO, KEY_NOME, KEY_COGNOME, KEY_DDN, KEY_NUMERO };
		
		Cursor cursor = database.query(NOME_TABELLA, colonne, null, null, null, null, null);
		
		return cursor;
	}
	
	public Cursor fetchContattiByFilter(String filter) {
		String[] colonne = { KEY_IDCONTATTO, KEY_NOME, KEY_COGNOME, KEY_DDN, KEY_NUMERO };
		
		Cursor cursor = database.query(true, NOME_TABELLA, colonne, filter, null, null, null, null, null);
		
		return cursor;
	}
}
