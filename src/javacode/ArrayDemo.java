package javacode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.function.IntToDoubleFunction;
import java.util.stream.IntStream;

public class ArrayDemo extends Demo {
	public static void demo(){
		
		listToArray();
		arraysDemo();
		num2Rmb();
	}
	
	private static void listToArray(){
		printLine("listToArray *******************");
		List<Integer> list = new ArrayList<Integer>();
		list.add(100);
		list.add(200);
		list.add(300);
		list.add(400);
		
		Integer[] intArr = list.toArray(new Integer[0]);
		printLine(Arrays.toString(intArr));
	}
	
	public static void arraysDemo(){
		//
		printLine("parallelPrefix ***********");
		int[] intArr = {4,5,6,7};
		
		Arrays.parallelPrefix(intArr, (left, right) -> {
			printLine("left = " + left + ", right =" + right);
			return left + right;
		});
		
		printLine(Arrays.toString(intArr));
		
		printLine("parallelPrefix fromIndex, toIndex ***********");
		int[] intArr2 = {4,5,6,7};
		Arrays.parallelPrefix(intArr2,0,2, (left, right) -> {
			printLine("left = " + left + ", right =" + right);
			return left + right;
		});
		printLine(Arrays.toString(intArr2));
		
		printLine("\nsetAll ***********");
		int[] intArr3 = {4,5,6,7};
		double[] doubleArr = new double[intArr3.length];
		//arrIndex 数组下标，从0开始
		Arrays.setAll(doubleArr, arrIndex -> {
			printLine("arrIndex = " + arrIndex);
			return arrIndex * 0.5;
		});
		printLine(Arrays.toString(doubleArr));
		
		
		printLine("\nsort ****************");
		int[] intArr4 = {7,4,8,1};
		Arrays.parallelSort(intArr4);
		printLine(Arrays.toString(intArr4));
		
		printLine("spliterator **********");
		int[] intArr5 = {2,4,8,6};
		Spliterator.OfInt intSplit =  Arrays.spliterator(intArr5);
	
		printLine("Arrays.stream ************");
		int[] intArr6 = {7,4,8,1};
		IntStream iStream = Arrays.stream(intArr6);
	}
	
	private static void num2Rmb(){
		printLine("num2Rmb **************");
		ArrayDemo aDemo = new ArrayDemo();
		Num2Rmb nr = aDemo.new Num2Rmb();
		printLine(Arrays.toString(nr.divice(811.239)));//[811, 24]
		printLine(nr.toHanStr("8008"));//捌千零捌
 	}
	
	class Num2Rmb{
		public Num2Rmb(){
			
		}
		private String[] hanArr = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
		private String[] unitArr = {"十","百","千"};
		
		/**
		 * 将小数拆分成整数和小数部分
		 * @param num
		 * @return
		 */
		public String[] divice(double num){
			long intPart = (long)num;
			long decimalPart = Math.round((num - intPart) * 100);
			
			return new String[] {intPart + "", decimalPart + ""};
		}
		
		public String toHanStr(String numStr){
			String result = "";
			
			int numLength = numStr.length();
			for(int i = 0; i < numLength; i++){
				int num = numStr.charAt(i) - 48;
				int nextNum = 0;
				if (i < numLength-1 && num ==0) {
					nextNum = numStr.charAt(i + 1) - 48;
					if (num == nextNum ) {
						continue;
					}
				}
				
				if (num !=0 && i != numLength-1) {
					result += hanArr[num] + unitArr[unitArr.length -1 - i];
				}
				else {
					result += hanArr[num];
				}
			}
			
			return result;
		}
	}
	
}
