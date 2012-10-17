package com.gabeochoa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SortAndDisplay {
    
    public static void main(String[] args) throws FileNotFoundException {
        
    	File file = null;
    	
    	//file = getFileWithInput();
    	file = getFile("Data.csv");
    	
        Scanner input = new Scanner(file);
        	
        ArrayList<Person> people = new ArrayList<Person>();
        
        while(input.hasNextLine()) {
            
        	try{
        	String line = input.nextLine();
            String[] pieces = line.split(",");
            String name = pieces[0];
            int hours = Integer.parseInt(pieces[4]);
            
            people.add(new Person(name, hours));
        	}
        	catch(Exception e){}
        }
        input.close();
        
        ArrayList<Person> sorted = quicksort(people);

    	FileOutputStream outfile;
        try {
        	
        	outfile = new FileOutputStream("SortedData.csv", false);
            PrintWriter output = new PrintWriter(outfile);
            
            output.println("Name, Hours Available");
            
            for(Person p: sorted)
            	output.println(p);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
   
    private static File getFile(String string) {
    	
    	File inFile = null;
        boolean goodFile = false; 
        
        while(!goodFile) {
            String inFileName = string;
            inFile = new File(inFileName);
            //SEE THE NOTE ABOUT THE FILE AT THE END OF THE PAGE
            if(!inFile.exists()) {
                System.out.println("\nThat file does not exist or is in a " +
                        "different directory");
                return null;
            } else {
                goodFile = true;
            }
        }
        return inFile;
	}

	@SuppressWarnings("unused")
	private static File getFileWithInput() {
    	
    	System.out.println("Please give the name of the input file");
        Scanner kbd = new Scanner(System.in);
        File inFile = null;
        boolean goodFile = false; 
        while(!goodFile) {
            String inFileName = kbd.nextLine();
            inFile = new File(inFileName);
            //SEE THE NOTE ABOUT THE FILE AT THE END OF THE PAGE
            if(!inFile.exists()) {
                System.out.println("\nThat file does not exist or is in a " +
                        "different directory");
                System.out.println("Please give the name of the input file");
            } else {
                goodFile = true;
            }
        }
        return inFile;
	}

	public static ArrayList<Person> quicksort(ArrayList<Person> people) {

        if (people.size() <= 1)
            return people;
        
        int pivot = people.size() / 2;
        
        ArrayList<Person> lesser = new ArrayList<Person>();
        ArrayList<Person> greater = new ArrayList<Person>();
        
        int sameAsPivot = 0;
        
        
        for (Person p : people) {
            if (p.hours < people.get(pivot).hours)
                greater.add(p);
            else if (p.hours > people.get(pivot).hours)
                lesser.add(p);
            else
                sameAsPivot++;
        }
        
        lesser = quicksort(lesser);
        
        for (int i = 0; i < sameAsPivot; i++)
            lesser.add(people.get(pivot));
        
        greater = quicksort(greater);
        
        ArrayList<Person> sorted = new ArrayList<Person>();
        
        for (Person p : lesser)
            sorted.add(p);
        for (Person p: greater)
            sorted.add(p);
        
        return sorted;
    }
}
