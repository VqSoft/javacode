package javacode.classes;

import static java.lang.System.out;

import javacode.Demo;

public class ClassDemo extends Demo {
	public static void demo(){
		printLine("Init block demo *****************");
		Mid mObj = new Mid();
	}
}

class Root{
	
	{
		out.println("Normal init block in Root.");
	}
	
	static {
		out.println("Static init block in Root.");
	}
	
	public Root(){
		out.println("Constructor of Root");
	}
}

class Mid extends Root{
	{
		out.println("Normal init block in Mid.");
	}
	
	static {
		out.println("Static init block in Mid.");
	}
	
	public Mid(){
		out.println("Constructor of Mid.");
	}
}
