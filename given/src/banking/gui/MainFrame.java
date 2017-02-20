package banking.gui;

/*
File:	MainFrame.java
Author:	Kevin A Gary
Date:   2/17/2017

Description: Sets up GUI JFrame
*/

import banking.primitive.core.Account;
import banking.primitive.core.AccountServer;
import banking.primitive.core.AccountServerFactory;

import java.io.*;
import java.util.*;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
class MainFrame extends JFrame {

	AccountServer myServer;
	Properties props;
	JLabel typeLabel;
	JLabel nameLabel;
	JLabel balanceLabel;
	JComboBox typeOptions;
	JTextField nameField;
	JTextField balanceField;
	JButton depositButton;
	JButton withdrawButton;
	JButton newAccountButton;
	JButton displayAccountsButton;
	JButton displayODAccountsButton;

	
	/**
	  Method: MainFrame
	  Inputs: String
	  Returns: MainFrame Object

	  Description: Constructor for MainFrame Class
	*/
	public MainFrame(String propertyFile) throws IOException {

		// ** initialize myServer
		myServer = AccountServerFactory.getMe().lookup();

		props = new Properties();

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(propertyFile);
			props.load(fis);
			fis.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw ioe;
		}
		_constructForm();
	}

	private void _constructForm() {
		// *** Make these read from properties
		typeLabel = new JLabel(props.getProperty("TypeLabel"));
		nameLabel = new JLabel(props.getProperty("NameLabel"));
		balanceLabel = new JLabel(props.getProperty("BalanceLabel"));

		Object[] accountTypes = { 
				"Savings", "Checking" 
				};
		
		typeOptions = new JComboBox(accountTypes);
		nameField = new JTextField(20);
		balanceField = new JTextField(20);

		newAccountButton = new JButton("New Account");
		JButton depositButton = new JButton("Deposit");
		JButton withdrawButton = new JButton("Withdraw");
		JButton saveButton = new JButton("Save Accounts");
		displayAccountsButton = new JButton("List Accounts");
		JButton displayAllAccountsButton = new JButton("All Accounts");

		this.addWindowListener(new FrameHandler());
		newAccountButton.addActionListener(new NewAccountHandler());
		displayAccountsButton.addActionListener(new DisplayHandler());
		displayAllAccountsButton.addActionListener(new DisplayHandler());
		depositButton.addActionListener(new DepositHandler());
		withdrawButton.addActionListener(new WithdrawHandler());
		saveButton.addActionListener(new SaveAccountsHandler());

		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());

		


		JPanel accTypePanel = new JPanel();
		accTypePanel.add(typeLabel);
		accTypePanel.add(typeOptions);

		JPanel displayOptions = new JPanel();
		displayOptions.add(displayAccountsButton);
		displayOptions.add(displayAllAccountsButton);
		displayOptions.add(saveButton);

		JPanel accLabelPanel = new JPanel();
		accLabelPanel.add(nameLabel);
		accLabelPanel.add(nameField);

		JPanel balancePanel = new JPanel();
		balancePanel.add(balanceLabel);
		balancePanel.add(balanceField);

		JPanel accActionButtons = new JPanel();
		accActionButtons.add(newAccountButton);
		accActionButtons.add(depositButton);
		accActionButtons.add(withdrawButton);

		pane.add(accTypePanel);
		pane.add(displayOptions);
		pane.add(accLabelPanel);
		pane.add(balancePanel);
		pane.add(accActionButtons);


		setSize(400, 250);
	}

	class DisplayHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			List<Account> accounts = null;
			if (e.getSource() == displayAccountsButton) {
				accounts = myServer.getActiveAccounts();
			} 
			else {
				accounts = myServer.getAllAccounts();
			}
			StringBuffer sb = new StringBuffer();
			Account thisAcct = null;
			for (Iterator<Account> li = accounts.iterator(); li.hasNext();) {
				thisAcct = (Account) li.next();
				sb.append(thisAcct.toString() + "\n");
			}

			JOptionPane.showMessageDialog(null, sb.toString());
		}
	}

	// Complete a handler for new account button
	class NewAccountHandler implements ActionListener {
		//action listener
		public void actionPerformed(ActionEvent e) {
			String type = typeOptions.getSelectedItem().toString();
			String name = nameField.getText();
			String balance = balanceField.getText();

			if (myServer.newAccount(type, name, Float.parseFloat(balance))) {
				JOptionPane.showMessageDialog(null, "Account created successfully");
			} 
			else {
				JOptionPane.showMessageDialog(null, "Account not created!");
			}
		}
	}

	// Complete a handler for new account button
	class SaveAccountsHandler implements ActionListener {
		//action listener
		public void actionPerformed(ActionEvent e) {
			try {
				myServer.saveAccounts();
				JOptionPane.showMessageDialog(null, "Accounts saved");
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(null, "Error saving accounts");
			}
		}
	}

	// Complete a handler for deposit button
	class DepositHandler implements ActionListener {
		// action listener
		public void actionPerformed(ActionEvent e) {
			String name = nameField.getText();
			String balance = balanceField.getText();
			Account acc = myServer.getAccount(name);
			if (acc != null && acc.deposit(Float.parseFloat(balance))) {
				JOptionPane.showMessageDialog(null, "Deposit successful");
			} 
			else {
				JOptionPane.showMessageDialog(null, "Deposit unsuccessful");
			}
		}
	}

	// Complete a handler for deposit button
	class WithdrawHandler implements ActionListener {
		//action listener
		public void actionPerformed(ActionEvent e) {
			String name = nameField.getText();
			String balance = balanceField.getText();
			Account acc = myServer.getAccount(name);
			if (acc != null && acc.withdraw(Float.parseFloat(balance))) {
				JOptionPane.showMessageDialog(null, "Withdrawal successful");
			} 
			else {
				JOptionPane.showMessageDialog(null, "Withdrawal unsuccessful");
			}
		}
	}

	// ** Complete a handler for the Frame that terminates
	// ** (System.exit(1)) on windowClosing event

	static class FrameHandler extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}
}
