package vClient_pkg;

import java.net.Socket;
import java.net.SocketException;

public class SM {
	public static String col(String inpStr , int inpIndex)
	{
		if (inpIndex <= countCol(inpStr))
			return inpStr.split(":", 0)[inpIndex-1];
		return null;
	}
	
	public static int countCol(String inp)
	{
		return inp.split(":", 0).length;
	}
	
	
	public static void printSocketInfo(Socket tmpSocket) {
		System.out.println("/* Socket Info");
		System.out.println("Client) " + tmpSocket.getLocalSocketAddress() +" -> "+ tmpSocket.getRemoteSocketAddress());
		System.out.println("Connected: "+tmpSocket.isConnected());
		System.out.println("closed? " +tmpSocket.isClosed());
		try {
			System.out.println("timeout: " + tmpSocket.getSoTimeout());
			System.out.println("keepAlive: " + tmpSocket.getKeepAlive());
		} catch (SocketException e) {
			e.printStackTrace();
		}
		System.out.println("*/");
	}
	
}
