package com.gabeochoa;

public class Person{

	public String name;
	public int hours;
	
	public Person(String name, int hours)
	{
		this.name = name;
		this.hours = hours;
	}
	
	public String toString()
	{
		return name+","+hours;
	}
}
