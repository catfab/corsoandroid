package com.example.corso;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SecondaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seconda);
		WebView mioWeb = (WebView)findViewById(R.id.webViewNostra);
		final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar1);
		
		mioWeb.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				progressBar.setVisibility(View.INVISIBLE);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}
		});
		
		if (isConnectionAvailable()) {
			// La webview carica la pagina passata attraverso gli extras
			mioWeb.loadUrl(getIntent().getExtras().getString("indirizzoDaCaricare"));
		}
		else {
			/*Toast.makeText(getBaseContext(), "Non è attiva alcuna connessione", Toast.LENGTH_SHORT).show();*/
			AlertDialog alert = new AlertDialog.Builder(this).create();
			alert.setTitle("Attenzione");
			alert.setMessage("Non è possibile connettersi ad internet");
			alert.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
				});
			
			alert.setCancelable(false); //non ascolta il tasto "indietro"
			alert.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.seconda, menu);
		return true;
	}

	public boolean isConnectionAvailable () {
		ConnectivityManager connM = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		
		return connM.getActiveNetworkInfo() !=null && connM.getActiveNetworkInfo().isConnectedOrConnecting();
	}
}
