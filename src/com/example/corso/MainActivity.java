package com.example.corso;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final EditText testoUrl = (EditText) findViewById(R.id.editTextNome);

		/* Accedo alle preferenze in modo privato */
		final SharedPreferences prefs = getSharedPreferences("NOSTREPREFS",
				Context.MODE_PRIVATE);
		String nomeSalvato = prefs.getString("nomeSalvato", "");

		if ("".equals(nomeSalvato)) {
			// non c'è preferenza
		} else {
			Toast.makeText(getBaseContext(), "La preferenza è " + nomeSalvato,
					Toast.LENGTH_SHORT).show();
		}

		Button bottone = (Button) findViewById(R.id.buttonCambia);
		bottone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/*
				 * TextView testo = (TextView)findViewById(R.id.textView1);
				 * Log.i("LOGG", testo.getText().toString()); if
				 * (testo.getText()
				 * .toString().equals(getString(R.string.hello_world))) {
				 * testo.setText("Ciao Mondo"); } else {
				 * testo.setText(getString(R.string.hello_world)); }
				 */

				/* Determino il percorso da caricare */
				Log.i("LOGG", testoUrl.getText().toString());

				Intent intent = new Intent(MainActivity.this,
						SecondaActivity.class);
				intent.putExtra("indirizzoDaCaricare", testoUrl.getText()
						.toString());
				startActivity(intent);
			}
		});

		Button bottoneLista = (Button) findViewById(R.id.buttonLista);
		bottoneLista.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						ListaActivity.class);
				startActivity(intent);
			}
		});

		Button bottonePrefs = (Button) findViewById(R.id.buttonPrefs);
		bottonePrefs.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString("nomeSalvato", testoUrl.getText().toString());
				editor.commit();
				Toast.makeText(getBaseContext(), "Nome salvato",
						Toast.LENGTH_SHORT).show();
			}
		});

		Button bottoneListaPersone = (Button) findViewById(R.id.buttonSalva);
		bottoneListaPersone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						ListaPersoneActivity.class);
				startActivity(intent);
			}
		});

		Button bottoneCreaContatto = (Button) findViewById(R.id.buttonCreaContatto);
		bottoneCreaContatto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						CreazioneModificaActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
