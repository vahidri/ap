package vClient_pkg;

import java.io.*;
import java.net.*;
import java.util.Vector;

public class Client {
	final static String valRespDone = "done";
	final static String valTypeEcho = "echo";
	final static String valTypeUsercheck = "usrck";
		final static String valRespNewAvailable = "ok";
		final static String valRespNewTaken = "no";
	final static String valTypeLogin = "login";
		final static String valAnsFail = "fail";
		final static String valAnsAlreadyOn = "inuse";
		final static String valAnsPass = "pass";
		final static String valAnsUnknown = "unknwn";
	final static String valTypeLogout = "logout";
	final static String valTypeNew = "new";
	final static String valTypeMsgsr = "msgsr";
	final static String valTypeMsgcl = "msgcl";
	final static String valTypeGeton = "geton";
	final static String valTypeSec = "sec";
	final static String valTypeFileLs = "filels";
	final static String valTypeFileUp = "fileup";
	final static String valTypeFileDl = "filedl";
	final static String valTypeFileLu = "filelu";
	final static int valAuthNotFound = 404;
	final static int valAuthWrongPass = 401;
	final static int valAuthPass = 1;
	final static int valAuthAlreadyOn = 66;
	final static int valStatusNone = 6; //by initials on T9
	final static int valStatusConnection = 3; 
	final static int valStatusIn = 4;
	final static int valStatusOut = 9;
	

	
	static String theServerAddr = "localhost";
	static int theServerRxcPort = 9998;
	static int theServerTxcPort = 9999;
	static int theFileServerPort = 10000;

	static UIstart uiStart = new UIstart();
	static UIlogin uiLogin = new UIlogin();
	static UInew uiNew = new UInew();
	static UImsg uiMsg = new UImsg();
	
	//I could build a 'Superior' style but not needed! static will do.
	static Socket mySocketRxs;
	static Socket mySocketTxs;
//	static Txs myTxs;
	static Rxs myRxs;
	static Vector<String> vIncoming = new Vector<>();
	
	static String myUsername, myPass;
	static int myStatus = valStatusNone;

	public static void main(String [] args)
	{
		System.out.println("vClient: MAIN BEGIN");
		showStart();
		System.out.println("vClient: MAIN END");
	}

	public static void createSockets() {
		try {
			mySocketTxs = new Socket(theServerAddr, theServerRxcPort);
			mySocketTxs.setKeepAlive(true);
			mySocketRxs = new Socket(theServerAddr, theServerTxcPort);
			mySocketRxs.setKeepAlive(true);
			System.out.println("Client > createSockets; Success (server found and connected)");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createIO() {
		Txs.makeTxs(mySocketTxs);
		myRxs = new Rxs(mySocketRxs);
		myRxs.start();
		System.out.println("Client > createIO; Success");
	}
	
	public static int matchAmongIncoming(String inpS)	
	{
		for(int i=0;i<vIncoming.size(); i++)
			if(vIncoming.elementAt(i).equals(inpS))
				return i;
		return -1;
	}
	
	public static int findSegmentAmongIncoming(String inpSeg, int place) //only finds first in order	
	{
		String q;
		for(int i=0;i<vIncoming.size(); i++){
			q = SM.col(vIncoming.elementAt(i), place);
			System.out.println(q);
			if(q!=null)
				if(q.equals(inpSeg))
				return i;
		}
		return -1;
	}
	
	
	static void showStart()
	{
		uiStart.setSize(300,300);
		uiStart.setVisible(true);
	}
	
	static void showLogin()
	{
		uiLogin.setSize(200,150);
		uiLogin.setVisible(true);
	}

	static void showNew()
	{
		uiStart.setSize(300,300);
		uiStart.setVisible(true);
	}

	static void showMsg()
	{
		uiStart.setSize(300,300);
		uiStart.setVisible(true);
	}

	
	
	
}
