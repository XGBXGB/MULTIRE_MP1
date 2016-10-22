package controller;

import model.CompareHistograms;

public class Controller {
	CompareHistograms comparator;

	public double getSimilarity(int[] histogram1, int[] histogram2, int imageWidth, int imageHeight){
		return comparator.getSimilarity(histogram1, histogram2, imageWidth, imageHeight);
	}
}
