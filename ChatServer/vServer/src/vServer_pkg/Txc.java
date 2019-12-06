package vServer_pkg;

import java.io.*;
import java.net.Socket;

public class Txc{
	Socket mySocketTxc;
	PrintWriter mySout;
	Clientele mySuperior;
	
	public Txc(Clientele Superior, Socket socketTxc) {
		mySocketTxc = socketTxc;
		mySuperior = Superior;
		try {
			mySout = new PrintWriter(mySocketTxc.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void echo()
	{
		mySout.println(Server.valTypeEcho);
		mySout.flush();
		System.out.println("ECHO answered");
	}

	public void handleUsercheck(String tmpUsername) {
		System.out.println("Txc > handleUsercheck: " + tmpUsername);
		if( Server.getIndex(tmpUsername) == -1)
		{	//means hasn't been used yet; can be "new"ed
			mySout.println(Server.valRespDone + ":" + Server.valTypeUsercheck + ":" + tmpUsername + ":" + Server.valRespNewAvailable);
			mySout.flush();
			System.out.println(tmpUsername+" is Available");
		}
		else
		{
			mySout.println(Server.valRespDone + ":" + Server.valTypeUsercheck + ":" + tmpUsername + ":" + Server.valRespNewTaken);
			mySout.flush();
			System.out.println(tmpUsername+" is Taken");
		}
	}

	public void handleNew(String tmpUsername, String tmpPass) {
		if ( Server.getIndex(tmpUsername) == -1) //usrck: assuming Exist scenario has been covered at Client UI
		{
			System.out.println("Txc > Registering New User");
			Server.vUsers.addElement(tmpUsername);
			Server.vPasses.addElement(tmpPass);
			Server.appendPasswd(tmpUsername , tmpPass);
			
			signIn(tmpUsername, tmpPass);
		}
		else
			System.err.println("Txc > New attempt on existing user \""+tmpUsername+"\"");
	}

	public void handleLogin(String tmpUsername, String tmpPass) {
		int ac = Server.authCheck(tmpUsername, tmpPass);
		System.out.println("Txc > handleLogin: " + tmpUsername);
		switch(ac)
		{
		case Server.valAuthPass:
			mySout.println(Server.valTypeSec +":"+ Server.valTypeLogin +":"+ tmpUsername +":"+ Server.valAnsPass);
			mySout.flush();
			System.out.println("Txc > Auth Passed: " + tmpUsername);
			signIn(tmpUsername, tmpPass);
			break;
		case Server.valAuthAlreadyOn:
			mySout.println(Server.valTypeSec +":"+ Server.valTypeLogin +":"+ tmpUsername +":"+ Server.valAnsAlreadyOn);
			mySout.flush();
			System.out.println("Txc > Auth: Already On: " + tmpUsername);
			break;
		default: //not found OR wrong pass
			mySout.println(Server.valTypeSec +":"+ Server.valTypeLogin +":"+ tmpUsername +":"+ Server.valAnsFail);
			mySout.flush();
			System.out.println("Txc > Auth Failed");
			break;
		}

	}

	
	private void signIn(String tmpUsername, String tmpPass) {
		mySuperior.myUsername = tmpUsername;
		mySuperior.myPass = tmpPass;
		mySuperior.myStatus = Server.valStatusIn;
		System.out.println("Txc > (signIn): " + mySuperior.myUsername);
		++Server.countOfLogin;
	}

	public void handleGeton() {
		String ons=null;
		int onCount=0;
		
		for (Clientele ce: Server.vClientele)
			if (ce.myStatus == Server.valStatusIn)
			{
				ons += ce.myUsername + ":";
				++onCount;
			}
		ons = ons.substring(0, ons.length()-1);
		Server.countOflastOnline = onCount;
		mySout.println(Server.valRespDone + ":" + Server.valTypeGeton + ":" + onCount + ":" + ons);
		mySout.flush();
		System.out.println("Txc > GetOn Sent");
	}

	public void handleLogout() {
		mySout.println(Server.valRespDone + ":" + Server.valTypeLogout + ":" + mySuperior.myUsername);
		++Server.countOfLogout;
		mySuperior.myStatus = Server.valStatusOut;
		System.out.println("Txc > LOGOUT: " + mySuperior.myUsername);
		mySuperior.shutdown();
	}
	
	public void handleUnknown() {
		mySout.println(Server.valAnsUnknown + ":" + mySuperior.myUsername);
		mySout.flush();
	}

	public void send(String inpS) {
		mySout.println(inpS);
		mySout.flush();
	}
	
	public void shutdown() {
		try {
			mySocketTxc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
