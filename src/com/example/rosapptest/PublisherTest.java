package com.example.rosapptest;

import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;


public class PublisherTest{

	protected Publisher<std_msgs.String> publisher;

	public PublisherTest(final ConnectedNode connectedNode) {
		publisher = connectedNode.newPublisher("androidRos_test_publisher", std_msgs.String._TYPE);		
	}

	public void sendData(String data){
		if(publisher==null){
			System.out.println("Publisher is null Error...Might need to start your roscore first");
			return;
		}
		std_msgs.String str = publisher.newMessage();
		str.setData(data);
		publisher.publish(str);
	}
}
