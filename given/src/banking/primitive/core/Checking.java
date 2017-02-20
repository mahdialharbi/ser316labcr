package banking.primitive.core;

/*
File:	Checking.java
Author:	Kevin A Gary
Date:   2/17/2017

Description: Account of type Checking
*/

public class Checking extends Account {

	/**
	  Class: Checking
	  
	  Description: This Checking class is responsible for
	  			   holding the checking account name and 
	  			   also it does the functionality of deposits 
	  			   made into the account, while also updating 
	  			   the balance of the account. Also it does 
	  			   the withdraw functionality, which also 
	  			   checks for policies (withdraw limits).
	  			   
	*/
	
	
	private static final long _SERIALVERSIONUID = 11L;
	private static int NUMWITHDRAWS = 0;
	
	private Checking(String _name) {
		super(_name);
	}

	/**
	  Method: getType
	  Inputs: None
	  Returns: String

	  Description: Returns the type of account 
	*/
	public String getType() { return "Checking"; }
	
	/**
	  Method: createChecking
	  Inputs: String
	  Returns: Checking account

	  Description: Creates a new Checking account object with 
	  the name provided in the input string.
	*/
    public static Checking createChecking(String name) {
        return new Checking(name);
    }

    /**
	  Method: Checking
	  Inputs: String, float
	  Returns: Checking account

	  Description: Constructor for Checking Account that creates a Checking Account 
	  object with the name provided in the input string, and the balance with the input float.
	*/
	public Checking(String name, float balance) {
		super(name, balance);
	}

	/**
	 * A deposit may be made unless the Checking account is closed
	 * @param float is the deposit amount
	 */
	public boolean deposit(float amount) {
		if (_getState() != STATE.CLOSED && amount > 0.0f) {
			_balance = _balance + amount;
			if (_balance >= 0.0f) {
				_setState(STATE.OPEN);
			}
			return true;
		}
		return false;
	}

	/**
	 * Withdrawal. After 10 withdrawals a fee of $2 is charged per transaction You may 
	 * continue to withdraw an overdrawn account until the balance is below -$100
	 */
	public boolean withdraw(float amount) {
		if (amount > 0.0f) {		
			// KG: incorrect, last balance check should be >=

			if (_getState() == STATE.OPEN || (_getState() == STATE.OVERDRAWN && _balance > -100.0f)) {
				_balance = _balance - amount;
				NUMWITHDRAWS++;
				if (NUMWITHDRAWS > 10)
					_balance = _balance - 2.0f;
				if (_balance < 0.0f) {
					_setState(STATE.OVERDRAWN);
				}
				return true;
			}
		}
		return false;
	}

	/**
	  Method: toString
	  Inputs: None
	  Returns: String

	  Description: Returns the type of account with the name
	  of the account, and the balance of the account.
	*/
	public String toString() {
		return "Checking: " + getName() + ": " + getBalance();
	}
	
}
