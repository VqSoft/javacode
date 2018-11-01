package javacode.basetype;

import javacode.Demo;

public class StringDemo extends Demo {
	public static void demo(){
		printLine("String demo*************");
		compareDemo();
	}
	
	private static void compareDemo(){
		
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("abc&def&");
		sBuilder.setLength(sBuilder.length()-1);
		printLine(sBuilder.toString());
		
		String s1 = "hello";
		String s2 = "hello";
		String s3 = "he" + "llo";
		printLine((s1 == s2) + "");//true
		printLine((s2 == s3) + "");//true
		String s4 = new String("hello");
		printLine((s1 == s4) + "");//false
		printLine(s1.equals(s4) + "");//true
		
		String s5 = s4.intern();
		printLine("s1 == s5 :" + (s1 == s5));
		
	}
}
