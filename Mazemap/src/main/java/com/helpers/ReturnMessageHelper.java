package com.helpers;

public class ReturnMessageHelper
{
	public static String getReturnMessage(String s)
	{
		return String.format("{\"%s\":\"%s\"}","message",s);
	}

}