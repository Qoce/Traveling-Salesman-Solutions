package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class Main {
  //Defines Variables
	private ArrayList<Integer> kX=new ArrayList<Integer>();
	private ArrayList<Integer> kY=new ArrayList<Integer>();
	private ArrayList<Integer> tX=new ArrayList<Integer>();
	private ArrayList<Integer> tY=new ArrayList<Integer>();
  //Finds distance
	public double getDistance(){
		try{
      //Read in data file with coordinates
			BufferedReader reader=new BufferedReader(new FileReader("BUGE.txt"));
			String str=reader.readLine();
			while((str=reader.readLine())!=null){
				readString(str);
			}
			reader.close();
		}
		catch(Exception e){
			System.out.println("Input file not found");
		}
    //Starting at a random node, pick the nearest node, then pick the node that has not been picked yet in which the sum of the distances of it and the two closest nodes subtracted from the distance between the two closest nodes to it is smallest, and add it to the loop. 
		Random rand=new Random();
		int index=Math.abs(rand.nextInt()%kX.size());
		int x=kX.get(index);
		int y=kY.get(index);
		kX.remove(index);
		kY.remove(index);
		tX.add(x);
		tY.add(y);
		getClosestPoint(x,y);
		tX.add(x);
		tY.add(y);
		while(kX.size()>0){
			int nextPoint=findClosestPoint();
	    //Picks next point
      changePoint(nextPoint,getOptimalLocation(nextPoint));
		}
		double distance=0;
		for(int i=0;i<tX.size()-1;i++){
	    //Updates distance by adding the distance between the two starting points, this gives the final distance. This is done after the rest of the distance is calculated.		
      distance+=Math.sqrt(getSquareOfDistance(tX.get(i),tY.get(i),tX.get(i+1),tY.get(i+1)));
			System.out.println(tX.get(i)+" , "+tY.get(i));
		}
		return distance;
	}
  //Finds optimal location by determining which point has lowest insertion cost
	public int getOptimalLocation(int index){
		int x=kX.get(index);
		int y=kY.get(index);
		double closest=Integer.MAX_VALUE;
		int nextIndex=0;
		for(int i=0;i+1<tX.size();i++){
			int jX=tX.get(i);
			int jY=tY.get(i);
			int iX=tX.get(i+1);
			int iY=tY.get(i+1);		
      //Calculates the insertion cost
      double p=Math.sqrt(getSquareOfDistance(jX,jY,x,y))+Math.sqrt(getSquareOfDistance(iX,iY,x,y))-Math.sqrt(getSquareOfDistance(iX,iY,jX,jY));
			if(p<closest){
				closest=p;
				nextIndex=i+1;
			}
		}
		return nextIndex;
	}
  //Finds the closest node to the curret subtour
	public int findClosestPoint(){
		int closest=Integer.MAX_VALUE;
		int index=0;
		for(int i=0;i<tX.size();i++){
			int x=tX.get(i);
			int y=tY.get(i);
			for(int j=0;j<kX.size();j++){
				int nextX=kX.get(j);
				int nextY=kY.get(j);
				if(getSquareOfDistance(nextX,nextY,x,y)<closest){
					closest=(x-nextX)*(x-nextX)+(y-nextY)*(y-nextY);
					index=j;
				}
			}
		}
		return index;
	}
  // Finds the clost point to the specififed point, used at the beggining to chose the secone point in the tour
	public void getClosestPoint(int x, int y){
		if(kX.size()==0) return;
		int closest=Integer.MAX_VALUE;
		int index=0;
		for(int i=0;i<kX.size();i++){
			int nextX=kX.get(i);
			int nextY=kY.get(i);
			if(getSquareOfDistance(nextX,nextY,x,y)<closest){
				closest=(x-nextX)*(x-nextX)+(y-nextY)*(y-nextY);
				index=i;
			}
		}
		changePoint(index);
	}
  //Gets the square of distance bewteen two points
	public int getSquareOfDistance(int x1,int y1, int x2,int y2){
		return (x1-x2)*(x1-x2)+(y1-y2)*(y1-y2);
	}
	public void readString(String str){
		str=str.substring(str.indexOf(' ')+1);// This line is used to skip the index values as they are irrelevant... will break it if there are no index values
		kX.add(Integer.valueOf(str.substring(0,str.indexOf(' '))));
		str=str.substring(str.indexOf(' ')+1);
		kY.add(Integer.valueOf(str.substring(0,str.length())));
	}
  //Changes the index of the point to the end of the array
	public void changePoint(int orig){
		int x=kX.get(orig);
		int y=kY.get(orig);
		tX.add(x);
		tY.add(y);
		kX.remove(orig);
		kY.remove(orig);
	}
  //Changes the index of the point to the specified destination
	public void changePoint(int orig,int dest){
		int x=kX.get(orig);
		int y=kY.get(orig);
		tX.add(dest,x);
		tY.add(dest,y);
		kX.remove(orig);
		kY.remove(orig);
	}

}
