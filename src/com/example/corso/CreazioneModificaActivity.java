package com.example.corso;

import java.sql.SQLException;

import UtilsDB.DBAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreazioneModificaActivity extends Activity {
	private DBAdapter adapter;
	Persona persona;
	long idPersona = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creazione_modifica);
		
		final DBAdapter adapter = new DBAdapter(this);
		persona = getIntent().getParcelableExtra("persona");
		
		if (persona != null) {
			idPersona =persona.getIdPersona();
			
			EditText editTextNome = (EditText)findViewById(R.id.editTextNome);
			editTextNome.setText(persona.getNome());
			EditText editTextCognome = (EditText)findViewById(R.id.editTextCognome);
			editTextCognome.setText(persona.getCognome());
			EditText editTextDataNascita = (EditText)findViewById(R.id.editTextDataNascita);
			editTextDataNascita.setText(persona.getDataNascita());
			EditText editTextNumTelefono = (EditText)findViewById(R.id.editTextNumTelefono);
			editTextNumTelefono.setText(persona.getNumTelefono());
		}
		
		Button buttonSalva = (Button)findViewById(R.id.buttonSalva);
		buttonSalva.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean modificaEseguita = true;
				
				/*Leggo gli oggetti*/
				EditText editTextNome = (EditText)findViewById(R.id.editTextNome);
				String nome = editTextNome.getText().toString(); 
				EditText editTextCognome = (EditText)findViewById(R.id.editTextCognome);
				String cognome = editTextCognome.getText().toString();
				EditText editTextDataNascita = (EditText)findViewById(R.id.editTextDataNascita);
				String dataNascita = editTextDataNascita.getText().toString();
				EditText editTextNumTelefono = (EditText)findViewById(R.id.editTextNumTelefono);
				String numTelefono = editTextNumTelefono.getText().toString();
				
				if (nome.trim().equals("") || cognome.trim().equals("") || dataNascita.trim().equals("") || numTelefono.trim().equals(""))
				{
					Toast.makeText(getBaseContext(), "Compilare tutti i campi", Toast.LENGTH_SHORT).show();
				}
				else {
				
					try {
						adapter.open();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					if (idPersona != -1) {
						modificaEseguita = adapter.updateContatto(idPersona, nome, cognome, dataNascita, numTelefono);
						
						if (modificaEseguita) {
							Toast.makeText(getBaseContext(), "Salvataggio eseguito", Toast.LENGTH_SHORT).show();
						}
						else {
							Log.i("adapter", "updateContattoError");
						}
					}
					else {
						idPersona = adapter.creaContatto(nome, cognome, dataNascita, numTelefono);
						
						if (idPersona != -1) {
							Toast.makeText(getBaseContext(), "Creazione contatto eseguita", Toast.LENGTH_SHORT).show();
						}
						else {
							Log.i("adapter", "creaContattoError");
						}
					}
					
					adapter.close();
					
					/*Intent intent = new Intent(CreaModificaContattoActivity.this,ListaPersoneActivity.class);
					startActivity(intent);*/
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.creazione_modifica, menu);
		return true;
	}

}
