import java.awt.Image;
import java.util.LinkedList;

import javax.swing.ImageIcon;


public class Map {
	private Image background_img;
	private LinkedList<Node> map_node;
	private Image balk_img;
	public Map(LinkedList<Node> map_node,String background_str,String balk_str){
		this.map_node=map_node;
		background_img=new ImageIcon(background_str).getImage();
		balk_img=new ImageIcon(balk_str).getImage()	;
	}
	public Image getBackgroundImage(){
		return background_img;
	}
	public Image getBalkImage(){
		return balk_img;
	}
	public LinkedList<Node> getMapNode(){
		return map_node;
	}

}
