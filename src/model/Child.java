package model;

public class Child {
	
	int[] grpChildren; 
	
	public Child(int width, int height)
	{
		grpChildren = new int[width*height+1];
	}

	void addChild(int parent, int child)
	{
		if(grpChildren[child] == 0)
		{
			grpChildren[child] = parent;
		}
	}
	
	int getParent(int child)
	{
		if(grpChildren[child] == 0)
			return child;
		else return getParent(grpChildren[child]);
	}
	
}
