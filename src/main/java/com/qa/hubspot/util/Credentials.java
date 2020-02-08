package com.qa.hubspot.util;
	/* Good practice to maintain a Credentials class
	 * instead of passing multiple parameters, we pass the object
	 * in that object, the credential values (using getter and setter) are available
	 * this is especially helpful when we have multiple users
	 */

	/* setter methods are also useful when you want to set username & password at runtime
	 * credentials are encapsulated
	 */
public class Credentials {

	String appUsername;
	String appPassword;
	
	public Credentials(String appUsername, String appPassword) {
		this.appUsername = appUsername;
		this.appPassword = appPassword;
	}

	public String getAppUsername() {
		return appUsername;
	}

	public void setAppUsername(String appUsername) {
		this.appUsername = appUsername;
	}

	public String getAppPassword() {
		return appPassword;
	}

	public void setAppPassword(String appPassword) {
		this.appPassword = appPassword;
	}
	
	
}
