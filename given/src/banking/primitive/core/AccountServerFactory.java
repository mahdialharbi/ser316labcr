package banking.primitive.core;

/*
File:	AccountServerFactory.java
Author:	Kevin A Gary
Date:   2/17/2017

Description: Receives value inputs from ServerSolution
*/


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

	/**
	  Method: getMe
	  Inputs: None
	  Returns: AccountServerFactory object

	  Description: returns an AccountServerFactory Object
	*/
	
	public static AccountServerFactory getMe() {
		if (SINGLETON == null) {
			SINGLETON = new AccountServerFactory();
		}

		return SINGLETON;
	}

	/**
	  Method: lookup
	  Inputs: None
	  Returns: ServerSolution

	  Description: Returns a new ServerSolution Object
	*/
	
	public AccountServer lookup() {
		return new ServerSolution();
	}
	// factory
}
