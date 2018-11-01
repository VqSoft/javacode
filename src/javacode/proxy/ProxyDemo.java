package javacode.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javacode.Demo;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyDemo extends Demo {
	public static interface BookFacade{
		int addBook(String bookName);
	}
	
	public static class BookFacadeImpl implements BookFacade{
		@Override
		public int addBook(String bookName) {
			printLine("Add book "+ bookName +" in " + getClass().getName());
			return 0;
		}
	}
	
	public static class BookFacadeProxy implements InvocationHandler{
		
		private Object target;
		
		public Object bind(Object pTarget){
			target = pTarget;
			return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
		}
	
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Object result = null;
			printLine("proxy:before invoke.");
			result = method.invoke(target, args);
			printLine("proxy:after invoke, result = " + result);
			
			return result;
		}
	}
	
	
	private static void javaDynamicProxy(){
		BookFacadeProxy proxy = new BookFacadeProxy();
		BookFacade bookProxy = (BookFacade)proxy.bind(new BookFacadeImpl());
		bookProxy.addBook("Eric");
	}
	
	
	public static class BookFacadeImpl2{
		public int addBook(String bookName) {
			printLine("Add book "+ bookName +" in BookFacadeImpl2" );
			return 0;
		}
	}
	
	public static class BookFacadeProxyCglib implements MethodInterceptor{
		private Object target;
		
		public Object getInstance(Object pTarget){
			this.target = pTarget;
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(target.getClass());
			enhancer.setCallback(this);
			
			return enhancer.create();
		}
		
		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			printLine("proxy:before invoke");
			Object result  = proxy.invokeSuper(obj, args);
			
			printLine("proxy:after invoke,result = "+ result);
			return null;
		}
	}
	
	private static void cgLibDynamicProxy(){
		BookFacadeProxyCglib proxyCglib = new BookFacadeProxyCglib();
		BookFacadeImpl2 impl = (BookFacadeImpl2)proxyCglib.getInstance(new BookFacadeImpl2());
		impl.addBook("Eric Cglib.");
	}
	
	public static void demo(){
		cgLibDynamicProxy();
	}
	
}
