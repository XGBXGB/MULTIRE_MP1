package model;

public class CCVCompute {
	
	public static void main(String[] args)
	{
		int[][] image = {
					    {1,1,1,1,1,1,1,2,1,1,1,2},
					    {2,1,0,0,0,0,1,1,1,2,2,1},
					    {0,1,0,2,0,2,1,1,1,2,2,1},
					    {2,1,0,0,0,0,0,1,1,2,2,1},
					    {1,2,0,0,2,0,0,0,1,0,0,2},
					    {1,1,1,1,2,2,2,2,2,0,0,0},
					    {0,0,0,0,1,1,1,2,1,0,0,1},
					    {1,1,0,2,2,2,1,2,2,0,0,0}
					    };
		
		getCCV4(image, 3, 12, 8, 5);
	}
	
	public static double[] getComparedCCV4(int[][] image1, int[][] image2, int width, int height, int nColors, int threshold)
	{
		double[][] c1 = getCCV4(image1, nColors, width, height, threshold);
		double[][] c2 = getCCV4(image2, nColors, width, height, threshold);
		double[] compared = new double[nColors];
		
		for(int x = 0; x < nColors; x++)
		{
			double co = c1[x][0] - c2[x][0];
			if(co < 0) co *= -1;
			double nco = c1[x][1] - c2[x][1];
			if(nco < 0) nco *= -1;
			compared[x] = co + nco;
			
		}
		
		return compared;
	}
	
	public static double[][] getCCV4(int[][] image,int nColors, int width, int height, int threshold)
	{
		// results because nColors then coherent and not coherent
		double[][] coherence = new double[nColors][2]; 
		// picture
		int[][] picture = new int[height][width];
		//groups of the picture
		int[][] groups = new int[height][width];
		// for children group #s
		Child child = new Child(width, height);
		
		//group number init
		int gNum = 1;
		
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				picture[y][x] = image[y][x]; //index of dapat 
				groups[y][x] = 0;
				
			}
		}
		
		//first pass
		
		//loop for all the colors na meron
		for(int currColor = 0; currColor < nColors; currColor++)
		{
			// loop for all the pixels in the image
			for(int x = 0; x < height; x++)
			{
				for(int y = 0; y < width; y++)
				{
					// kung yung color nya is yung current na color na cinocompare
					if(currColor == picture[x][y])
					{
						//kung first pixel sya
						if(x == 0 && y == 0)
						{
							//iseset mo lang yung group number sa unang group number.
							groups[x][y] = gNum;
							gNum++;
						}
						//kung top pixel sya
						else if(x == 0)
						{
							//kung yung prev color sa left is same sa color nya
							if(picture[x][y] == picture[x][y-1])
							{
								//kunin mo lang yung group nya
								groups[x][y] = groups[x][y-1];
							}
							//kung hindi, magsstart sya bali ng bagong group
							else
							{
								groups[x][y] = gNum;
								gNum++;
							}
						}
						//left pixel lang sya
						else if(y == 0)
						{
							//kung yung prev color sa left is same sa color nya
							if(picture[x][y] == picture[x-1][y])
							{
								//kunin mo lang yung group nya
								groups[x][y] = groups[x-1][y];
							}
							//kung hindi, magsstart sya bali ng bagong group
							else
							{
								groups[x][y] = gNum;
								gNum++;
							}
						}
						//kung mid pixel lang sya
						else
						{
							//kung same color yung left and top nya
							if(picture[x][y] == picture[x-1][y] && picture[x][y] == picture[x][y-1])
							{
								//kunin lang yung lesser gNum tapos add sa child yung isa
								//kung si left mas maliit
								if(groups[x][y-1] < groups[x-1][y])
								{
									//assign left group to it
									groups[x][y] = groups[x][y-1];
									// add top to the child ng left
									child.addChild(groups[x][y], groups[x-1][y]);
								}
								else if(groups[x][y-1] == groups[x-1][y])
								{
									//assign top to it
									groups[x][y] = groups[x-1][y];
									// wala nang add kasi equal
								}
								else
								{
									//assign top to it
									groups[x][y] = groups[x-1][y];
									// add left as it's child
									child.addChild(groups[x][y], groups[x][y-1]);
								}
								
							}
							//kung same color yung left nya
							else if(picture[x][y] == picture[x][y-1])
							{
								groups[x][y] = groups[x][y-1];
							}
							//kung same color yung top nya
							else if(picture[x][y] == picture[x-1][y])
							{
								groups[x][y] = groups[x-1][y];
							}
							//kung walang same sa top and left
							else
							{
								groups[x][y] = gNum;
								gNum++;
							}
						}
					}
					
				}
			}
			
		}
		
		//-----------------------------------SECOND PASS-------------------------//
		// include counting per group here
		
		// 2 cols for color and count
		int[][] cntPerGrp = new int[gNum][2];
		
		//initialize
		for(int x = 1; x < gNum; x++)
		{
			cntPerGrp[x][0] = 0;
			cntPerGrp[x][1] = 0;
		}
		
		//loop for all the colors na meron
		for(int currColor = 0; currColor < nColors; currColor++)
		{
			// loop for all the pixels in the image
			for(int x = 0; x < height; x++)
			{
				for(int y = 0; y < width; y++)
				{
					//change to the parent #
					groups[x][y] = child.getParent(groups[x][y]);
				}
			}
		}
		
		// loop for all the pixels in the image
		for(int x = 0; x < height; x++)
		{
			for(int y = 0; y < width; y++)
			{
				//count per group
				cntPerGrp[groups[x][y]][0] = image[x][y]; //color ng group
				cntPerGrp[groups[x][y]][1] = cntPerGrp[groups[x][y]][1]+1; // add to ctr
			}
		}
		
		//------------------------------PRINTVAL---------------------//
		
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				System.out.printf("%3d ", groups[y][x]);
			}
			System.out.println("");
		}
		
		System.out.println("\n");
		
		for(int x = 1; x < gNum; x++)
		{
			System.out.println(x + " " + cntPerGrp[x][0] + " " + cntPerGrp[x][1]);
		}
		
		
		//-------------------------COLOR COHERENCE CTR-----------------------//
		
		for(int x = 0; x < nColors; x++)
		{
			coherence[x][0] = 0; //coherent
			coherence[x][1] = 0; //noncoherent
			
		}
		
		for(int x = 1; x < gNum; x++)
		{
			if(cntPerGrp[x][1] >= threshold)
				coherence[cntPerGrp[x][0]][0] += cntPerGrp[x][1]; // coherent
			else coherence[cntPerGrp[x][0]][1] += cntPerGrp[x][1]; // noncoherent
		}
		
		
		for(int x = 0; x < nColors; x++)
		{
			System.out.println(x + " (" + coherence[x][0] + ","+ coherence[x][1] + ")");
		}
		
		
		return coherence;
	}
	
	
}
