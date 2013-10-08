package com.example.corso;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomImageAdapter extends ArrayAdapter<String> {

	private final Context context;
	private final int textViewResourceId;
	private final String[] objects;
	
	public CustomImageAdapter(Context context, int textViewResourceId, String[] objects) {
		super(context, textViewResourceId, objects);

		this.context = context;
		this.textViewResourceId = textViewResourceId;
		this.objects = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(textViewResourceId, parent, false);
		
		ImageView imageViewCella = (ImageView)rowView.findViewById(R.id.imageViewCella);
		TextView textViewCella = (TextView)rowView.findViewById(R.id.textViewCella);
		
		/*Recupero l'id della foto e la setto l'immagine*/
		//int fotoId =context.getResources().getIdentifier("com.example.corso:drawable/scoiattolo.jpg", null, null);
		//imageViewCella.setImageResource(fotoId);
		//altro metodo per fare la stessa cosa, con la differenza che sopra usa identifier
		imageViewCella.setImageDrawable(context.getResources().getDrawable(R.drawable.scoiattolo));
		
		textViewCella.setText(objects[position]);
		return rowView;
	}
}
