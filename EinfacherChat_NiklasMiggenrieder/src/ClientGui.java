import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextField;

public class ClientGui extends JFrame
{

	private JPanel contentPane;
	private JLabel lblServer;
	private JComboBox<String> comboBoxServer;
	private DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
	private JButton btnStart;
	private JLabel lblTitelEingeben;
	private JTextField textFieldText;
	private JButton btnSenden;
	private JLabel lblStatus;
	private JButton btnBeenden;
	private ControlClient control;

	public ClientGui()
	{
		initialize();
		
		comboBoxModel.addElement("localhost");
		
		control = new ControlClient(textFieldText, lblStatus, comboBoxServer);
		
		btnStart.addActionListener( e ->
		{
			control.clientStart();
		});
		
		btnSenden.addActionListener(e ->
		{
			control.sendMessage();
		});
		
		btnBeenden.addActionListener(e ->
		{
			control.clientEnd();
		});
	}
	
	private void initialize()
	{
		setTitle("Fi11-Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 401, 264);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_lblServer = new GridBagConstraints();
		gbc_lblServer.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblServer.insets = new Insets(0, 0, 5, 5);
		gbc_lblServer.gridx = 0;
		gbc_lblServer.gridy = 0;
		contentPane.add(getLblServer(), gbc_lblServer);
		GridBagConstraints gbc_comboBoxServer = new GridBagConstraints();
		gbc_comboBoxServer.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxServer.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxServer.gridx = 0;
		gbc_comboBoxServer.gridy = 1;
		contentPane.add(getComboBoxServer(), gbc_comboBoxServer);
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.insets = new Insets(0, 0, 5, 0);
		gbc_btnStart.gridx = 1;
		gbc_btnStart.gridy = 1;
		contentPane.add(getBtnStart(), gbc_btnStart);
		GridBagConstraints gbc_lblTitelEingeben = new GridBagConstraints();
		gbc_lblTitelEingeben.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblTitelEingeben.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitelEingeben.gridx = 0;
		gbc_lblTitelEingeben.gridy = 2;
		contentPane.add(getLblTitelEingeben(), gbc_lblTitelEingeben);
		GridBagConstraints gbc_textFieldText = new GridBagConstraints();
		gbc_textFieldText.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldText.gridwidth = 2;
		gbc_textFieldText.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldText.gridx = 0;
		gbc_textFieldText.gridy = 3;
		contentPane.add(getTextFieldText(), gbc_textFieldText);
		GridBagConstraints gbc_btnBeenden = new GridBagConstraints();
		gbc_btnBeenden.anchor = GridBagConstraints.WEST;
		gbc_btnBeenden.insets = new Insets(0, 0, 5, 5);
		gbc_btnBeenden.gridx = 0;
		gbc_btnBeenden.gridy = 4;
		contentPane.add(getBtnBeenden(), gbc_btnBeenden);
		GridBagConstraints gbc_btnSenden = new GridBagConstraints();
		gbc_btnSenden.insets = new Insets(0, 0, 5, 0);
		gbc_btnSenden.gridx = 1;
		gbc_btnSenden.gridy = 4;
		contentPane.add(getBtnSenden(), gbc_btnSenden);
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.anchor = GridBagConstraints.WEST;
		gbc_lblStatus.insets = new Insets(0, 0, 0, 5);
		gbc_lblStatus.gridx = 0;
		gbc_lblStatus.gridy = 5;
		contentPane.add(getLblStatus(), gbc_lblStatus);
	}

	private JLabel getLblServer() {
		if (lblServer == null) {
			lblServer = new JLabel("Server");
		}
		return lblServer;
	}
	private JComboBox<String> getComboBoxServer() {
		if (comboBoxServer == null) {
			comboBoxServer = new JComboBox<String>(comboBoxModel);
		}
		return comboBoxServer;
	}
	private JButton getBtnStart() {
		if (btnStart == null) {
			btnStart = new JButton("start");
		}
		return btnStart;
	}
	private JLabel getLblTitelEingeben() {
		if (lblTitelEingeben == null) {
			lblTitelEingeben = new JLabel("Text eingeben");
		}
		return lblTitelEingeben;
	}
	private JTextField getTextFieldText() {
		if (textFieldText == null) {
			textFieldText = new JTextField();
			textFieldText.setColumns(10);
		}
		return textFieldText;
	}
	private JButton getBtnSenden() {
		if (btnSenden == null) {
			btnSenden = new JButton("senden");
		}
		return btnSenden;
	}
	private JLabel getLblStatus() {
		if (lblStatus == null) {
			lblStatus = new JLabel("Status");
		}
		return lblStatus;
	}

	private JButton getBtnBeenden() {
		if (btnBeenden == null) {
			btnBeenden = new JButton("beenden");
		}
		return btnBeenden;
	}
}
