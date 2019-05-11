import java.awt.EventQueue;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ControlServer extends Thread
{
	private int port;
	private JTextArea messagesArea;
	private JTextField textFieldPort;
	private JTextField text;
	private JLabel status;
	private ArrayList<ClientProxy> proxyList;
	private ServerSocket server;
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ServerGui frame = new ServerGui();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	public ControlServer(JTextArea messagesArea, JTextField port, JTextField text, JLabel status)
	{
		this.messagesArea = messagesArea;
		this.textFieldPort = port;
		this.text = text;
		this.status = status;
	}

	public void run()
	{
		try
		{	
			while(!isInterrupted())
			{
				Socket client = server.accept();
				ClientProxy proxy = new ClientProxy(client, this, messagesArea);
				proxy.start();
				proxyList.add(proxy);
			}
		}
		catch(SocketException ex)
		{
			System.out.println("Verbindung wurde getrennt!");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void serverStart(int port)
	{
		this.port = port;
		
		if(runServer())
		{
			this.start();
		}
	}

	private boolean runServer()
	{
		try
		{
			server = new ServerSocket(port);
			System.out.println("Server wurde gestartet!");
			this.proxyList = new ArrayList<>();
			return true;
		} 
		catch (IOException e)
		{
			System.out.println("Server konnte nicht gestartet werden!");
			e.printStackTrace();
			return false;
		}		
	}

	public void serverEnd()
	{
		try
		{
			this.interrupt();
			server.close();
		} 
		catch (IOException e)
		{			
			e.printStackTrace();
		}
	}	
}
