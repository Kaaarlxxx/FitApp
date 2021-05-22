package application;

import java.io.Serializable;

public class Dish implements DishInt,Serializable {

    private static final long serialVersionUID = 1L;
	double Carb,pro,fats;
	public Dish(double a,double b , double c) {
	pro = a;
	fats = b;
	Carb = c;	
	}
	public Dish() {}
	
	public double GetCalories() {	
		return pro * 4 + fats * 4 + Carb * 9;
	}
	
	public void SetParameters(double parseDouble, double parseDouble2, double parseDouble3) {
		pro = parseDouble;
		fats = parseDouble2;
		Carb = parseDouble3;	
		
	}
	@Override
	public String ToString() {
		return "Posi�ek sk�ada si� z " + pro + "g bia�ka" + fats + "g t�uszczu oraz" + Carb + "g w�glowodan�w ";
	}

	
	}
	
