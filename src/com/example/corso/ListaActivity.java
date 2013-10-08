package com.example.corso;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista);
		
		ListView lista = (ListView)findViewById(R.id.listViewSemplice);
		
		//definisco un elenco
		final String[] elenco = new String[] { "Fabrizio", "Enrico", "Giuseppe Roberto", " Aldo", "Mauro", "Daniele" };
		//creo l'adapter sull'elenco
		//android.R.layout ==> layout di android, non R dell'activity		
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elenco);
		
		CustomImageAdapter adapter = new CustomImageAdapter(this, R.layout.custom_cell, elenco);
		
		lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				/*Toast.makeText(getBaseContext(), elenco[arg2].toString(), Toast.LENGTH_SHORT).show();*/
				
				Intent intent = new Intent(ListaActivity.this, DettaglioActivity.class);
				intent.putExtra("nome", elenco[arg2].toString());
				startActivity(intent);
			}
			
		});
		
		lista.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista, menu);
		return true;
	}

}
