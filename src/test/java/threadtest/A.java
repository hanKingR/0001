package threadtest;

public class A {

	public static void main(String[] args) {
		A a = new A();
		a.aaa();
	}

	
	public void aaa() {
		System.out.println(Thread.currentThread().getName());
	}
	
}
