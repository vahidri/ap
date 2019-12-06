package vServer_pkg;

import java.io.*;
import java.net.*;

public class Entry extends Thread{

	public void run()
	{
		try {
			ServerSocket serverSocketRxc = new ServerSocket(Server.theServerRxcPort);
			ServerSocket serverSocketTxc = new ServerSocket(Server.theServerTxcPort);

			while(Server.isRunning)
			{
				Socket socketRxc = serverSocketRxc.accept();
				socketRxc.setKeepAlive(true);
				Socket socketTxc = serverSocketTxc.accept();
				socketTxc.setKeepAlive(true);
				
				Clientele tmpC = new Clientele(socketRxc, socketTxc);
				Server.vClientele.addElement(tmpC);
				++Server.countOfConnection;
				tmpC.start();
			}
			
			serverSocketRxc.close();
			serverSocketTxc.close();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}


	}
}
