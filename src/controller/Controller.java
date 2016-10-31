package controller;

import java.util.ArrayList;

import model.CompareHistograms;
import model.ResultImageData;

public class Controller
{
	CompareHistograms comparator;

	public Controller()
	{
		comparator = new CompareHistograms();
	}

	public double getSimilarity(double sig, int[] histogram1, int imageWidth1, int imageHeight1, int[] histogram2,int imageWidth2, int imageHeight2)
	{
		return comparator.getSimilarity(sig, histogram1, imageWidth1, imageHeight1, histogram2, imageWidth2,imageHeight2);
	}

	public ArrayList<ResultImageData> compare(double sig, String imagePath1, String imageFilename1, String imagesRepo)
	{
		return comparator.compare(sig, imagePath1, imageFilename1, imagesRepo);
	}

	public ArrayList<ResultImageData> compareCCV4(String imagePath1, String imageFilename1, String imagesRepo, int threshold, int nColors)
	{
		return comparator.compareCCV4(imagePath1, imageFilename1, imagesRepo, threshold, nColors);
	}
	
	public ArrayList<ResultImageData> compareWithCR(int percent, String imagePath1, String imageFilename1, String imagesRepo)
	{
		return comparator.compareWithCR(percent, imagePath1, imageFilename1, imagesRepo);
	}
	
}
