package com.example.rssfeedparse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	
Button b1;
String link1;
DefaultHttpClient httpClient ;
HttpEntity httpEntity = null;
HttpResponse httpResponse = null;
String response, result = " ";
InputStream is = null;
String[] currencylist=new String[14];
String[] finallist=new String[14];
String[] finallist2=new String[14];
String[] currencyvalue=new String[42];
ArrayList<String> currencyvaluelist;
StringBuilder sb;
String[] linkHref =new String[56];
String linkText ;
int i=0,j=0,l=0;
public static final int DIALOG_DOWNLOAD_PROGRESS = 0;

private ProgressDialog mProgressDialog,pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		link1="http://192.168.58.23/test/json.php";
		
		b1=(Button) findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				for(int i=0;i<finallist.length;i++){
//					Toast.makeText(getApplicationContext(), finallist[i], Toast.LENGTH_LONG).show();
//						}
				startDownload(link1);

				
			}
		});
		
	
		
		
	}
	protected void startDownload(String myurl) {
		// TODO Auto-generated method stub
		String url = myurl;

		new DownloadFileAsync().execute(url);

	}
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_DOWNLOAD_PROGRESS:

			mProgressDialog = new ProgressDialog(MainActivity.this);
			mProgressDialog.setMessage("Downloading file..");
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			

			return mProgressDialog;
		default:
			return null;
		}
	}
	
	class DownloadFileAsync extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			
			showDialog(DIALOG_DOWNLOAD_PROGRESS);
			
			
		}

		@Override
		protected String doInBackground(String... aurl) {
			try {								
				
				sb = new StringBuilder();
				httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(aurl[0]);

				httpResponse = httpClient.execute(httpGet);

				is = httpResponse.getEntity().getContent();
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is));
					
					

					String line = "";
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}

							
						result = sb.toString();

				} catch (Exception e) {
					Log.d("Error",
							"Error occured while reading data");
				}
//
			} 
			catch (Exception e) {
//
			}
//			
				
			return result;

		}

		@Override
		protected void onPostExecute(String result) {
			 
			Log.i("Rssfeed", result);
				dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
			

		}
		
	}
	
}
