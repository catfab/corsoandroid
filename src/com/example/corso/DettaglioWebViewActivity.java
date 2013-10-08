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
import android.widget.TextView;
import android.widget.Toast;

public class DettaglioWebViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dettaglio_web_view);
		
		String myUrl = getIntent().getExtras().getString("url");
			
		WebView webView = (WebView)findViewById(R.id.webViewDettaglio);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		
		if (isConnectionAvailable()) {
			webView.loadUrl(myUrl);
		}
		else {
			Toast.makeText(getBaseContext(), "Non è attiva alcuna connessione", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dettaglio_web_view, menu);
		return true;
	}
	
	private boolean isConnectionAvailable () {
		ConnectivityManager connM = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		
		return connM.getActiveNetworkInfo() !=null && connM.getActiveNetworkInfo().isConnectedOrConnecting();
	}
}
