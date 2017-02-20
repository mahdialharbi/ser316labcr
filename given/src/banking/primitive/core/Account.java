package banking.primitive.core;

/*
File:	Account.java
Author:	Kevin A Gary
Date:   2/17/2017

Description: Abstract class for types of accounts
*/

public abstract class Account implements java.io.Serializable {
    

    protected enum State {
        OPEN, CLOSED, OVERDRAWN
    };

    protected float balance = 0.0F;
    protected String name;
    private State state;
    private static final long serialVersionUID = 1L;
    
    protected Account(String n) {
        name = n;
        state = State.OPEN;
    }
// an account
    protected Account(String n, float b) {
        this(n); 
        balance = b;
    }

    /**
     * @return balance in the Account
     */
    public final float getBalance() {
        return balance;
    }
    
    /**
     * @return name of the Account
     */
    public final String getName() {
        return name;
    }

    protected final State getState() {
        return state;
    }

    protected final void setState(State s) {
        state = s;
    }

    /**
     * @return either "Checking" or "Savings"
     */
    public abstract String getType();

   
    public String toString() {
        return "Account " + name + " has $" + balance + "and is " + getState()
                + "\n";
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

}
