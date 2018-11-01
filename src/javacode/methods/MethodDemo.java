package javacode.methods;

import javacode.Demo;

public class MethodDemo extends Demo {

	
	private static void printIt(String... strings ){
		if (strings.length == 0) {
			printLine("empty array.");
			return;
		}
		printIt("array length = " + strings.length);
	}
	
	public static void demo(){
		printIt();
		printIt(null);
	}
}
