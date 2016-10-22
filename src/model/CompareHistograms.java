package model;

public class CompareHistograms {

	
	public int[] getNormalizedHistogram(int[] histogram, int imageWidth, int imageHeight){
		int[] nH = new int[159];
		
		for(int i=0; i<159; i++){
			nH[i] = histogram[i]/(imageWidth*imageHeight);
		}
		
		return nH;
	}
	
	public double getSimilarity(int[] histogram1, int[] histogram2, int imageWidth, int imageHeight){
		int[] nh1 = getNormalizedHistogram(histogram1, imageWidth, imageHeight);
		int[] nh2 = getNormalizedHistogram(histogram2, imageWidth, imageHeight);
		
		return 0.0;
	}
}
