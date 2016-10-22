package controller;

import model.CompareHistograms;

public class Controller {
	CompareHistograms comparator;

	public double getSimilarity(double sig, int[] histogram1, int imageWidth1, int imageHeight1, int[] histogram2, int imageWidth2, int imageHeight2){
		return comparator.getSimilarity(sig, histogram1, imageWidth1, imageHeight1, histogram2, imageWidth2, imageHeight2);
	}
}
