package com.example.rosapptest;

import org.ros.message.MessageListener;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

public class SubscriberTest implements MessageListener<std_msgs.String> {
	protected Subscriber<std_msgs.String> subscriber;
	protected String data = null;

	public SubscriberTest(final ConnectedNode connectedNode) {
		subscriber = connectedNode.newSubscriber("androidRos_test_subscriber", std_msgs.String._TYPE);
		subscriber.addMessageListener(this);
	}
	
	public void onNewMessage(std_msgs.String message) {		
		data = message.getData();
//		if(data!=null){
			System.out.println(data);
//		}
	}
	
	public boolean hasData(){
		return (data!=null)? true : false;
	}
	
	public String fetchData(){
		String temp = data;
		this.data = null;
		return temp;
	}
}

