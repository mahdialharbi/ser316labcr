package banking.primitive.core;

/*
File:	Account.java
Author:	Kevin A Gary
Date:   2/17/2017

Description: Abstract class for types of accounts
*/

public abstract class Account implements java.io.Serializable {
	
	/**
	  Class: Account
	  
	  Description: The Account class is responsible for containing 
	  			   all of the information that is needed for every 
	  			   account. You will see that in this class that 
	  			   it holds the name, balance, deposit, withdraw, 
	  			   the type and the state of all of the accounts 
	  			   that will exist individually.
	  			   
	*/
	
	
    private static final long serialVersionUID = 1L;

    protected enum STATE {
        OPEN, CLOSED, OVERDRAWN
    };

    protected float _balance = 0.0F;
    protected String _name;
    private STATE _state;

    protected Account(String _accountName) {
        _name = _accountName;
        _state = STATE.OPEN;
    }

    protected Account(String _accountName, float _accountBalance) {
        this(_accountName); 
        _balance = _accountBalance;
    }

    /**
     * @return name of the Account
     */
    public final String getName() {
        return _name;
    }

    /**
     * @return balance in the Account
     */
    public final float getBalance() {
        return _balance;
    }

    /**
     * Adds money to an account. May not be done if the account is CLOSED
     * 
     * @param parameter
     *            amount is a deposit and must be > 0
     * @return true if the deposit was successful, false if not due to amount or
     *         invalid state
     */
    public abstract boolean deposit(float amount);

    /**
     * Takes money out of an account. If the balance falls below 0 then the
     * account is moved to an OVERDRAWN state
     * 
     * @param parameter
     *            amount is a withdrawal and must be > 0
     * @return true if the deposit was successful, false if not due to amount or
     *         invalid state
     */
    public abstract boolean withdraw(float amount);

    /**
     * @return either "Checking" or "Savings"
     */
    public abstract String getType();

    protected final STATE _getState() {
        return _state;
    }

    protected final void _setState(STATE _setAccState) {
        _state = _setAccState;
    }

    public String toString() {
        return "Account " + _name + " has $" + _balance + "and is " + _getState()
                + "\n";
    }
}
