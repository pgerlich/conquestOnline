package conquest.online.gameAssets.Structures;

import android.widget.ImageView;

public class GridItem {
	
	
	public ImageView image;
	public conquest.client.classes.AbstractStructure struct;
	
	public GridItem(ImageView image, conquest.client.classes.AbstractStructure struct){
		this.image = image;
		this.struct = struct;
	}

}
