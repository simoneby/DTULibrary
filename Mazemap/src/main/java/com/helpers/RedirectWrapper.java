package com.helpers;
import com.models.*;

public class RedirectWrapper{
	User user;
	boolean existed;

	public RedirectWrapper(User user, boolean existed)
	{
		this.user = user;
		this.existed = existed;
	}

	public User getUser()
	{
		return this.user;
	}

	public boolean getExisted()
	{
		return this.existed;
	}

}