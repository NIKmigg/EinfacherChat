import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ControlClient extends Thread
{
	private JTextField textFieldText;
	private JLabel status;
	private JComboBox<String> comboBoxServer;
	private String localhost;
	private PrintWriter writer;
	private BufferedReader reader;
	private Socket client;
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ClientGui frame = new ClientGui();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	public ControlClient(JTextField textFieldText, JLabel lblStatus, JComboBox<String> comboBoxServer)
	{
		this.textFieldText = textFieldText;
		this.status = lblStatus;
		this.comboBoxServer = comboBoxServer;
		localhost = comboBoxServer.getSelectedItem().toString();
	}

	public void run()
	{
		try
		{
//			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			writer = new PrintWriter(client.getOutputStream());
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void clientStart()
	{
		if(connectToServer())
		{
			this.start();
		}
	}

	private boolean connectToServer()
	{
		try
		{
			client = new Socket(localhost, 8008);
			System.out.println("Netzwerkverbindung hergestellt!");
			return true;
		} 
		catch (UnknownHostException e)
		{
			System.out.println("Netzwerkverbindung konnte nicht hergestellt werden!");
			return false;
		} 
		catch (IOException e)
		{
			System.out.println("Netzwerkverbindung konnte nicht hergestellt werden!");
			return false;
		}
	}

	public void sendMessage()
	{
		writer.write(textFieldText.getText().toString() + "\n");
		writer.flush();
		
		textFieldText.setText("");
		textFieldText.requestFocus();
	}

	public void clientEnd()
	{
		writer.write("exit\n");
		writer.flush();
		this.interrupt();
		System.out.println("Client meldet sich ab");
	}
}
