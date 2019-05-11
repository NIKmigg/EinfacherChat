import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.JList;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class ServerGui extends JFrame
{

	private JPanel contentPane;
	private JLabel lblNachrichten;
	private JLabel lblPortnr;
	private JTextField textFieldPortnr;
	private JButton btnStart;
	private JTextField textField;
	private JLabel lblFehlermeldung;
	private JTextArea textAreaMessages;
	private ControlServer control;
	private JButton btnBeenden;

	public ServerGui()
	{
		setTitle("Server");
		initialize();
		
		control = new ControlServer(textAreaMessages, textFieldPortnr, textField, lblFehlermeldung);
		
		textFieldPortnr.addActionListener(l ->
		{
			// Button erscheint erst, wenn eine Portnummer mit Enter bestätigt wird
			// Die Portnummer die beim Client als localhost eingestellt ist, ist die 8008
			if(textFieldPortnr.getText() != "")
			{
				btnStart.setVisible(true);
			}
		});
		
		btnStart.addActionListener(e ->
		{
			control.serverStart(Integer.parseInt(textFieldPortnr.getText()));
		});
		
		btnBeenden.addActionListener(e -> 
		{
			control.serverEnd();
		});
	}
	
	private void initialize()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 513, 260);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_lblNachrichten = new GridBagConstraints();
		gbc_lblNachrichten.anchor = GridBagConstraints.WEST;
		gbc_lblNachrichten.insets = new Insets(0, 0, 5, 5);
		gbc_lblNachrichten.gridx = 0;
		gbc_lblNachrichten.gridy = 0;
		contentPane.add(getLblNachrichten(), gbc_lblNachrichten);
		GridBagConstraints gbc_lblPortnr = new GridBagConstraints();
		gbc_lblPortnr.insets = new Insets(0, 0, 5, 0);
		gbc_lblPortnr.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPortnr.gridx = 1;
		gbc_lblPortnr.gridy = 1;
		contentPane.add(getLblPortnr(), gbc_lblPortnr);
		GridBagConstraints gbc_textFieldPortnr = new GridBagConstraints();
		gbc_textFieldPortnr.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldPortnr.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPortnr.gridx = 1;
		gbc_textFieldPortnr.gridy = 2;
		contentPane.add(getTextFieldPortnr(), gbc_textFieldPortnr);
		GridBagConstraints gbc_textAreaMessages = new GridBagConstraints();
		gbc_textAreaMessages.gridheight = 3;
		gbc_textAreaMessages.insets = new Insets(0, 0, 5, 5);
		gbc_textAreaMessages.fill = GridBagConstraints.BOTH;
		gbc_textAreaMessages.gridx = 0;
		gbc_textAreaMessages.gridy = 1;
		contentPane.add(getTextAreaMessages(), gbc_textAreaMessages);
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.fill = GridBagConstraints.BOTH;
		gbc_btnStart.insets = new Insets(0, 0, 5, 0);
		gbc_btnStart.gridx = 1;
		gbc_btnStart.gridy = 3;
		contentPane.add(getBtnStart(), gbc_btnStart);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 4;
		contentPane.add(getTextField(), gbc_textField);
		GridBagConstraints gbc_lblFehlermeldung = new GridBagConstraints();
		gbc_lblFehlermeldung.anchor = GridBagConstraints.WEST;
		gbc_lblFehlermeldung.insets = new Insets(0, 0, 0, 5);
		gbc_lblFehlermeldung.gridx = 0;
		gbc_lblFehlermeldung.gridy = 5;
		contentPane.add(getLblFehlermeldung(), gbc_lblFehlermeldung);
		GridBagConstraints gbc_btnBeenden = new GridBagConstraints();
		gbc_btnBeenden.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBeenden.gridx = 1;
		gbc_btnBeenden.gridy = 5;
		contentPane.add(getBtnBeenden(), gbc_btnBeenden);
	}

	private JLabel getLblNachrichten() {
		if (lblNachrichten == null) {
			lblNachrichten = new JLabel("Nachrichten!");
			lblNachrichten.setFont(new Font("Tahoma", Font.BOLD, 16));
		}
		return lblNachrichten;
	}
	private JLabel getLblPortnr() {
		if (lblPortnr == null) {
			lblPortnr = new JLabel("Portnr.");
			lblPortnr.setFont(new Font("Tahoma", Font.BOLD, 16));
		}
		return lblPortnr;
	}
	private JTextField getTextFieldPortnr() {
		if (textFieldPortnr == null) {
			textFieldPortnr = new JTextField();
			textFieldPortnr.setColumns(10);
		}
		return textFieldPortnr;
	}
	private JButton getBtnStart() {
		if (btnStart == null) {
			btnStart = new JButton("Start");
			btnStart.setVisible(false);
			btnStart.setFont(new Font("Tahoma", Font.BOLD, 17));
		}
		return btnStart;
	}
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setColumns(10);
		}
		return textField;
	}
	private JLabel getLblFehlermeldung() {
		if (lblFehlermeldung == null) {
			lblFehlermeldung = new JLabel("Fehlermeldungszeile");
		}
		return lblFehlermeldung;
	}
	private JTextArea getTextAreaMessages() {
		if (textAreaMessages == null) {
			textAreaMessages = new JTextArea();
		}
		return textAreaMessages;
	}
	private JButton getBtnBeenden() {
		if (btnBeenden == null) {
			btnBeenden = new JButton("beenden");
		}
		return btnBeenden;
	}
}
