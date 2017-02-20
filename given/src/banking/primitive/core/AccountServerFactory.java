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

	protected static AccountServerFactory singleton = null;

	protected AccountServerFactory() {

	}

	public static AccountServerFactory getMe() {
		if (singleton == null) {
			singleton = new AccountServerFactory();
		}

		return singleton;
	}

	public AccountServer lookup() {
		return new ServerSolution();
	}
}
