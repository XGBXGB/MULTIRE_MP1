package view;

import model.cieConvert;

public class Driver 
{
	public static void main(String[] args)
	{
		cieConvert comparator = new cieConvert();
		double[][] matrix = comparator.computeLUVSimilarityMatrix();
		
		System.out.println("Size: " + matrix.length + " | " + matrix[0].length);
		
//		for( int i = 0; i < matrix.length; i++ )
//		{
//			for( int j = 0; j < matrix[i].length; j++ )
//			{
//				System.out.print(matrix[i][j]+ "\t");
//				// Math.round(matrix[i][j] * 100.0) / 100.0
//			}
//			System.out.println();
//		}
		
		int size = 50;
		for( int i = 0; i < size; i++ )
		{
			for( int j = 0; j < size; j++ )
			{
				System.out.printf("%.2f\t", matrix[i][j]);
				// Math.round(matrix[i][j] * 100.0) / 100.0
			}
			System.out.println();
		}
	}
}
