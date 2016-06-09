package com.example.app01prj;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

@SuppressLint("NewApi")
public class HomeActivity extends ActionBarActivity {

	String user = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		//recuperiamo l'intento attraverso cui è stata fatta partire questa activity
		Intent i = getIntent();
		// memorizzo nella Stringa us il contenuto tramite il label "user"
		user = i.getStringExtra("user");
		Toast.makeText(getApplicationContext(),
				"Ben tornato " + user +" ;" , Toast.LENGTH_LONG).show();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// crea il menù nell'activity home
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch(item.getItemId() ){
			case R.id.action_capitali:
				Intent intent = new Intent(this, CapitaliActivity.class);
				Bundle b = new Bundle();
				b.putString("user", user);
				intent.putExtras(b);
				startActivity(intent);
				break;
			case R.id.action_fiumi:
				break;
			case R.id.action_laghi:
				break;
			case R.id.action_monumenti:
				break;
		
			default:
				return super.onOptionsItemSelected(item);
			
		}
		return true;
	}
	
	
}
