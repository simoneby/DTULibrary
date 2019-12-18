package com.helpers;

//@Author s192671
public class ReturnMessageHelper
{
	public static String getReturnMessage(String s)
	{
		return String.format("{\"%s\":\"%s\"}","message",s);
	}

}