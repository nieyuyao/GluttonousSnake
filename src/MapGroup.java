import java.util.LinkedList;


public class MapGroup {
	private Map[] map_group=new Map[2];
	LinkedList<Node> map_node01=new LinkedList<Node>();
	public MapGroup(){
		init_map_node();
		String background_str01="map\\background01.png";
		String balk_str01="map\\balk01.png";
		Map map01=new Map(map_node01,background_str01,balk_str01);
		map_group[0]=map01;
	}
	public void init_map_node(){
		for(int i=30;i<40;i++){
			Node node=new Node(i,10);
			map_node01.add(node);
		}
		for(int j=35;j<40;j++){
			Node node=new Node(15,j);
			map_node01.add(node);
		}
	}
	public Map[] getMapGroup(){
		return map_group;
	}

}
