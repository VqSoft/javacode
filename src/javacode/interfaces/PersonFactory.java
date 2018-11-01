package javacode.interfaces;

interface PersonFactory<P extends Person>{
	P create(String name, int age);
}
