package banking.primitive.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.*;

import banking.primitive.core.Account.STATE;

class ServerSolution implements AccountServer {

	static String FILENAME = "accounts.ser";

	Map<String,Account> accountMap = null;

	public ServerSolution() {
		accountMap = new HashMap<String,Account>();
		File file = new File(FILENAME);
		ObjectInputStream in = null;
		try {
			if (file.exists()) {
				System.out.println("Reading from file " + FILENAME + "...");
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
	
	private boolean _newAccountFactory(String _type, String _name, float _balance)
		throws IllegalArgumentException {
		
		if (accountMap.get(_name) != null) return false;
		
		Account acc;
		if ("Checking".equals(_type)) {
			acc = new Checking(_name, _balance);

		} else if ("Savings".equals(_type)) {
			acc = new Savings(_name, _balance);

		} else {
			throw new IllegalArgumentException("Bad account type:" + _type);
		}
		try {
			accountMap.put(acc.getName(), acc);
		} catch (Exception exc) {
			return false;
		}
		return true;
	}

	public boolean newAccount(String type, String name, float balance) 
		throws IllegalArgumentException {
		
		if (balance < 0.0f) throw new IllegalArgumentException("New account may not be started with a negative balance");
		
		return _newAccountFactory(type, name, balance);
	}
	
	public boolean closeAccount(String name) {
		Account acc = accountMap.get(name);
		if (acc == null) {
			return false;
		}
		acc._setState(STATE.CLOSED);
		return true;
	}

	public Account getAccount(String name) {
		return accountMap.get(name);
	}

	public List<Account> getAllAccounts() {
		return new ArrayList<Account>(accountMap.values());
	}

	public List<Account> getActiveAccounts() {
		List<Account> result = new ArrayList<Account>();

		for (Account acc : accountMap.values()) {
			if (acc._getState() != STATE.CLOSED) {
				result.add(acc);
			}
		}
		return result;
	}
	
	public void saveAccounts() throws IOException {
		ObjectOutputStream out = null; 
		try {
			out = new ObjectOutputStream(new FileOutputStream(FILENAME));

			out.writeObject(Integer.valueOf(accountMap.size()));
			for (int i=0; i < accountMap.size(); i++) {
				out.writeObject(accountMap.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("Could not write file:" + FILENAME);
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

}
