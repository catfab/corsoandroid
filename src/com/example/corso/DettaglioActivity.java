package com.example.corso;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DettaglioActivity extends Activity {

	protected String nome;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dettaglio);
		
		nome = getIntent().getExtras().getString("nome");
		
		
		TextView textView = (TextView)findViewById(R.id.textViewNomePersona);
		textView.setText(nome);
		
		ImageView imageView = (ImageView)findViewById(R.id.imageViewImmagine);
		imageView.setImageDrawable(this.getResources().getDrawable(R.drawable.scoiattolo));
		
		Button buttonApri = (Button)findViewById(R.id.buttonApri);
		buttonApri.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("test nome", nome);
				
				Intent intent = new Intent(DettaglioActivity.this,DettaglioWebViewActivity.class);
				intent.putExtra("url", "https://www.google.it/search?q=".concat(nome));
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dettaglio, menu);
		return true;
	}

}
