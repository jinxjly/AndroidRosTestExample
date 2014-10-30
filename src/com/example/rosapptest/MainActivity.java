package com.example.rosapptest;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    public ROSComm comm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
					
        //Set up ROS communication
        comm = new ROSComm();
		ROSNodeMaker.runROSNode(comm, ROSComm.nodeName);	
	}

	public void sendROSMessage(View view){
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String msg = editText.getText().toString();
    	comm.getPublisherTest().sendData(msg);	
    }

}
