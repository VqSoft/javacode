package javacode.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaStreamDemo {

	
	
	public static void demo(){
		//用stream 实现无穷数列，比如自然数集合
		System.out.println("用stream 实现无穷数列，比如自然数集合:");
		Stream<Long> natural = Stream.generate(new NaturalSupplier());
		natural.map(x -> {
			return x*x;
		}).limit(10).forEach(ret -> System.out.println(ret));;
		
		
		//Stream用法1：把collection变成stream
		List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9);
		Stream<Integer> stream1 = numbers.stream();
		stream1.filter(x -> {
			return x % 2 ==0;
		}).map(x1 -> {
			return x1 *  x1;
		}).forEach(ret -> {
			System.out.println(ret);
		});
		
		//斐波那契数列
		Stream<Long> stream2 = Stream.generate(new FabonacciSupplier());
		//stream2.skip(5).limit(10).forEach(System.out::println);
		
		List<Long> list1 = stream2.skip(30).limit(10).collect(Collectors.toList());
		System.out.println("stream2.skip(6).limit(10).collect(Collectors.toList()) :");
		for (Long long1 : list1) {
			System.out.println(long1);
		}
	}
}
