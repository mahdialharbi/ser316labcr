package banking.primitive.core;

public class Savings extends Account {
	
	/**
	  Class: Savings
	  
	  Description: This Savings class is responsible for
	  			   holding the savings account name and 
	  			   also it does the functionality of deposits 
	  			   made into the account, while also updating 
	  			   the balance of the account. Also it does 
	  			   the withdraw functionality, which also 
	  			   checks for policies (withdraw limits) and 
	  			   it applies a fee if the limit has been 
	  			   reached or exceeded.
	  			   
	*/
	
	
	private static final long SERIALVERSIONUID = 111L;
	private int _numWithdraws = 0;

	public Savings(String name) {
		super(name);
	}

	public Savings(String name, float balance) throws IllegalArgumentException {
		super(name, balance);
	}

	/**
	 * A deposit comes with a fee of 50 cents per deposit
	 */
	public boolean deposit(float amount) {
		if (_getState() != STATE.CLOSED && amount > 0.0f) {
			_balance = _balance + amount - 0.50F;
			if (_balance >= 0.0f) {
				_setState(STATE.OPEN);
			}
		}
		return false;
	}

	/**
	 * A withdrawal. After 3 withdrawals a fee of $1 is added to each withdrawal.
	 * An account whose balance dips below 0 is in an OVERDRAWN STATE
	 */
	public boolean withdraw(float amount) {
		if (_getState() == STATE.OPEN && amount > 0.0f) {
			_balance = _balance - amount;
			_numWithdraws++;
			if (_numWithdraws > 3)
				_balance = _balance - 1.0f;
			// KG BVA: should be < 0
			if (_balance <= 0.0f) {
				_setState(STATE.OVERDRAWN);
			}
			return true;
		}
		return false;
	}
	
	public String getType() { return "Checking"; }

	public String toString() {
		return "Savings: " + getName() + ": " + getBalance();
	}
}
