package application;
import java.io.Serializable;

public class Person implements Serializable  {

	
    private static final long serialVersionUID = 1L;
	double weigth,height;
	int age;
	String gender;
	
	
	public Person() {}
	
	public void SetParameters(double a, double b, int c, String d) {
		weigth = a;
		height = b;
		age = c;
		gender = d;
		}
	
	
}
