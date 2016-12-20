
public class Snake_Thread extends Thread {
	boolean Control_Thread = true;
	boolean Control_Panel = false;
	private Snake_Panel snakepanel;

	public Snake_Thread(Snake_Panel snakepanel) {
		this.snakepanel = snakepanel;
	}

	public void run() {
		while (true) {
			if (Control_Panel) {
				snakepanel.init();
				Control_Panel = false;
				try {
					Thread.sleep(60);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
			if (Control_Thread) {
				if (snakepanel.again) {
					try {
						snakepanel.snake_move();
						
						snakepanel.repaint();
						Thread.sleep(150);
					} catch (InterruptedException ie) {
						ie.printStackTrace();
					}
				} 
			}
		}
	}
}