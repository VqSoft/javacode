package javacode.lambdas;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import javacode.Demo;
import javacode.interfaces.VQConverter;

public class LambdaDemo extends Demo {

	public static void lambdaExpression(){
		System.out.println("lambdaExpression");
		List<String> names = Arrays.asList("Eric Zhao","Tom Sun","Thomas Jie");
		
		//normal sort method
		Collections.sort(names, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o2.compareTo(o1);
			}
		});
		
		//java 8 lambda expressions.
		Collections.sort(names, (String a, String b) -> {
			return b.compareTo(a);
		});
		//shorter 
		Collections.sort(names,(String a,String b) -> b.compareTo(a) );
		
		//more shorter for one line code method.
		names.sort((a,b) -> b.compareTo(a));
		
		System.out.println(names);
	}
	
	/**
	 * lambda expression can access local variables/ instance fields and static fields.
	 * When accessing local variable, the variable must be final or implicitly final.
	 * But can't access default method of interface.
	 */
	public static void lambdoScope(){
		printLine("Lambda Scope:");
		int count = 1;
		VQConverter<Integer, String> converter = from -> String.valueOf(from + count);
		converter.convert(200);
	}
	
	
	private static interface Eatable{
		void taste();
	}
	
	private static interface Flyable{
		void fly(String weather);
	}
	
	private static interface Addable{
		int add(int a,int b);
	}
	
	private static class LambdaTest{
		public void eat(Eatable e){
			printLine(e.toString());
			e.taste();
		}
		
		public void fly(Flyable f){
			printLine(f.toString());
			f.fly("Sunshine...");
		}
		
		public void add(Addable a){
			printLine(a.toString());
			a.add(10, 5);
		}
		
		public static void test(){
			LambdaTest lt = new LambdaTest();
			//如果lambda表达式的代码块只有一条语句，则可以省略花括号
			lt.eat(()-> printLine("Eat an apple.") );
			//如果lambda形参只有一个，则可以省略圆括号
			lt.fly(weather -> {
				printLine("Today is " + weather);
				printLine("End of line.");
			});
			//如果lambda代码块只有一条语句，并且需要返回值，则可以省略花括号和return关键字
			lt.add((inta,intb) -> inta + intb);
		}
	}
	
	@FunctionalInterface
	public static interface ConverterVQ{
		Integer convert(String from);
	}
	
	public static void classMethodDemo(){
		//使用lambda表达式创建ConverterVQ对象
		ConverterVQ converter1 = from -> Integer.valueOf(from);
		//类方法引用，函数式接口中被实现方法的全部参数会传给类方法作为参数. 
		ConverterVQ converter2 = Integer::valueOf;
	}
	
	public static void objectMethodDemo(){
		ConverterVQ converter1 = from -> "test.com".indexOf(from);
		ConverterVQ converter2 = "test.com"::indexOf;
	}
	
	@FunctionalInterface
	public static interface SubConverter{
		String test(String a, int b, int c);
	}
	
	public static void classObjectInstanceMethod(){
		SubConverter converter1 = (a,b,c) -> a.substring(b,c);
		//引用类对象的实例方法，函数式接口中被实现方法的第一个参数作为调用者，
		//后面的参数会全部传给 substring
		SubConverter converter2 = String::substring;
		converter2.test("I love java", 2, 3);
	}
	
	@FunctionalInterface
	private static interface TestClass{
		Integer getInt(String strInt);
	}
	
	public static void refConstruct(){
		TestClass tc = str -> new Integer(str);
		//下面代码的作用同上
		TestClass tc2 = Integer::new;//函数式接口中被实现方法的全部参数将会传给构造器作为参数。
		tc2.getInt("123");
	}
	
	public static void demo(){
		lambdaExpression();
		lambdoScope();
	}
}
