package javacode.unittests;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for running specific testcases.
 * @author Eric Zhao 
 * Created on Apr 1, 2017 
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SuiteClassesAndMethods {
	/**
	 * Specify which test classes will be runned.
	 * @return
	 */
	public Class<?>[] classes();
	
	/**
	 * Specify hoa many methods will be runned per class
	 * @return
	 */
	public int[] methodCountPerClass();
	
	/**
	 * Specify which method will be runned
	 * @return
	 */
	public String[] methodNames();
}
