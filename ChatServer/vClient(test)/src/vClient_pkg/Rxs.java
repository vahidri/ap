package vClient_pkg;

import java.io.*;
import java.net.Socket;


public class Rxs extends Thread {
	Socket mySocketRxs;
	BufferedReader mySin;

	public Rxs(Socket socketRxs)
	{
		mySocketRxs = socketRxs;
		try {
			mySin = new BufferedReader(new InputStreamReader(mySocketRxs.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		while(true)
		{
			try {
				String s = mySin.readLine();
				Client.vIncoming.addElement(s);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
