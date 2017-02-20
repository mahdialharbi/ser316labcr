package banking.primitive.core;

/*
File:	AccountServerFactory.java
Author:	Kevin A Gary
Date:   2/17/2017

Description: Receives value inputs from ServerSolution
*/


public class AccountServerFactory {

	protected static AccountServerFactory singleton = null;

	protected AccountServerFactory() {

	}

	/**
	  Method: getMe
	  Inputs: None
	  Returns: AccountServerFactory object

	  Description: returns an AccountServerFactory Object
	*/
	
	public static AccountServerFactory getMe() {
		if (singleton == null) {
			singleton = new AccountServerFactory();
		}

		return singleton;
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
}
