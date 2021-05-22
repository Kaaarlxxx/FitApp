package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import java.io.File;  
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Controller {
	
	
	//SET YOUR PATH FIRST
		String SetYourPathHere = "C:\\Users\\Karol\\Desktop\\Dane\\";
		
		
		
		
		
	//Kcal per 1g 
	final double Prog = 4;
	final double Fatg = 4;
	final double Carg = 9;
	

	//FXML elements
	@FXML Button BMIcalc,Submit,LogIn,SumUpWeekbef;
	@FXML Label calcres,date,Standard,WDP,WDF,WDC,FullKcal;
	@FXML TextField weigth,height,age,pdn,fdn,cdn,pbr,fbr,cbr,psp,csp,fsp,nickname,Gender;
	
	//needed variables and objects
	static Date ActDate;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
    SimpleDateFormat weeknum = new SimpleDateFormat("ww");
    SimpleDateFormat weekday = new SimpleDateFormat("E");
    double WholeDayProteins,WholeDayFats,WholeDayCarbo,DayKcal;
    public String Nickname = "";
	Dish sniadanie = new Dish();
	Dish obiad = new Dish();
	Dish kolacja = new Dish();
	Person hum = new Person();
	ArrayList<Dish> posiłki = new ArrayList<Dish>();
	String[] daynames = {"pon", "wt", "�r", "czw","pt","sob","niedz"};
	double[] daycalories = new double[7];
	
	
	//Function starts when the project get started - initializing date
	@FXML void initialize() {
		ActDate = new Date();
		date.setText(simpleDateFormat.format(ActDate));
	}
	
	
	//supp function to create files
void CreateFile(String sciezka) {
		
		try {
		      File myObj = new File(sciezka);
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }	
	
	}
//sup function to create directory
void CreateDirectory(String sciezka) {
	try {
	    Path path = Paths.get(sciezka);
	    Files.createDirectories(path);
	    System.out.println("Directory is created!");
	  } catch (IOException e) {
	    System.err.println("Failed to create directory!" + e.getMessage());
	  }

}

//support function to set Label according to BMI
	private String BMIbuild(double BMIval) {
		if(BMIval < 18.5) return " Masz niedowage";
		else if (BMIval >=18.5 && BMIval < 24.9) return "Twoja waga jest prawidlowa";
		else if (BMIval >=24.9 && BMIval < 30) return "Masz nadwage";
		else return "Jestes otyly";
		
	}
	
	@FXML
	//function to log in 
	void LOG() {	
		
		//Logging in , setting nickname
		Nickname = nickname.getText();	
		//setting strings - paths for files
		String paf = SetYourPathHere+Nickname;
		String patfd = SetYourPathHere+Nickname+"\\Data";
		String patf = SetYourPathHere+Nickname+"\\Tydzien"+weeknum.format(ActDate);
		//Creating Paths
		CreateDirectory(paf);
		CreateDirectory(patf);
		CreateDirectory(patfd);
		//Strings below are FileName Strings
		final String ParametersDataPath = SetYourPathHere+Nickname+"\\Data\\ParametryData";
		final String DataPath = SetYourPathHere+Nickname+"//Tydzien"+weeknum.format(ActDate)+"\\"+weekday.format(ActDate);
		CreateFile(DataPath);
		//Unpacking Objects - dishes
		try {
		FileInputStream fi = new FileInputStream(new File(DataPath));
		ObjectInputStream oi = new ObjectInputStream(fi);

		Dish sniadanie = (Dish) oi.readObject();
		Dish obiad = (Dish) oi.readObject();
		Dish kolacja = (Dish) oi.readObject();

		oi.close();
        fi.close();
        if(sniadanie!=null) {
			pbr.setText(String.valueOf(sniadanie.pro));
			fbr.setText(String.valueOf(sniadanie.fats));
			cbr.setText(String.valueOf(sniadanie.Carb));
			}
			if(obiad!=null) {
				pdn.setText(String.valueOf(obiad.pro));
				fdn.setText(String.valueOf(obiad.fats));
				cdn.setText(String.valueOf(obiad.Carb));
				}
			if(kolacja!=null) {
				psp.setText(String.valueOf(kolacja.pro));
				fsp.setText(String.valueOf(kolacja.fats));
				csp.setText(String.valueOf(kolacja.Carb));
				}	
			}
		catch (FileNotFoundException e) {System.out.println("File not found");} 
		catch (IOException e) {System.out.println("Error!");} 
		catch (ClassNotFoundException e) {e.printStackTrace();}		
		
		WholeDay();
		//Unpacking object - person
		try {
		FileInputStream fi = new FileInputStream(new File(ParametersDataPath));
        ObjectInputStream oi = new ObjectInputStream(fi);
        Person hum = (Person) oi.readObject();
        if(hum!=null) {
        weigth.setText(String.valueOf(hum.weigth));
        height.setText(String.valueOf(hum.height));
        age.setText(String.valueOf(hum.age));
        Gender.setText(hum.gender);
        }
        oi.close();
        fi.close();
        double waga = Double.parseDouble(weigth.getText());
		double wzrost = Double.parseDouble(height.getText());
		double BMI = Math.round(waga / Math.pow(wzrost,2)*1000000);
		BMI = BMI/100;
		calcres.setText("Twoje BMI - " + String.valueOf(BMI));
		Standard.setText(BMIbuild(BMI));
		}
		catch (FileNotFoundException e) {System.out.println("File not found");} 
		catch (IOException e) {System.out.println("Error!");} 
		catch (ClassNotFoundException e) {e.printStackTrace();}	
		
    } 
	
	

	
	@FXML
	public void BM() {
		//Oversaving parameters Fields and Calculating BMI - saving it to file
		String ParametersPath = SetYourPathHere+Nickname+"\\ParametryPersonalne.txt";
		final String ParametersDataPath = SetYourPathHere+Nickname+"\\Data\\ParametryData";
		double waga = Double.parseDouble(weigth.getText());
		double wzrost = Double.parseDouble(height.getText());
		int wiek = Integer.parseInt(age.getText());
		String plec = Gender.getText();
		double BMI = Math.round(waga / Math.pow(wzrost,2)*1000000);
		BMI = BMI/100;
		calcres.setText("Twoje BMI - " + String.valueOf(BMI));
		Standard.setText(BMIbuild(BMI));
		CreateFile(ParametersPath);
		try {
		      FileWriter myWriter = new FileWriter(ParametersPath);
		      myWriter.write("Twoja obecna waga - "+waga);
		      myWriter.write("\nTwoj wzrost - "+wzrost);
		      myWriter.write("\nTwoj wiek - "+wiek);
		      myWriter.write("\nPlec - "+plec);
		      myWriter.write("\nTwoj wskaznik BMI - "+ BMI);
		      myWriter.write("\nWedlug wskaznika BMI "+BMIbuild(BMI));
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		hum.SetParameters(waga,wzrost,wiek,plec);
		CreateFile(ParametersDataPath);
		try {
            FileOutputStream f = new FileOutputStream(new File(ParametersDataPath));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(hum);
            o.close();
            f.close();
		} catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
			
	}
	@FXML
	public void WholeDay() {
		
		//Suming up a Day

		sniadanie.SetParameters(Double.parseDouble(pbr.getText()),Double.parseDouble(fbr.getText()),Double.parseDouble(cbr.getText()));
		obiad.SetParameters(Double.parseDouble(pdn.getText()),Double.parseDouble(fdn.getText()),Double.parseDouble(cdn.getText()));
		kolacja.SetParameters(Double.parseDouble(psp.getText()),Double.parseDouble(fsp.getText()),Double.parseDouble(csp.getText()));
		WholeDayProteins = sniadanie.pro+obiad.pro+kolacja.pro;
		WholeDayFats = sniadanie.fats+obiad.fats+kolacja.fats;
		WholeDayCarbo = sniadanie.Carb+obiad.Carb+kolacja.Carb;
		DayKcal = sniadanie.GetCalories()+obiad.GetCalories()+kolacja.GetCalories();
		WDP.setText("Bialka spozyte podczas calego dnia - "+WholeDayProteins);
		WDF.setText("Tluszcze spozyte podczas calego dnia - "+WholeDayFats);
		WDC.setText("Weglowodany spozyte podczas calego dnia - "+WholeDayCarbo);
		FullKcal.setText("Dzisiejsze spozyte Kalorie = "+ DayKcal);
		
	}
	@FXML
	
	//Func - saving dishes(objects) to file and Day Summed up
	public void SaveToFile() {
		
		//Saving macro to file
		String DataPath = SetYourPathHere+Nickname+"//Tydzien"+weeknum.format(ActDate)+"\\"+weekday.format(ActDate);
		String fileName = SetYourPathHere+Nickname+"//"+simpleDateFormat.format(ActDate)+".txt";
		CreateFile(DataPath);
		CreateFile(fileName);
		try {
			FileOutputStream f = new FileOutputStream(new File(DataPath));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(sniadanie);
            o.writeObject(obiad);
            o.writeObject(kolacja);
            o.close();
            f.close();			 
		}catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } 
		
		try {
		      FileWriter myWriter = new FileWriter(fileName);
		      myWriter.write("Bialka spozyte podczas calego dnia - "+WholeDayProteins);
		      myWriter.write("\nTluszcze spozyte podczas calego dnia - "+WholeDayFats);
		      myWriter.write("\nWeglowodany spozyte podczas calego dnia - "+WholeDayCarbo);
		      myWriter.write("\nDzisiejsze spozyte Kalorie = "+ DayKcal);
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		        
		}
		
	
	public void SumUpWeek() {
		
		int lastweek = Integer.parseInt(weeknum.format(ActDate))-1;
		char tmp='0';
		double WholeWeekKcal = 0;
		double srkcal = 0;
		if(Integer.parseInt(weeknum.format(ActDate))>10) tmp = Character.MIN_VALUE;
		String path = SetYourPathHere+Nickname+"//Tydzien"+tmp+lastweek+"\\";
		String fileName = SetYourPathHere+Nickname+"//Podsumowanie Tygodnia "+lastweek+".txt";
		CreateFile(fileName);
		for(int i = 0; i < 7; i++) {
			String finalpath = path+daynames[i];
			//uploading dish values to week setup
			try {
				FileInputStream fi = new FileInputStream(new File(finalpath));
				ObjectInputStream oi = new ObjectInputStream(fi);
				Dish sniadanie = (Dish) oi.readObject();
				Dish obiad = (Dish) oi.readObject();
				Dish kolacja = (Dish) oi.readObject();	
				oi.close();
		        fi.close();
		       posiłki.add(sniadanie);
		       posiłki.add(obiad);
		       posiłki.add(kolacja);
					}
				catch (FileNotFoundException e) {
				sniadanie.SetParameters(0,0,0);
				obiad.SetParameters(0,0,0);
				kolacja.SetParameters(0,0,0);
				posiłki.add(sniadanie);
			    posiłki.add(obiad);
			    posiłki.add(kolacja);
				}
				catch (IOException e) {
		          System.out.println("Error!");} 
				catch (ClassNotFoundException e) {e.printStackTrace();}	
			
		}
		//adding calories value to days
		int j=0,k=0;
		for(int i = 0; i < posiłki.size(); i++) {
			if(k>2) 
			{
				j++;
				k=0;
			}
			if(posiłki.get(i)!=null) daycalories[j]+=posiłki.get(i).GetCalories();
			k++;
		}
		for(int i = 0; i < 7; i ++) WholeWeekKcal += daycalories[i];
		srkcal = Math.round(WholeWeekKcal/7*100)/100;
		try {
		      FileWriter myWriter = new FileWriter(fileName);
		      myWriter.write("kalorie z Poniedzialku - " + daycalories[0]);
		      myWriter.write("\nkalorie z Wtorku - " + daycalories[1]);
		      myWriter.write("\nkalorie ze srody - " + daycalories[2]);
		      myWriter.write("\nkalorie z Czwartku - " + daycalories[3]);
		      myWriter.write("\nkalorie z Piatku - " + daycalories[4]);
		      myWriter.write("\nkalorie z Soboty - " + daycalories[5]);
		      myWriter.write("\nkalorie z NIedzieli - " + daycalories[6]);
		      myWriter.write("\nkalorie w calym tygodniu - "+ WholeWeekKcal);
		      myWriter.write("\n�rednie dzienne kalorie - "+ srkcal);
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
	}
	
		
	}
	

