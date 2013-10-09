package com.example.corso;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class DettaglioContattoActivity extends Activity {

	private Persona persona;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dettaglio_contatto);
		
		persona = getIntent().getParcelableExtra("persona");
		
		if (persona != null) {		
			TextView editTextNome = (TextView)findViewById(R.id.textViewNome);
			editTextNome.setText(persona.getNome());
			TextView editTextCognome = (TextView)findViewById(R.id.textViewCognome);
			editTextCognome.setText(persona.getCognome());
			TextView editTextDataNascita = (TextView)findViewById(R.id.textViewDataDiNascita);
			editTextDataNascita.setText(persona.getDataNascita());
			TextView editTextNumTelefono = (TextView)findViewById(R.id.textViewNumeroDiTelefono);
			editTextNumTelefono.setText(persona.getNumTelefono());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dettaglio_contatto, menu);
		return true;
	}

}
