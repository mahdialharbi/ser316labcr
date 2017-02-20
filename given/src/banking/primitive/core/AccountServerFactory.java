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
