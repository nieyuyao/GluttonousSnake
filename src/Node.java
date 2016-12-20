
class Node {
	private int x,y;
	public Node(int x,int y){
		this.x=x;
		this.y=y;
	}
	public void setPosition(int newx,int newy){
		this.x=newx;
		this.y=newy;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}

}