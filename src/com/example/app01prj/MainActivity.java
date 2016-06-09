package com.example.app01prj;

import android.support.v7.app.ActionBarActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.w3c.dom.Text;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity{
	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//si aggancia all'activity_main.xml
		setContentView(R.layout.activity_main);
		// per agganciare da codice il listener al button accedi
		//Button b = (Button)findViewById(R.id.button1);
		//b.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClickAccedi(View v) throws FileNotFoundException {
		/*
		 * 1) definiamo il metodo da agganciare al click del pulsante
		 * 2)(Attivo il listener) tasto dx sul pulsante da attivare->other properties->inherited from view -> 
		 * 	  on click...-> inserire il nome del metodo (in questo caso onClickAccedi)
		 * 3) gestiamo l'azione successiva al click
		 */
		//TextView v1 = (TextView)findViewById(R.id.textView2);
		//v1.setText("MODIFICATO");
		String username="", psw = "";
		
		//riferimento al campo di id=username tramite oggetto usernameView di tipo Textview
		TextView usernameView =(TextView)findViewById(R.id.username);
		//ritorna la stringa associata all'oggetto usernameView
		username = usernameView.getText().toString();
		
		TextView pswView = (TextView)findViewById(R.id.psw);
		psw = pswView.getText().toString();
		
		if(username.length()<11 && psw.length()>7 ){
			if(!cercaUtente(username, psw, false) )
				Toast.makeText(getApplicationContext(),
							"Account inesistente o dati errati", Toast.LENGTH_LONG).show();
			else{// al click su accedi parte HomeActivity
				Intent intent = new Intent(this, HomeActivity.class);
				//this riferimento a questa activity(MainActivity), HomeActivity.class
				//-> activity a cui si vuole puntare
				Bundle b = new Bundle();// passiamo alla nuova activity lo user
				//tramite l'oggetto bundle
				b.putString("user", username);
				// inseriamo nel bundle  la coppia chiave = "user" e valore = username
				intent.putExtras(b);//tramite il bundle passiamo all'intent la coppia
				startActivity(intent);
				//Toast.makeText(getApplicationContext(),
					//"Ben tornato " + username +" ;" , Toast.LENGTH_LONG).show();
			}
			
		}
		
		else{
			if(username.length() > 10  || username.contains(" ") ){
				Toast.makeText(getApplicationContext(),
					"Errore username: "+
					"Puoi inserire fino a 10 caratteri senza spazi"		
					, Toast.LENGTH_LONG).show();
			
			}
		
			if(psw.length() < 8){
				Toast.makeText(getApplicationContext(),
					"Errore password: "+
					"La password DEVE contenere almeno 8 caratteri"		
					, Toast.LENGTH_LONG).show();
			
			}
		}
		
			
	}
	
	boolean cercaUtente(String username, String psw, boolean soloUser) throws FileNotFoundException{
		//Recuperiamo il path del file in cui andare a verificare le credenziali
		String nomeFileAccounts = getApplicationContext().getFilesDir().getPath() +
		"/" +getString(R.string.nomeFileAccounts);
		
		File f = new File(nomeFileAccounts);
		boolean utenteEsistente = false;
		
		if(f.exists()){
			
			//lettura contenuto file;
			FileReader fileLeggi = new FileReader(nomeFileAccounts);
			BufferedReader br = new BufferedReader(fileLeggi);
			
			String rigaLetta = "";
			String[] datiAccount = null;
			
			try {
				while( (rigaLetta = br.readLine() ) != null &&
						!utenteEsistente){
					datiAccount = rigaLetta.split(" ");//split restituisce un vettore di stringhe
					// attenzione per il confronto delle stringhe
					// è necessario utilizzare il metodo compare
					//altrimenti con == confronta i puntatori
					utenteEsistente = datiAccount[0].compareTo(username) == 0;
					if(!soloUser)
							utenteEsistente = 
								utenteEsistente && datiAccount[1].compareTo(psw) == 0;
				}
				
				br.close();
				fileLeggi.close();
				
				
			} 
			
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
			
		else
			try {
				f.createNewFile();
				Toast.makeText(getApplicationContext(),
						"Creazione file Utenti" , 
						Toast.LENGTH_LONG).show();
			} 	
			catch (IOException e) 	{
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Toast.makeText(getApplicationContext(),
						"Creazione file Utenti fallita (spazio esaurito?)" , 
						Toast.LENGTH_LONG).show();
			}
		
		return utenteEsistente;
	}
	
	
	
	public void onClickRegistrami(View v){
		String username="", psw = "";
		
		//riferimento al campo di id=username tramite oggetto usernameView di tipo Textview
		TextView usernameView =(TextView)findViewById(R.id.username);
		//ritorna la stringa associata all'oggetto usernameView
		username = usernameView.getText().toString();
		
		TextView pswView = (TextView)findViewById(R.id.psw);
		psw = pswView.getText().toString();
		
		if(username.length()<11 && psw.length()>7 ){
			try {
				if(cercaUtente(username, psw,true) )
					Toast.makeText(getApplicationContext(),
								"Username già in uso", Toast.LENGTH_LONG).show();
				else{
					String nomeFileAccounts = getApplicationContext().getFilesDir().getPath() +
							"/" +getString(R.string.nomeFileAccounts);
					FileWriter fw = new FileWriter(nomeFileAccounts, true);
					fw.append(username+" "+psw+"\n");
					fw.close();
					Toast.makeText(getApplicationContext(),
							"Nuovo account registrato\n Benvenuto "+username+";",
								Toast.LENGTH_LONG).show();
					
					
				}
				
			} 
			
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		else{
			if(username.length() > 10  || username.contains(" ") ){
				Toast.makeText(getApplicationContext(),
					"Errore username: "+
					"Puoi inserire fino a 10 caratteri senza spazi"		
					, Toast.LENGTH_LONG).show();
			
			}
		
			if(psw.length() < 8){
				Toast.makeText(getApplicationContext(),
					"Errore password: "+
					"La password DEVE contenere almeno 8 caratteri"		
					, Toast.LENGTH_LONG).show();
			
			}
		}	
		
		
		
		
	}
	
	
	
	
		
}

