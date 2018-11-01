package javacode.enums;

public class JavaEnumDemo {
	/**
	 * 普通枚举
	 * 
	 *
	 */
	public enum EnumGender{
		Male,
		Female
	}
	
	/**
	 * 
	 * 枚举还可以添加静态的和非静态的方法和属性。
	 *
	 */
	public enum EnumSeason{
		Spring,Summer,Autumn,Winter;
		
		private final static String springText = "spring";
		
		public static EnumSeason getSeason(String s){
			if (s.equals(springText)) {
				return EnumSeason.Spring;
			}
			return EnumSeason.Summer;
		}
	}
	
	/**
	 * 带构造函数的枚举，构造函数只能是private的
	 * @author ezhao3
	 *
	 */
	public enum EnumColor{
		Red("red"),Green("green"),Blue("blue");
		
		private final String name;
		
		private EnumColor(String name) {
			this.name = name;
		}
		
		public String getName(){
			return name;
		}
	}
	
	public enum EnumColor2{
		Red(1,"red"),Green(2,"green"),Blue(3,"blue");
		
		private final int index;
		private final String name;
		
		private EnumColor2(int idx, String name) {
			this.index = idx;
			this.name = name;
		}
		
		public String getName(){
			return name;
		}
		
		public int getIndex(){
			return index;
		}
	}
	
	public interface GenderDesc{
		void info();
	}
	
	public enum Gender implements GenderDesc{
		MALE("男")
		//花括号部分实际上是一个类体部分
		{
			@Override
			public void info() {
				System.out.println("男");
			}
		},
		FEMAIL("女")
		{
			@Override
			public void info() {
				System.out.println("女");
			}
		};
		
		private final String name;
		
		private Gender(String pName){
			this.name = pName;
		}
	}
	
	/**
	 * 
	 * 带有抽象方法的枚举,枚举会被默认加上abstract
	 *
	 */
	public enum EnumOrderStatus{
		
		Cancel { @Override
		public String getStatus() {
			return "Has Cancelled.";
		} },
		WaitConfirm { @Override
			public String getStatus() {
				return "Wait confirm.";
			} },
		Received { @Override
				public String getStatus() {
					return "Received.";
				} };
		
		public abstract String getStatus();
	}
	
	public static void demoEnum(){
		//变量赋值
		EnumGender male = EnumGender.Male;
		if (male == EnumGender.Male) {
			System.out.println("Male: " + male);
		}
		
		//遍历枚举
		for (EnumGender gender : EnumGender.values()) {
			System.out.println("gender: " + gender);
		}
		
		//获取索引位置
		System.out.println(EnumColor.Red.ordinal());//0
		System.out.println(EnumColor.Green.ordinal());//1
		
		System.out.println(EnumColor2.Red.ordinal());//0
		
		System.out.println(EnumColor2.Red.name);//red
	}
}
