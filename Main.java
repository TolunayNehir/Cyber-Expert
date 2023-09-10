package cyberexpert;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;  
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
	static HashMap<Integer, String> readedprocesses = new HashMap<Integer, String>();
	static HashMap<Integer, String> tookedprocesses = new HashMap<Integer, String>();
	public static void main(String[] args) {
		int a=0;
		Scanner myReader=new Scanner(System.in);
		System.out.println("Welcome to Cyber-expert");
		System.out.println("Do you want to loop (loop count or 0):");
        int loop = myReader.nextInt();
        System.out.println("Sleep Time (minute):");
        int sleeptime=myReader.nextInt();
        while(loop>a) {
        configure();
		int ppoint=processcontrol();
	    int upoint=usagecontrol();
		int concpoint=expertcontrol(ppoint,upoint);
		
		if(concpoint==0) {
			System.out.println("Your riskpoint is 0 no risk :)");
		}
		else if(concpoint>=2) {
			System.out.println("Your riskpoint is 2 medium be careful");
		}
		else if(concpoint>=3) {
			System.out.println("Your riskpoint is 3 check the antivirus program and scan your computer");
		}
		else if(concpoint>=4) {
			System.out.println("Your riskpoint 4 or highter this is bad :( ");
		}
		a++;
		try {
			TimeUnit.MINUTES.sleep(sleeptime);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        }
		
	}
	
	
	public static void configure() {
		File myObj = new File("config1.txt");
	    Scanner myReader;
		try {
			myReader = new Scanner(myObj);
			int i=1;
			while (myReader.hasNextLine()) {
	        String data = myReader.nextLine();
	        readedprocesses.put(i,data);
	        i++;
	      }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
			
	}
	
	public static void networkcontrol() {
		
		 ProcessBuilder processBuilder = new ProcessBuilder();
		 ArrayList<String> builderList = new ArrayList<>();
		 builderList.add("cmd.exe");
	     builderList.add("/C");
	     builderList.add("nestat -n");
	     processBuilder.command(builderList);
         Process process;
         String log="netlog.txt";
         String line;
		try {
			process = processBuilder.start();  
			BufferedReader reader= new BufferedReader(new InputStreamReader(process.getInputStream()));
			FileWriter fw=new FileWriter(log);
			while ((line = reader.readLine()) != null) {
			     fw.append(line);
			 }
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("Network log saved to netlog.txt will not be evaluated");
		
	}
	
	public static int expertcontrol(int processpoint,int usagepoint) {
		
		int concpoint=usagepoint+processpoint;
		return concpoint;
	}
	
	public static int processcontrol() {
		
		 ProcessBuilder processBuilder = new ProcessBuilder();
		 ArrayList<String> builderList = new ArrayList<>();
		 builderList.add("cmd.exe");
	     builderList.add("/C");
	     builderList.add("tasklist");
	     processBuilder.command(builderList);
         Process process;
         try {
	     process = processBuilder.start();
         BufferedReader reader= new BufferedReader(new InputStreamReader(process.getInputStream()));
         String line;
         int i=1;
         String log="processlog.txt";
         FileWriter fw=new FileWriter(log);
			while ((line = reader.readLine()) != null) {
			     //System.out.println(line);
			     tookedprocesses.put(i,line);
			     fw.append(line);
			     i++;
			 }
		} catch (IOException e) {
			
			e.printStackTrace();
		}
        int point=0;
        ArrayList<String> suspicius = new ArrayList<String>();
        for(int i=1;i<=readedprocesses.size();i++) {
        	String tookedprocess=tookedprocesses.get(i);
        	if(tookedprocess==readedprocesses.get(i)) {
        		suspicius.add(tookedprocess);
        		point++;
        	}
        }
        System.out.println("Suspicius Processes List:");
        for(int i=1;i<=suspicius.size();i++) {
        	System.out.println(i+" "+suspicius.get(i));
        }
        
        System.out.println("Suspicius Process count is: "+point);
        if(point<=2 || point>0) {
        	return 1;
        }
        else if(point>2) {
        	return 2;
        }
        else {
        	return 0;
        }
		
	}
	
	public static int usagecontrol() {
		File cDrive = new File("C:");
		double space1=(double)cDrive.getTotalSpace();
		System.out.println(String.format("Total space is: ",space1));
		try {
			TimeUnit.MINUTES.sleep(3);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		double space2=(double)cDrive.getTotalSpace();
		System.out.println(String.format("New Total space is: ",space2));
		if(space1==space2) {
			return 0;
		}
		else if(space1>space2) {
			return 2;
		}
		else {
			return 0;
		}
		
	}

}
