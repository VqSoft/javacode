package javacode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CollectionDemo extends Demo {
	public static void demo(){
		deepCopyCollection();
	}
	
	public static void sublist(){
		printLine("sublist demo:");
		List<Integer> intList =new ArrayList<>();
		intList.add(11);
		intList.add(12);
		intList.add(13);
		int idx = 0;
		testIndex(idx++,idx);
		List<Integer> subIntList = intList.subList(idx++, idx);
		printLine(subIntList.toString());
	}
	
	private static void testIndex(int fromIdx, int toIdx){
		printLine("From index: " + fromIdx + ", to index: " + toIdx);
	}
	
	private static void copyCollection(){
		ArrayList<Student> students = new ArrayList<>();
		students.add(new Student("n1"));
		students.add(new Student("n2"));
		students.add(new Student("n3"));
		
		ArrayList<Student> arr2 = new ArrayList<>();
		//Collections.addAll(arr2, new Student[students.size()]);
		arr2.addAll(students);
		printLine("Source siez = "+ students.size() + ", dest size =" + arr2.size());//Source siez = 3, dest size =3
		Collections.copy(arr2, students);//shallow copy
		
		Iterator<Student> ites = arr2.iterator();
		if (ites.hasNext()) {
			ites.next();
			ites.remove();
		}
		printLine("Source siez = "+ students.size() + ", dest size =" + arr2.size());//Source siez = 3, dest size =2
		
		printLine(String.valueOf(arr2.get(0) == students.get(0)));
	}
	
	private static void deepCopyCollection(){
		ArrayList<Student> students = new ArrayList<>();
		students.add(new Student("n1"));
		students.add(new Student("n2"));
		students.add(new Student("n3"));
		Student[] srcArr = new Student[students.size()];
		students.toArray(srcArr);
		
		Student[] destArr = new Student[students.size()];
		System.arraycopy(srcArr, 0, destArr, 0, students.size());
		
		printLine("Source siez = "+ srcArr.length + ", dest size =" + destArr.length);//Source siez = 3, dest size =3
		
		printLine(String.valueOf(srcArr[0].hashCode() == destArr[0].hashCode()));//true, still shallow copy
	}
	
	private static class Student{
		private String name;
		
		public Student(String pName){
			this.name = pName;
		}
	}
	
}
