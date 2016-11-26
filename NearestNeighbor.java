//Setup
package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class MainNearest {
//Defines variables
	private ArrayList<Integer> xCoord=new ArrayList<Integer>(); // array of x coords of each node
	private ArrayList<Integer> yCoord=new ArrayList<Integer>(); // array of y coords of each node
	private double distance=0; //total distance
	private int firstX=0; //first x coord
	private int firstY=0; //first y coord
	public static void main(String[] args){
		MainNearest main=new MainNearest();
		System.out.println(main.getDistance());
	}
  //Finds full path using nearest neighbor and reports length
	public MainNearest(){
 		try{
      //reads in coordinates of nodes
			BufferedReader reader=new BufferedReader(new FileReader("BUGE.txt"));
			String str;
			while((str=reader.readLine())!=null){
				readString(str);
			}
			reader.close();
		}
		catch(Exception e){
			System.out.println("Input file not found");
		}
    //Start at random node, and begins choosing nearest neighbor
		Random rand=new Random();
		int index=Math.abs(rand.nextInt()%xCoord.size());
		firstX = xCoord.get(index);
		firstY = yCoord.get(index);
		xCoord.remove(index);
		yCoord.remove(index);
		nextIteration(firstX,firstY);
	}
  //Gets the distance
  public double getDistance(){
		return distance;
	}
	public void nextIteration(int x, int y){
    //If all nodes have been visted Updates distance by adding the distance from teh last node to the first node
		if(xCoord.size()==0){
			distance+=Math.sqrt((x-firstX)*(x-firstX)+(y-firstY)*(y-firstY));
			return;
		}
    //Finds closest node
		double closest=Integer.MAX_VALUE;
		int cX=0;
		int cY=0;
		int index=0;
		for(int i=0;i<xCoord.size();i++){
			int otherX=xCoord.get(i);
			int otherY=yCoord.get(i);
			if((x-otherX)*(x-otherX)+(y-otherY)*(y-otherY)<closest){
				closest=(x-otherX)*(x-otherX)+(y-otherY)*(y-otherY);
				index=i;
				cX=otherX;
				cY=otherY;
			}
		}
		distance+=Math.sqrt(closest);
    // Adds distance from previos node to next node
		System.out.println(cX+" , "+cY);
		xCoord.remove(index);
		yCoord.remove(index);
		nextIteration(cX,cY);
	}
  //Reads a string from the file and adds its coordinats to the array
	public void readString(String str){
		str=str.substring(str.indexOf(' ')+1);// This line is used to skip the index values as they are irrelevant... will break it if there are no index values
		xCoord.add(Integer.valueOf(str.substring(0,str.indexOf(' '))));
		str=str.substring(str.indexOf(' ')+1);
		yCoord.add(Integer.valueOf(str.substring(0,str.length())));
	}
}

