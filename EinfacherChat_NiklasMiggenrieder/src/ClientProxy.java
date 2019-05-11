import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.DefaultListModel;
import javax.swing.JTextArea;

public class ClientProxy extends Thread
{
	private PrintWriter writer;
	private BufferedReader reader;
	private Socket client;
	private ControlServer server;
	private JTextArea messagesArea;
	
	public ClientProxy(Socket client, ControlServer server, JTextArea messagesArea)
	{	
		try
		{
			this.client = client;
			this.server = server;
			this.messagesArea = messagesArea;
			
			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		String message;
		
		try
		{
			while(client.isConnected() == true)
			{
				message = reader.readLine();
				
				if(message.equals("exit")) 
				{
					client.close();
				}
				else
				{
					String[] s = message.split(";");
					if(s.length == 2)
					{
						switch(s[0])
						{
							case "1":
								writeInArea(s[1]);
								System.out.println("Mit Protokoll 1!");
								break;
							case "2":
								writeInArea(s[1]);
								System.out.println("Mit Protokoll 2!");
								break;
							default:
								System.out.println("Ungültiges Protokoll!");
								break;			
						}
					}
					else
					{
						writeInArea(message);
						System.out.println("Ohne Protokoll!");
					}
				}
			}
		}
		catch(SocketException ex)
		{
			System.out.println("Verbindung unterbrochen!");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	private void writeInArea(String message)
	{
		System.out.println("vom Client: \n" + message);
		messagesArea.append(message + "\n");
	}
}
