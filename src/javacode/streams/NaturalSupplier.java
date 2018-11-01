package javacode.streams;

import java.util.function.Supplier;

public class NaturalSupplier implements Supplier<Long>{
	long value = 0;
	@Override
	public Long get() {
		return this.value++;
	}
}