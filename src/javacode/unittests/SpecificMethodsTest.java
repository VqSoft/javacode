package javacode.unittests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;

/**
 * @author Eric Zhao 
 * Created on Apr 1, 2017 
 * Main entry class which runs specific test casses.
 */
//@SuiteClassesAndMethods(classes={YourClassTestCase.class}
//,methodCountPerClass={1}
//,methodNames={"testGetSavedCreditCardList"})
public class SpecificMethodsTest {
	
	public static void main(String[] args){
		
		runCurrentClassSpecifyMethods();
		
	}//main
	
	private static void runCurrentClassSpecifyMethods(){
		Map<Class<?>, List<String>> classMethodsMap = getTestClassMethodsMap(SpecificMethodsTest.class);
		Map<Boolean, List<Result>> resultMap = getResult(classMethodsMap);
		StringBuffer output = formatResultAsString(resultMap);
		
		System.out.print(output.toString());
	}
	
	private static Map<Class<?>, List<String>> getTestClassMethodsMap(Class<?> theClass ){
		//Class<SpecificMethodsTest> currentClass = SpecificMethodsTest.class;
		SuiteClassesAndMethods suiteClassMethods = theClass.getAnnotation(SuiteClassesAndMethods.class);
		
		Class<?>[] classes = suiteClassMethods.classes();
		int[] methodCountPerClass = suiteClassMethods.methodCountPerClass();
		String[] methodNames = suiteClassMethods.methodNames();
		
		Map<Class<?>, List<String>> classMethodsMap = new LinkedHashMap<>();
		int caseCount = 0;
		
		for (int i=0; i < classes.length; i++) {
			List<String> currentClsMethodNames = new ArrayList<>();
			for(int j=0; j < methodCountPerClass[i]; j++ ){
				currentClsMethodNames.add(methodNames[caseCount]);
				caseCount++;
			}//for
			classMethodsMap.put(classes[i], currentClsMethodNames);
		}//for
		
		return classMethodsMap;
	}
	
	private static Map<Boolean, List<Result>> getResult(Map<Class<?>, List<String>> classMethodsMap){
		Map<Boolean, List<Result>> resultMap = new HashMap<>();
		
		JUnitCore junitRunner = new JUnitCore();
		List<org.junit.runner.Result> caseResults = new ArrayList<>();

		List<Result> successCaseReults = new ArrayList<>();
		List<Result> failedCaseReults = new ArrayList<>();
		for (Map.Entry<Class<?>, List<String>> classMethodEntry : classMethodsMap.entrySet()) {
			Class testClass = classMethodEntry.getKey();
			List<String> testMethodNames = classMethodEntry.getValue();
			for(int i=0; i < testMethodNames.size(); i++){
				org.junit.runner.Request req = Request.method(testClass, testMethodNames.get(i));
				Result result = junitRunner.run(req);
				
				if (result.wasSuccessful()) {
					successCaseReults.add(result);
				}
				else {
					failedCaseReults.add(result);
				}
			}//for
		}//for
		
		resultMap.put(true, successCaseReults);
		resultMap.put(false, failedCaseReults);
		
		return resultMap;
	}//getResult
	
	private static StringBuffer formatResultAsString(Map<Boolean, List<Result>> resultMap){
		StringBuffer output = new StringBuffer();
		List<Result> successCaseReults = resultMap.get(true);
		List<Result> failedCaseReults = resultMap.get(false);
		long spendTime = 0;
		spendTime += successCaseReults.stream().mapToLong(result -> result.getRunTime()).sum();
		spendTime += failedCaseReults.stream().mapToLong(result -> result.getRunTime()).sum();
		
		output.append("**************** Result output: ******************** \n");
		output.append("Total cases: " + (successCaseReults.size() + failedCaseReults.size()));
		output.append(", Success cases: " + successCaseReults.size());
		output.append(", Failed cases: " + failedCaseReults.size());
		output.append(", Spend time: " + (spendTime/1000)/60 + "minutes," + (spendTime/1000)%60 + "seconds");
		
		if (failedCaseReults.size() > 0) {
			output.append("\n Failure information : \n");
			failedCaseReults.stream().forEach(result -> {
				output.append("*** case:"+ result.toString() + " failure info: ***");
				output.append(result.getFailures().toString());
				output.append("\n");
			});
		}
		
		return output;
	}
}
