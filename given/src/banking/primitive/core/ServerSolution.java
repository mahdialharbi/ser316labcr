package banking.primitive.core;

/*
File:	ServerSolution.java
Author:	Kevin A Gary
Date:   2/17/2017

Description: Server that stores account information from file accounts.ser
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.*;

import banking.primitive.core.Account.State;

class ServerSolution implements AccountServer {

	static String fileName = "accounts.ser";

	Map<String,Account> accountMap = null;

	/**
	  Method: ServerSolution
	  Inputs: None
	  Returns: ServerSolution

	  Description: Constructor for ServerSolution
	*/
	public ServerSolution() {
		accountMap = new HashMap<String,Account>();
		File file = new File(fileName);
		ObjectInputStream in = null;
		try {
			if (file.exists()) {
				System.out.println("Reading from file " + fileName + "...");
				in = new ObjectInputStream(new FileInputStream(file));

				Integer sizeI = (Integer) in.readObject();
				int size = sizeI.intValue();
				for (int i=0; i < size; i++) {
					Account acc = (Account) in.readObject();
					if (acc != null)
						accountMap.put(acc.getName(), acc);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}
	
	private boolean newAccountFactory(String type, String name, float balance)
		throws IllegalArgumentException {
		
		if (accountMap.get(name) != null) return false;
		
		Account acc;
		if ("Checking".equals(type)) {
			acc = new Checking(name, balance);

		} else if ("Savings".equals(type)) {
			acc = new Savings(name, balance);

		} else {
			throw new IllegalArgumentException("Bad account type:" + type);
		}
		try {
			accountMap.put(acc.getName(), acc);
		} catch (Exception exc) {
			return false;
		}
		return true;
	}

	/**
	  Method: newAccount
	  Inputs: String, String, float
	  Returns: account

	  Description: Creates a new account of type input string 1, sets the account name to the 
	  input string 2, and sets the balance of the account equal to the float input.
	*/
	public boolean newAccount(String type, String name, float balance) 
		throws IllegalArgumentException {
		
		if (balance < 0.0f) throw new IllegalArgumentException("New account may not be started with a negative balance");
		
		return newAccountFactory(type, name, balance);
	}
	
	/**
	  Method: closeAccount
	  Inputs: String
	  Returns: boolean

	  Description: Closes an account and returns true if closed and false if null
	*/
	public boolean closeAccount(String name) {
		Account acc = accountMap.get(name);
		if (acc == null) {
			return false;
		}
		acc.setState(State.CLOSED);
		return true;
	}

	/**
	  Method: getAccount
	  Inputs: String
	  Returns: account

	  Description: returns an account from the list by searching the input string as the 
	  account name.
	*/
	public Account getAccount(String name) {
		return accountMap.get(name);
	}

	/**
	  Method: getAllAccounts
	  Inputs: None
	  Returns: List

	  Description: Returns all the accounts in the current list
	*/
	public List<Account> getAllAccounts() {
		return new ArrayList<Account>(accountMap.values());
	}

	/**
	  Method: getActiveAccounts
	  Inputs: None
	  Returns: List

	  Description: Returns all the active accounts in the current list
	*/
	public List<Account> getActiveAccounts() {
		List<Account> result = new ArrayList<Account>();

		for (Account acc : accountMap.values()) {
			if (acc.getState() != State.CLOSED) {
				result.add(acc);
			}
		}
		return result;
	}
	
	/**
	  Method: saveAccounts
	  Inputs: None
	  Returns: Nothing

	  Description: Saves accounts in the current list to a file
	*/
	public void saveAccounts() throws IOException {
		ObjectOutputStream out = null; 
		try {
			out = new ObjectOutputStream(new FileOutputStream(fileName));

			out.writeObject(Integer.valueOf(accountMap.size()));
			for (int i=0; i < accountMap.size(); i++) {
				out.writeObject(accountMap.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("Could not write file:" + fileName);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}
	// solution
}
