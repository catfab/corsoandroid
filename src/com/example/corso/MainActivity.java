package com.example.corso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import UtilsDB.DBAdapter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
		
		//Parte l'async task
		new DownloadFileTask().execute(this);

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

	public class DownloadFileTask extends AsyncTask<Context, Integer, String> {

		String path = "";
		private long contentLength;
		
		private ProgressDialog dialog;
		
		@Override
		protected String doInBackground(Context... arg0) {
			downloadFromUrl();
			
			return "OK";
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			if (this.dialog != null)
				this.dialog.dismiss();
			
			DBAdapter dbAdapter = new DBAdapter(MainActivity.this);
			
			try {
				dbAdapter.open();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			String xmlUrl = "file://" + path + "persone.xml";
			
			Parser parser = new Parser();
			parser.parseXml(xmlUrl);
			
			ArrayList<Persona> arrayParsato = parser.getParsedData();
			
			for (Persona persona : arrayParsato) {
				Cursor cursore =dbAdapter.fetchContattiByFilter("numero = " + persona.getNumTelefono()); 
				if (cursore == null || cursore.getCount() == 0) {
					dbAdapter.creaContatto(persona.getNome(), persona.getCognome(), persona.getDataNascita(), persona.getNumTelefono());
				}
				else {
					Log.i("db", "Contatto scartato perché già esistente");
				}
			}
		}

		@Override
		protected void onPreExecute() {
			
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			
			if (contentLength > 0) {
				dialog = new ProgressDialog(MainActivity.this);
				dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				dialog.setProgress(0);
				
				dialog.setMax((int)contentLength);
				contentLength = 0;
				dialog.setMessage("Caricamento...");
				dialog.show();
			}
			else {
				dialog.setProgress(values[0]);
			}
		}

		@Override
		protected void onCancelled() {
			
		}
		
		public void downloadFromUrl() { //Il nostro metodo di download
            Log.d("dbproj", "enter download from url");
            if(isConnectionAvailable()){
                try {

                    ContextWrapper contextWrapper = new ContextWrapper(MainActivity.this);

                    if(Environment.getExternalStorageDirectory() != null){
                        //esiste una sd, settiamo il path su di essa
                        path = Environment.getExternalStorageDirectory()
                                + "/" +
                                contextWrapper.getPackageName() + "/downloads/";
                    } else {
                        //il telefono ha solo memoria interna
                        path = Environment.getDataDirectory().getAbsolutePath()
                                + File.separator
                                + contextWrapper.getPackageName()
                                + "/downloads/";
                    }

                    Log.v("dbproj", "PATH: " + path);


                    HttpClient client = new DefaultHttpClient();
                    HttpGet getHttp = new HttpGet("http://www.senetech.it/persone.xml");

                    HttpResponse res = client.execute(getHttp);

                    contentLength = res.getEntity().getContentLength();
                    Log.i ("dbproj","content length: " + contentLength);
                    Log.i("dbproj", "response code: " + res.getStatusLine().getStatusCode());
                    if (res.getStatusLine().getStatusCode() < 200 ||
                            res.getStatusLine().getStatusCode() >= 300) {
                    	//Do' un cancel al thread
                        cancel(true);
                    }

                    File file = new File(path);
                    file.mkdirs();

                    String fileName = "persone.xml";

                    File outputFile = new File(file, fileName);

                    long startTime = System.currentTimeMillis();
                    Log.d("DownloadTask", "download iniziato");

                    FileOutputStream fos = new FileOutputStream(outputFile);

                    //ottengo il contenuto
                    InputStream is = res.getEntity().getContent();

                    byte[] buffer = new byte[1024];
                    int len1 = 0;
                    int cntr = 0;

                    while ((len1 = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len1);
                        cntr++;
                        publishProgress(cntr * 1024);
                    }
                    fos.close();
                    is.close();
                    //Flush del contenuto
                    res.getEntity().consumeContent();

                    Log.d("dbproj",
                            "download pronto in  "
                                    + ((System.currentTimeMillis() - startTime) / 1000)
                                    + " sec");

                } catch (IOException e) {
                    Log.i("dbproj","Sono andato in eccezione");
                    e.printStackTrace();
                }
                Log.d("dproj", "exit download from url");

            } else {
            	//Metodo che effettua un run sul UI thread (ovvero il main thread)
            	runOnUiThread(new Thread(new Runnable() {
					
					@Override
					public void run() {
						Toast.makeText(MainActivity.this,"Impossibile effettuare il download",
		                        Toast.LENGTH_SHORT).show();
						
					}
				}));
            }
		}
	}
	
	public boolean isConnectionAvailable () {
		ConnectivityManager connM = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		
		return connM.getActiveNetworkInfo() !=null && connM.getActiveNetworkInfo().isConnectedOrConnecting();
	}
}
