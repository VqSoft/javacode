package javacode.referencedemo;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

import javacode.Demo;

public class ReferenceDemo extends Demo {

	private static void phantomReference(){
		String str = new String("Hello");
		ReferenceQueue<String> rQueue = new ReferenceQueue<String>();
		
		PhantomReference<String> pr = new PhantomReference<String>(str, rQueue);
		str = null;
		String obj = pr.get();//无法通过虚引用获取被引用的对象，此处返回null
		System.gc();
		System.runFinalization();
		//垃圾回收完成后，虚引用会被放入队列中
		printLine((rQueue.poll() == pr) + "");
	}
	
	public static void demo(){
		phantomReference();
	}
	
}
