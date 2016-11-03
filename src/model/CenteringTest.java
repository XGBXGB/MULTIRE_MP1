package model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CenteringTest {
	public static void main(String[] args) {
	
		int width = 12, height = 8, centerW=0, centerH=0;
		int num = 72;
		int incrementer = 1;
		if (num % 2 != 0) {
			incrementer = 2; // only test the odd ones
		}
		int big=0,small=0;
		if(width>height){
			big=width;
			small = height;
		}else{
			big=height;
			small=width;
		}
		ArrayList<Data> numbers = new ArrayList();
		for (int i = 1; i <= num / 2; i += incrementer) {
			if (num % i == 0) {
				int temp1 = i;
				int temp2 = num / i;
				if (temp1 <= small && temp2 <= big) {
					if(width>height){
						numbers.add(new Data(temp2, temp1));
						centerW=temp2;
						centerH=temp1;
					}else{
						numbers.add(new Data(temp1, temp2));
						centerW=temp1;
						centerH=temp2;
					}
//					System.out.println("centerW:" + centerW + " centerH:" + centerH);
//					break;
				}
			}
		}
		int startPosW=0, startPosH=0;
		for(int i=numbers.size()-1; i>=0; i--){
			startPosW = getPositionOffset(width, numbers.get(i).getInt1());
			startPosH = getPositionOffset(height, numbers.get(i).getInt2());
			if(startPosH!=-1 && startPosW!=-1){
				centerW = numbers.get(i).getInt1();
				centerH = numbers.get(i).getInt2();
				break;
			}
		}
		System.out.println("centerW:" + centerW + " centerH:" + centerH);
		
		for(int i=0; i<numbers.size(); i++){
			System.out.println("yay: "+numbers.get(i).getInt1()+" "+numbers.get(i).getInt2());
		}
	}

	public static int getPositionOffset(int length, int chunkLength) {
		for (int i = 0; i < length; i++) {
			if (i == length - (i + chunkLength)) {//6 == 12-(6+0)
				return i;
			}
		}
		return -1;
	}
	
	static class Data{
		private int int1;
		private int int2;
		public Data(int int1, int int2){
			this.int1 = int1;
			this.int2 = int2;
		}
		public int getInt1() {
			return int1;
		}
		public void setInt1(int int1) {
			this.int1 = int1;
		}
		public int getInt2() {
			return int2;
		}
		public void setInt2(int int2) {
			this.int2 = int2;
		}
	}
}
