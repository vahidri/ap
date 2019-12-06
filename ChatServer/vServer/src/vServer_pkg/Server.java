package vServer_pkg;

import java.io.*;
import java.util.Vector;

public class Server {
	final static int theServerRxcPort = 9998;
	final static int theServerTxcPort = 9999;
	final static int theFileServerPort = 10000;

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
	
	final static String addrPasswd = "passwd.txt"; //plain text!
	final static String addrFileServer = "files"; //the folder which the client files are stored at

	
	static long countOfConnection = 0;
	static long countOfLogin = 0;
	static long countOfLogout = 0;
	static long countOfTotalIncoming = 0;
	static long countOfTotalMsg = 0;

	static int countOflastOnline= 0;
	
	
	
	static boolean isRunning = false;
	static Vector <String> vUsers= new Vector<>();
	static Vector <String> vPasses= new Vector<>();
	static Vector <Clientele> vClientele = new Vector<>();
	
	
	static Entry entry;
	static FileServer fileServer;
	
	public static void main(String [] args)
	{
		System.out.println("vServer: MAIN END");
		isRunning = true;
		
		loadPasswd();
		
		entry = new Entry();
		entry.start();
		
		fileServer = new FileServer();
		fileServer.start();
		
		ServerUI serverUI = new ServerUI();
		serverUI.setSize(300,300);
		serverUI.setLocation(300,300);
		serverUI.setVisible(true);
		System.out.println("vServer: MAIN END");
	}
	
	private static void loadPasswd()
	{
		vUsers.clear();
		vPasses.clear();
		try {
			BufferedReader in = new BufferedReader(new FileReader(addrPasswd));
			String s;
			while( (s=in.readLine()) != null)
			{
				vUsers.addElement(SM.col(s, 1));
				vPasses.addElement(SM.col(s, 2));
			}
			System.out.println("Server > loadPasswd, loaded: " + vUsers.size());
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void appendPasswd(String inpUsername, String inpPass) {
		System.out.println("Server > append Passwd for: " + inpUsername);
		try {
			FileWriter out = new FileWriter(addrPasswd, true);
			out.append(inpUsername + ":" + inpPass +"\r\n");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static int authCheck(String inpUsername, String inpPass) {
		int ind = getIndex(inpUsername);
		if ( -1 == ind) //existence check
			return valAuthNotFound;
		if (-1 < indexOf(inpUsername) )
			return valAuthAlreadyOn;
		else
			if (inpPass.equals(vPasses.elementAt(ind)) )
				return valAuthPass;
			else
				return valAuthWrongPass;
	}

	
	static int getIndex(String inpUser) //for Registered Users
	{
		for (int i=0 ; i<vUsers.size() ; i++) 
			if (vUsers.elementAt(i).equals(inpUser) )
				return i;
		return -1;
	}

	static int indexOf(String inpUsername) //for Online Users (Connected Clients)
	{
		for(int i=0; i<vClientele.size(); i++)
			if (vClientele.elementAt(i).myUsername.equals(inpUsername))
				return i;
		return -1;
	}


}
