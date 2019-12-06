package vServer_pkg;

import java.io.*;
import java.net.Socket;

public class Rxc extends Thread {
	Socket mySocketRxc;
	BufferedReader mySin;
	Clientele mySuperior;
	
	public Rxc(Clientele Superior, Socket socketRxc) {
		mySocketRxc = socketRxc;
		mySuperior = Superior;
		try {
			mySin = new BufferedReader(new InputStreamReader(mySocketRxc.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run()
	{
		while(Server.isRunning)
		{
			try {
				String s = mySin.readLine();
				mySuperior.vIncoming.addElement(s);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void shutdown() {
		try {
			mySocketRxc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.stop();
	}

}
