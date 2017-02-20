package banking.primitive.core;

/*
File:	Savings.java
Author:	Kevin A Gary
Date:   2/17/2017

Description: Account of type Savings
*/

public class Savings extends Account {
	
	private static final long serialVersionUID = 111L;
	private int numWithdraws = 0;

	/**
	  Method: Savings
	  Inputs: String
	  Returns: Savings account

	  Description: Constructor for Savings account object sets input string as name
	*/
	public Savings(String name) {
		super(name);
	}

	/**
	  Method: Savings
	  Inputs: String, float
	  Returns: Savings account

	  Description: Constructor for Savings account object sets input string
	  as name, and input float as blance.
	*/
	public Savings(String name, float balance) throws IllegalArgumentException {
		super(name, balance);
	}

	/**
	  Method: getType
	  Inputs: None
	  Returns: String

	  Description: Returns account type
	*/
	public String getType() { return "Checking"; }

	/**
	 * A deposit comes with a fee of 50 cents per deposit
	 */
	public boolean deposit(float amount) {
		if (getState() != State.CLOSED && amount > 0.0f) {
			balance = balance + amount - 0.50F;
			if (balance >= 0.0f) {
				setState(State.OPEN);
				return true;
			}
		}
		return false;
	}

	/**
	 * A withdrawal. After 3 withdrawals a fee of $1 is added to each withdrawal.
	 * An account whose balance dips below 0 is in an OVERDRAWN state
	 */
	public boolean withdraw(float amount) {
		if (getState() == State.OPEN && amount > 0.0f) {
			balance = balance - amount;
			numWithdraws++;
			if (numWithdraws > 3)
				balance = balance - 1.0f;
			// KG BVA: should be < 0
			if (balance <= 0.0f) {
				setState(State.OVERDRAWN);
			}
			return true;
		}
		return false;
	}
	
	/**
	  Method: toString
	  Inputs: None
	  Returns: String

	  Description: Returns a string with account type, account name, and account
	  balance.
	*/
	public String toString() {
		return "Savings: " + getName() + ": " + getBalance();
	}
	// save this
}
