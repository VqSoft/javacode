package javacode.classes;

import javacode.Demo;

public class AnonymousClassDemo extends Demo {
	private static abstract class Device{
		private String name;
		public Device(String pName){
			this.name = pName;
		}
		
		public String getDeviceName(){
			return this.name;
		}
		abstract double getPrice();
	}
	
	public void test(Device d){
		printLine(d.getDeviceName() + ",price:" + d.getPrice());
	}
	
	public static void demo(){
		AnonymousClassDemo anonymousClassDemo = new AnonymousClassDemo();
		anonymousClassDemo.test(new Device("Apple") {
			
			@Override
			double getPrice() {
				// TODO Auto-generated method stub
				return 999.00;
			}
		});
	}
}
