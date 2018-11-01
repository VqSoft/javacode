package javacode.streams;

import java.util.function.Supplier;

public class FabonacciSupplier implements Supplier<Long> {
	private Long a = 0L;
	private Long b = 1L;
	
	@Override
	public Long get() {
		Long x = a + b;
		a = b;
		b = x;
		
		return a;
	}
}
