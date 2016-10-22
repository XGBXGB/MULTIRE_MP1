package model;

import java.util.ArrayList;

public class CompareHistograms {

	
	public double[] getNormalizedHistogram(int[] histogram, int imageWidth, int imageHeight){
		double[] nH = new double[159];
		
		for(int i=0; i<159; i++){
			nH[i] = histogram[i]/(imageWidth*imageHeight);
		}
		
		return nH;
	}
	
	public double getSimilarity(double sig, int[] histogram1, int imageWidth1, int imageHeight1, int[] histogram2, int imageWidth2, int imageHeight2){
		double[] nh1 = getNormalizedHistogram(histogram1, imageWidth1, imageHeight1);
		double[] nh2 = getNormalizedHistogram(histogram2, imageWidth2, imageHeight2);
		int bigN = 0;
		
		double sum = 0;
		for(int i=0; i<159; i++){
			if(nh1[i]>sig){
				bigN++;
				sum += 1-(Math.abs(nh1[i]-nh2[i])/Math.max(nh1[i], nh2[i]));
			}
		}
		return bigN*sum;
	}
	
	public ArrayList<ResultImageData> compare(double sig, String imagePath1, String imageFilename1, String imagesRepo){
		ArrayList<ResultImageData> images = new ArrayList();
		ImageObject basis = new ImageObject(imagePath1, imageFilename1);
		int basisWidth = basis.getImageObject().getWidth();
		int basisHeight = basis.getImageObject().getHeight();
		basis.initializeHistogram();
		int[] basisHistogram = basis.getHistogram();
		
		for(int i=0; i<2000; i++){
			ImageObject sample = new ImageObject(imagesRepo, i+".jpg");
			int sampleWidth = sample.getImageObject().getWidth();
			int sampleHeight = sample.getImageObject().getHeight();
			sample.initializeHistogram();
			int[] sampleHistogram = sample.getHistogram();
			double weight = getSimilarity(sig, basisHistogram, basisWidth, basisHeight, sampleHistogram, sampleWidth, sampleHeight);
			images.add(new ResultImageData(sample.getFileName(), weight));
		}
		
		return images;
	}
}
