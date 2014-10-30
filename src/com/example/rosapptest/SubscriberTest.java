package com.example.rosapptest;

import org.ros.message.MessageListener;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

public class SubscriberTest implements MessageListener<std_msgs.String> {
	protected Subscriber<std_msgs.String> subscriber;

	public SubscriberTest(final ConnectedNode connectedNode) {
		subscriber = connectedNode.newSubscriber("androidRos_test_subscriber", std_msgs.String._TYPE);
		subscriber.addMessageListener(this);
	}
	
	public void onNewMessage(std_msgs.String message) {
		String data = message.getData();
		if(data != null){
			System.out.println(data);
		}
	}
}
