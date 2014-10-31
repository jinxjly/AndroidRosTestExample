package com.example.rosapptest;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	public static final long TIME_DELAY_MS = 100; 

    public ROSComm comm;
    public Handler handler;
    
    public TextView displayText;
    public Runnable updateTextRunnable;
		 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		displayText = (TextView)findViewById(R.id.displayText);
		
        //Set up ROS communication
        comm = new ROSComm();
		ROSNodeMaker.runROSNode(comm, ROSComm.nodeName);
		
		//Set up Handler
		handler = new Handler();

		//Kind of a hacky way to update the textview periodically..
		updateTextRunnable = new Runnable(){  
			  public void run() {  				  
				  if(comm.getSubscriberTest()!=null){
					  if(comm.getSubscriberTest().hasData()){
						  String msg = comm.getSubscriberTest().fetchData();
						  displayText.setText(msg);
					  }
				  }
			      handler.postAtTime(updateTextRunnable, SystemClock.uptimeMillis() + TIME_DELAY_MS);
			     }  
			 };  
			 
		//Start 		
		handler.postAtTime(updateTextRunnable, SystemClock.uptimeMillis() + TIME_DELAY_MS);

	}		

	public void sendROSMessage(View view){
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String msg = editText.getText().toString();
    	comm.getPublisherTest().sendData(msg);	
    }

}
