package com.example.rosapptest;

import java.net.URI;
import java.net.URISyntaxException;

import org.ros.exception.RosRuntimeException;
import org.ros.node.DefaultNodeMainExecutor;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMain;
import org.ros.node.NodeMainExecutor;

import com.google.common.base.Preconditions;

/**
 * This class configures and runs a ROS node that executes the functionality of a
 * given NodeMain object.
 * 
 * **********************************************************************
 * FOR THIS TO WORK, YOU MUST FOLLOW THESE STEPS!!!!
 * 1) Set the static variables below for each ROS machine, this one and the master.
 * 2) Set the ROS_IP environment variable in the ROS master machine. It is critical
 * that (a) ROS_IP can be used for ssh'ing (e.g., ssh $ROS_IP) and (b) ROS_IP is set
 * in every window that runs roscore or a ROS node from command line (always before 
 * running them, of course.)
 * 
 * Step 2 can be made automatic by adding 
 * 
 * export ROS_IP="$(/sbin/ifconfig eth1 | grep 'inet addr:' | cut -d: -f2 | awk '{print $1}')"
 * 
 * to .bashrc, .profile, etc. of the machine running roscore.
 * **********************************************************************
 * 
 * For debugging:
 * - try roswtf command on ROS master machine **while problems are occurring**, 
 *   such as nodes not communicating
 * - read http://wiki.ros.org/ROS/Tutorials/MultipleMachines
 * - maybe read http://wiki.ros.org/ROS/NetworkSetup (it was recommended on ROS Answers, but I haven't used it)
 * 
 * 
 * @author bradknox
 *
 */
public class ROSNodeMaker {
			
	/*
	 *  IP address of the machine running roscore. It might be possible to use the machine's
	 *  hostname instead, though that didn't work in one limited test with the ros master 
	 *  machine being run on Ubuntu in Parallels (where hostname was "ubuntu").
	 */

	public static String ROS_MASTER_IP = "192.168.5.242"; 

	static String ROS_MASTER_URI = "http://" + ROS_MASTER_IP + ":11311/"; // typically don't change this one; should match ROS_MASTER_URI displayed when roscore is run

	/*
	 * Hostname of the local machine on which this node will run. Running "hostname" in
	 * the terminal will provide the correct string value. 
	 * 
	 * hostnames are not typically resolved by Android, so IP addresses should be inserted instead.
	 */
	public static String ROS_LOCAL_ADDR = "192.168.5.234";

	
	public static void runROSNode(NodeMain nodeMain, String nodeName) {
		/*
		 * Configure node
		 */
		NodeConfiguration nodeConfiguration = makeNodeConfig(nodeName);
		Preconditions.checkState(nodeMain != null);

		/*
		 * Execute node
		 */
		NodeMainExecutor nodeMainExecutor = DefaultNodeMainExecutor.newDefault();
		nodeMainExecutor.execute(nodeMain, nodeConfiguration);

	}
	

	
	/*
	 * Configures node to be able to communicate with roscore
	 */
	private static NodeConfiguration makeNodeConfig(String nodeName) {
		
		URI uri = NodeConfiguration.DEFAULT_MASTER_URI;
		try {
			if (!ROS_MASTER_IP.isEmpty()) {
				uri = new URI(ROS_MASTER_URI);
			}
		} catch (URISyntaxException e) {
			throw new RosRuntimeException("Invalid master URI: " + uri);
		}
		
		if (ROS_LOCAL_ADDR == null) {
			System.out.println("ROS_LOCAL_ADDR is null. Check HostAddressGetter.getHostAddress().");		
		}
		else {
			System.out.println("ROS_LOCAL_ADDR set to " + ROS_LOCAL_ADDR);
		}
	
		
		NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(ROS_LOCAL_ADDR, uri);
		nodeConfiguration.setNodeName(nodeName);
		
		//		nodeConfiguration.setParentResolver(buildParentResolver());
		//		nodeConfiguration.setRosRoot(getRosRoot());
		//		nodeConfiguration.setRosPackagePath(getRosPackagePath());	
		return nodeConfiguration;
	}
}
