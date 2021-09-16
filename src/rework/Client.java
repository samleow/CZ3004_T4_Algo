package src.rework;

import java.io.DataOutputStream;
import java.net.Socket;

// Client in java
// Server planning to do in python on RPI
public class Client
{
	private static Client _instance = null;
	
	// Set IP and PORT here !!
	private static final String SERVER_IP = "localhost";
	private static final int SERVER_PORT = 1111;
	
	private Socket _s = null;
	
	private Client()
	{
		// createSocket();
	}
	
	public static Client getInstance()
	{
		if(_instance == null)
			_instance = new Client();
		return _instance;
	}
	
	public void createSocket()
	{
		try
		{
			if(_s == null || _s.isClosed())
				_s = new Socket(SERVER_IP,SERVER_PORT);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public void closeSocket()
	{
		try
		{
			if(_s != null && !_s.isClosed())
				_s.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	// Function to call from algo to send commands to STM
	// eg.:
	// Client.getInstance().sendMessage("MOV 1");
	public void sendMessage(String mssge)
	{
		try
		{
			createSocket();
			
			DataOutputStream os = new DataOutputStream(_s.getOutputStream());
			os.writeUTF(mssge);
			os.flush();
			os.close();
			
			closeSocket();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
}
