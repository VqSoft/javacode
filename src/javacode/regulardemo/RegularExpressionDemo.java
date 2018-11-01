package javacode.regulardemo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javacode.Demo;

public class RegularExpressionDemo extends Demo {
	
	public static void demo(){
		findGroupDemo();
	}
	
	private static void commonDemo(){
		printLine("\u0041\\\\");//A\\
		printLine("A\\");//a\
		Pattern pattern = Pattern.compile("\u0041\\\\");
		Matcher matcher = pattern.matcher("A\\");
		printLine("正则匹配结果: " + matcher.matches());//true
	}
	
	private static void findGroupDemo(){
		String str = "I have a book, you can contact me :13500005678"
				+ "the second tel:13622226987 "
				+ "the third tel:15833331234";
		Matcher matcher = Pattern.compile("((13\\d)|(15\\d))\\d{8}").matcher(str);
		while(matcher.find()){
			printLine(matcher.group());
		}
	}
	
}
