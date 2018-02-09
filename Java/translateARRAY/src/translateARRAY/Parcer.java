package translateARRAY;

import java.io.*;


public class Parcer {

	public double[][] parseFile(String filePath) throws IOException {
		FileReader fr = new FileReader(filePath); 
		BufferedReader br = new BufferedReader(fr); 
		String s,buffer = null; 
		String[] splitString = null;
		int length=1;
		String finalArray= "{{";
		//Ignore first line
		br.readLine();
			
		
		while((s = br.readLine()) != null) { 
			splitString=s.split(",");
			finalArray=finalArray+splitString[3]+","+splitString[4]+",10"+"}"+","+"\n"+"{";
			length++;
			
		} 
		finalArray=finalArray+length;
				
		System.out.println(finalArray);
			fr.close();
			return null; 
		 
		

}

}