package vServer_pkg;

//import java.io.*;
import java.net.*;
import java.util.Vector;

public class Clientele extends Thread {

	Socket mySocketRxc, mySocketTxc;
	Txc myTxc;
	Rxc myRxc;
	
	int myStatus = Server.valStatusNone;
	String myUsername, myPass;
	Vector<String> vIncoming = new Vector<>();
	Vector<String> vMyMsgCl = new Vector<>();
	
	long countOfMyIncoming = 0;
	long countOfMsg = 0;
	
	public Clientele(Socket socketRxc, Socket socketTxc) {
		mySocketRxc = socketRxc;
		mySocketTxc = socketTxc;
		myTxc = new Txc(this, socketTxc);
		myRxc = new Rxc(this, socketRxc);
		myStatus = Server.valStatusConnection;
	}

	
	public void run()
	{
		myRxc.start(); //always listening for incomings!
		while(Server.isRunning)
		{
			if(1 < vMyMsgCl.size())
				System.err.println("C > BIG myMsgCl QUEUE");
			while (0 < vMyMsgCl.size())
			{
				myTxc.send(vMyMsgCl.elementAt(0));
				vMyMsgCl.removeElementAt(0);
			}
			
			if(0 < vIncoming.size())
			{
				++countOfMyIncoming;
				++Server.countOfTotalIncoming;
				if(1 < vIncoming.size())
					System.err.println("C > BIG INCOMING QUEUE");
				String incoming = vIncoming.elementAt(0);
				vIncoming.removeElementAt(0);
				
				String tmpType=null, tmpUsername=null, tmpPass=null;
				String tmpToUsername=null, tmpMsgContent=null;
//				String tmpFilename=null;
				
				tmpType = SM.col(incoming, 1);
				if (!tmpType.equals(Server.valTypeEcho))
					tmpUsername = SM.col(incoming, 2);

				switch(tmpType)
				{
				case Server.valTypeGeton: //2 type:username
					//send content online vector as String
					if (myStatus != Server.valStatusIn)
						System.err.println("C > geton WHEN myStatus != Server.valStatusIn");
					myTxc.handleGeton();
					break;

				case Server.valTypeLogout: //2 type:username
					if (myStatus != Server.valStatusIn)
						System.err.println("C > logout WHEN myStatus != Server.valStatusIn");
					myTxc.handleLogout();
					break;
					
				case Server.valTypeMsgsr: //4 type:username:to:content
					if (myStatus != Server.valStatusIn)
						System.err.println("C > Msgsr WHEN myStatus != Server.valStatusIn");
					++countOfMsg;
					++Server.countOfTotalMsg;
					tmpToUsername = SM.col(incoming, 3);
					tmpMsgContent = SM.col(incoming, 4);
					if (!tmpToUsername.equals(myUsername) )//just checking
					{
						//my client is sending a msg to another client
						//send the msg to tmpToUsername's msgQueue
							//thus making it the Client's duty to handle the bunch of incomings!
							//hence decreasing Server Load (since this doesn't need a msgServer)
						Server.vClientele.elementAt(Server.indexOf(tmpToUsername)).vMyMsgCl.addElement(Server.valTypeMsgcl + ":" + tmpUsername + ":" + tmpToUsername + ":" + tmpMsgContent);
						System.out.println("C > MsgSr enqueued as MsgCl");
					}
					else
						System.err.println("perr C > msgSr aimed at self occurs inside Switch, problem");
					break;

				case Server.valTypeEcho:
					if (myStatus != Server.valStatusConnection)
						System.err.println("C > echo WHEN myStatus != Server.valStatusConnection");
					myTxc.echo();
					SM.printSocketInfo(mySocketTxc);
					break;
				case Server.valTypeUsercheck: //used for attempt New
					if (myStatus != Server.valStatusConnection)
						System.err.println("C > usercheck WHEN myStatus != Server.valStatusConnection");
					System.out.println("C > Request: usercheck: "+tmpUsername);
					myTxc.handleUsercheck(tmpUsername);
					break;
				case Server.valTypeNew:
					if (myStatus != Server.valStatusConnection)
						System.err.println("C > new WHEN myStatus != Server.valStatusConnection");
					System.out.println("C > Request: New User: "+tmpUsername);
					tmpPass= SM.col(incoming, 3);
					myTxc.handleNew(tmpUsername, tmpPass);
					break;
				case Server.valTypeLogin:
					if (myStatus != Server.valStatusConnection)
						System.err.println("C > login WHEN myStatus != Server.valStatusConnection");
					System.out.println("C > Request: Login: "+tmpUsername);
					tmpPass= SM.col(incoming, 3);
					myTxc.handleLogin(tmpUsername, tmpPass);
					break;

				case Server.valTypeFileLs: //2 type:username
					if (myStatus != Server.valStatusIn)
						System.err.println("C > FileLs WHEN myStatus != Server.valStatusIn");
					//$
//					mySout.println(Server.valRespDone + ":" + Server.valTypeFileLs + ":" + getStringFileLs());
//					mySout.flush();
//					System.out.println("TCM > FileLs sent");
					break;

				case Server.valTypeMsgcl: //4 type:username:to:content
					System.err.println("perr C > msgCl at Incoming, Blatant problem");
					break;
					
				default:
					System.err.println("perr C > Unknown Request Type!");
					myTxc.handleUnknown();
					break;
				}

			}
		}
		
		shutdown();
	}


	public void shutdown() {
		myRxc.shutdown();		
		myTxc.shutdown();
		if (myStatus != Server.valStatusOut)//myStatus must've been updated if it's a proper logout
			System.err.println("C > user: " + myUsername + " IMPROPER SHUTDOWN");
	}
}
