package javacode.interfaces;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.print.DocFlavor.STRING;

import javacode.Demo;

public class InterfaceDemo extends Demo {
	
	

	public static void defaultMethod(){
		System.out.println("Default method for interface: ");
		
		Formula formulaObj = new Formula() {
			@Override
			public double calculate(int a) {
				return sqrt(a);
			}
		};
		
		System.out.println(formulaObj.calculate(100));
		
	}
	
	/**
	 * If an interface has only one abstract method , then it's a functional interface.
	 * then the lambda expression which used will match to this method.
	 */
	public static void functionalInterface(){
		System.out.println("Functional Interface: ");
		
		VQConverter<String, Integer> converter = from -> Integer.valueOf(from);
		Integer intValue = converter.convert("999");
		System.out.println("Convert result: " + intValue);
	}
	
	/**
	 * The abstract method of functional interface can be referred to a static/instance/constructor method.
	 * So when you call the abstract method of functional interface,it actually call the referred method.
	 */
	public static void referenceMethodForInterface(){
		System.out.println("Reference method: ");
		
		//static method reference
		VQConverter<String, Integer> converter = Integer::valueOf;
		System.out.println(converter.convert("888"));
		
		//constructor method reference
		PersonFactory<Person> pFactory = Person::new;
		pFactory.create("Eric Zhao", 111);//it will call correct construct method.
	}
	
	public static void builtInFuctionalInterface(){
		printLine("Built in functional interface:");
		
		//Predicate
		Predicate<String> predicate = str -> str.length() > 0;
		predicate.test("abc");//true
		predicate.negate().test("abc");//false
		
		Predicate<Boolean> nonNull = Objects::nonNull;
		String str ="hello";
		Predicate<String> isEmpty = String::isEmpty;
		printLine("isEmpty: " + isEmpty.test(str));
		
		//Functions
		Function<String, Integer> toInt = Integer::valueOf;
		Function<String, String> backToString = toInt.andThen(String::valueOf);
		backToString.apply("199");
		
		//Supplier
		Supplier<Person> personSupplier = Person::new;
		personSupplier.get();
		
		//Consumer
		Consumer<Person> consumer = p -> printLine(p.getName());
		consumer.accept(new Person("Eric Zhao", 100));
		
		//Comparators
		Comparator<Person> comparator = (p1,p2) -> p1.getName().compareTo(p2.getName());
		Person p1 = new Person("Eric", 100);
		Person p2 = new Person("Alice", 99);
		comparator.compare(p1, p2);
		comparator.reversed().compare(p1, p2);
		
		//Optionals: this is not functional interface.
		Optional<String> optional = Optional.of("Hello");
		optional.isPresent();
		optional.get();
		optional.orElse("World");
		optional.ifPresent((s) -> System.out.println(s.charAt(0)));//"H"
		
	}
	
	
	public static void demo(){
		defaultMethod();
		
		functionalInterface();
		
		referenceMethodForInterface();
		
		builtInFuctionalInterface();
	}
}
