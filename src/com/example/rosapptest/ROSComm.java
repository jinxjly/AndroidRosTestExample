package com.example.rosapptest;

import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;


public class ROSComm extends AbstractNodeMain{

	public static final String nodeName = "androidRos";

	//ROS Publishers
	public PublisherTest myPub; 
	//ROS Subscribers
	public SubscriberTest mySub;
	
	public ROSComm() {
	}

	public GraphName getDefaultNodeName() {
		return null;
	}
	
	public void onStart(final ConnectedNode connectedNode){
		myPub = new PublisherTest(connectedNode);
		mySub = new SubscriberTest(connectedNode);
	}
	
	public PublisherTest getPublisherTest(){
		return this.myPub;
	}
	
	public SubscriberTest getSubscriberTest(){
		return this.mySub;
	}
}
