package banking.primitive.core;


public class AccountServerFactory {
	
	/**
	  Class: AccountServerFactory
	  
	  Description: This AccountServerFactory will load information
	  			   that is given from the ServerSolution class in the
	  			   ServerSolution.java file. When the values and information
	  			   is received it will properly set them so that it ensures
	  			   that they have been updated correctly to where they are 
	  			   supposed to go.
	  			   
	*/

	protected static AccountServerFactory SINGLETON = null;

	protected AccountServerFactory() {

	}

	public static AccountServerFactory getMe() {
		if (SINGLETON == null) {
			SINGLETON = new AccountServerFactory();
		}

		return SINGLETON;
	}

	public AccountServer lookup() {
		return new ServerSolution();
	}
}
