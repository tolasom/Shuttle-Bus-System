package com.client_mail;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Test2 {

	public static void main(String args[]){
		InetAddress ip;
		  try {

			ip = InetAddress.getLocalHost();
			System.out.println("Current IP address : " + ip.getHostAddress());

		  } catch (UnknownHostException e) {

			e.printStackTrace();

		  }
	}
}
