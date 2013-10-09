package com.example.corso;

import java.sql.SQLException;
import java.util.ArrayList;

import UtilsDB.DBAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaPersoneActivity extends Activity {

	private DBAdapter adapter;
	private Cursor cursor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_persone);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_persone, menu);
		return true;
	}

	@Override
	protected void onResume() {
		//Non uso la OnCreate così sono sempre allineato al DB
		super.onResume();
		
		adapter = new DBAdapter(this);
		try {
			adapter.open();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		cursor = adapter.fetchAll();
		
		final Persona[] arrayPersone = new Persona[cursor.getCount()];
		ArrayList<String> cognomi = new ArrayList<String>(cursor.getCount());
		
		int index = 0;
		while (cursor.moveToNext()) {
			long id = cursor.getLong(cursor.getColumnIndex(DBAdapter.KEY_IDCONTATTO));
			
			Persona current_persona = new Persona(id);
			current_persona.setNome(cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_NOME)));
			current_persona.setCognome(cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_COGNOME)));
			current_persona.setDataNascita(cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_DDN)));
			current_persona.setNumTelefono(cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_NUMERO)));
			
			arrayPersone[index] = current_persona;
			cognomi.add(index, current_persona.getCognome());
			index++;
		}
		
		ArrayAdapter arrAdapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, cognomi);
		
		ListView lista = (ListView)findViewById(R.id.listViewPersone);
		lista.setAdapter(arrAdapter);
		lista.setOnItemClickListener( new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 
				Intent intent = new Intent(ListaPersoneActivity.this, DettaglioContattoActivity.class);
				
				Bundle extras = new Bundle();
				extras.putParcelable("persona", arrayPersone[arg2]);
				intent.putExtras(extras);
				
				startActivity(intent);
			}
		});
	}
}
