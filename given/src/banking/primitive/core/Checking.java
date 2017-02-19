package banking.primitive.core;

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
	private int _numWithdraws = 0;
	
	private Checking(String _name) {
		super(_name);
	}

    public static Checking createChecking(String name) {
        return new Checking(name);
    }

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
				_numWithdraws++;
				if (_numWithdraws > 10)
					_balance = _balance - 2.0f;
				if (_balance < 0.0f) {
					_setState(STATE.OVERDRAWN);
				}
				return true;
			}
		}
		return false;
	}

	public String getType() { return "Checking"; }
	
	public String toString() {
		return "Checking: " + getName() + ": " + getBalance();
	}
}
