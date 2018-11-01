package javacode.interfaces;

/**
 * TIf the @FunctionalInterface is specified, then 
 * we must meet the  condition: only have one abstract method(default method is not abstract method.)
 * @author ezhao3
 *
 */
@FunctionalInterface
public interface VQConverter<F,T> {
	T convert(F from);
}
