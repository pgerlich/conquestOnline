package conquest.online.gameAssets.Structures;

import android.widget.ImageView;

public class GridItem {
	
	
	public ImageView image;
	public conquest.client.classes.AbstractStructure struct;
	public int x;
	public int y;
	
	public GridItem(ImageView image, conquest.client.classes.AbstractStructure struct){
		this.image = image;
		this.struct = struct;
	}

}
