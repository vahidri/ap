package vClient_pkg;

import java.io.*;
import java.net.Socket;

public class Txs{
	static Socket socketTxs;
	static PrintWriter sout;

	public static void makeTxs(Socket inpSocket)
	{
		socketTxs = inpSocket;
		try {
			sout = new PrintWriter(socketTxs.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean echo() {
		System.out.println("Txs > echo");
		sout.println(Client.valTypeEcho);
		sout.flush();
		long sec1 = System.currentTimeMillis() %10000;
		System.out.println("Txs(5s) > reading the resp to ECHO...");
		while( ( (System.currentTimeMillis() %10000) - sec1) < 5000)
		{
			int x = Client.matchAmongIncoming(Client.valTypeEcho);
			if(x != -1)
			{
				Client.vIncoming.removeElementAt(x);
				return true;
			}
		}
		System.err.println("Txs > echo timeout!");
		return false;
	}

	public static boolean usercheck(String inpUsername) {
		System.out.println("Txs > "+Client.valTypeUsercheck + ":" + inpUsername);
		sout.println(Client.valTypeUsercheck + ":" + inpUsername);
		sout.flush();
		System.out.println("Txs(5s) > reading the resp to usrck...");
		long sec1 = System.currentTimeMillis() %10000;
		while( ( (System.currentTimeMillis() %10000) - sec1) < 5000)
		{
			int x = Client.findSegmentAmongIncoming(Client.valTypeUsercheck, 2);
			if( x != -1)
			{
				if(SM.col(Client.vIncoming.elementAt(x), 4).equals(Client.valRespNewAvailable)) //assumption :D
				{
					Client.vIncoming.removeElementAt(x);
					return true;
				}
				Client.vIncoming.removeElementAt(x);
			}
		}
		return false;
	}

	public static String login(String inpUsername, String inpPass)
	{
		System.out.println("Txs > login:" + inpUsername);
		sout.println(Client.valTypeLogin + ":" + inpUsername + ":" + inpPass);
		sout.flush();
		System.out.println("Txs(5s) > reading the resp to Login...");
		long sec1 = System.currentTimeMillis() %10000;
		while( ( (System.currentTimeMillis() %10000) - sec1) < 5000)
		{
			int x = Client.findSegmentAmongIncoming(Client.valTypeLogin, 2);
			if( x != -1)
			{
				String o = SM.col(Client.vIncoming.elementAt(x), 4);
				Client.vIncoming.removeElementAt(x);
				return o;
			}
		}
		System.err.println("Txs > login timeout!");
		return null;
	}

	public static String geton() {
		System.out.println("Txs > geton: " + Client.myUsername);
		sout.println(Client.valTypeGeton + ":" + Client.myUsername);
		sout.flush();
		System.out.println("Txs(2s) > reading the resp to GetOn...");
		long sec1 = System.currentTimeMillis() %10000;
		while( ( (System.currentTimeMillis() %10000) - sec1) < 2000)
		{
			int x = Client.findSegmentAmongIncoming(Client.valTypeGeton, 2);
			if(-1 != x)
			{
				String o = Client.vIncoming.elementAt(x);
				Client.vIncoming.removeElementAt(x);
				return o;
			}
		}
		System.err.println("Txs > GetOn timeout!");
		return null;
	}

	public static void logout() {
		System.out.println("Txs > logout");
		sout.println(Client.valTypeLogout + ":" + Client.myUsername);
		sout.flush();
		Client.myStatus = Client.valStatusOut;
		System.out.println("Txs(2s) > reading resp of Logout...");
		long sec1 = System.currentTimeMillis() %10000;
		while( ( (System.currentTimeMillis() %10000) - sec1) < 2000)
		{
			int x = Client.findSegmentAmongIncoming(Client.valTypeLogout, 2);
			if(-1 != x)
			{
				Client.vIncoming.removeElementAt(x);
				return;
			}
		}
		System.err.println("Txs > logout timeout!");
	}
	
}
