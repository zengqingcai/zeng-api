package com.ectrip.config;

import com.ectrip.model.Employees;

public class UserContext {
	
	private static ThreadLocal<Employees> userHolder = new ThreadLocal<Employees>();
	
	public static void setEmployees(Employees employees) {
		userHolder.set(employees);
	}
	
	public static Employees getEmployees() {
		return userHolder.get();
	}

}
