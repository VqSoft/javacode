package javacode.systemdemo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.Map;
import java.util.Properties;

import javacode.Demo;

public class SystemDemo extends Demo {
	
	private static void systemClassDemo() throws FileNotFoundException, IOException{
		Map<String, String> allEnvVariables = System.getenv();
		for (String varName : allEnvVariables.keySet()) {
			printLine(varName + " = " + allEnvVariables.get(varName));
		}
		printLine("**************************************************");
		printLine("JAVA_HOME = " + System.getenv("JAVA_HOME"));
		
		Properties props = System.getProperties();
		props.store(new FileOutputStream("props.txt"),"System properties");
		printLine("**************************************************");
		printLine(System.getProperty("os.name"));
	}
	
	private static void runtimeClassDemo() throws IOException{
		printLine("Runtime class.*****************");
		Runtime rt = Runtime.getRuntime();
		printLine("cpu count: " + rt.availableProcessors());
		printLine("free memory: "+ rt.freeMemory());
		printLine("total memory: "+rt.totalMemory());
		printLine("max usable memory: " + rt.maxMemory());
		rt.exec("notepad.exe");
	}
	
	public static void demo(){
		try {
			runtimeClassDemo();
		} catch (Exception e) {
			printLine(e.getMessage());
		}
	}
}
