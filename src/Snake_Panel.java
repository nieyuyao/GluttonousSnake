import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Snake_Panel extends JPanel implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int panel_Width;
	private int panel_Height;
	private static int Node_Width = 15;
	private boolean[][] snake_body;
	private LinkedList<Node> snake_node = new LinkedList<Node>();
	private Node start;
	private int food_x, food_y; // food的坐标
	private int head_x, head_y; // head的坐标
	private int weiba_x, weiba_y;// 尾巴的坐标
	boolean again;
	private int direction;
	private int weiba_direction;
	private static int up = 1, down = 3, left = 2, right = 4;
	static boolean isEaten = false;
	private Image[] snake_head_img = new Image[5];
	private Image[] snake_weiba_img = new Image[5];
	private Image food_img = new ImageIcon("image\\dag.png").getImage();
	private Image snake_body_img = new ImageIcon("image\\snake_body.png")
			.getImage();
	private MapGroup mapGroup = new MapGroup();
	private Map[] map_group = mapGroup.getMapGroup();
	private LinkedList<Node> map_node = map_group[0].getMapNode();

	public Snake_Panel(int panel_Width, int panel_Height) {
		this.panel_Height = panel_Height;
		this.panel_Width = panel_Width;
		this.setSize(panel_Width, panel_Height);
		setBackground(Color.WHITE);
		setFocusable(true);
		addKeyListener(this);
		// 初始化snake_body[][]
		snake_body = new boolean[panel_Height / Node_Width][panel_Width
				/ Node_Width];
		init();
	}

	// 初始化图片数组
	public void init_pic() {
		snake_head_img[1] = new ImageIcon("image\\snake_head_up.png")
				.getImage();
		snake_head_img[2] = new ImageIcon("image\\snake_head_left.png")
				.getImage();
		snake_head_img[3] = new ImageIcon("image\\snake_head_down.png")
				.getImage();
		snake_head_img[4] = new ImageIcon("image\\snake_head_right.png")
				.getImage();
		snake_weiba_img[1] = new ImageIcon("image\\snake_weiba_up.png")
				.getImage();
		snake_weiba_img[2] = new ImageIcon("image\\snake_weiba_left.png")
				.getImage();
		snake_weiba_img[3] = new ImageIcon("image\\snake_weiba_down.png")
				.getImage();
		snake_weiba_img[4] = new ImageIcon("image\\snake_weiba_right.png")
				.getImage();
	}

	// 游戏初始化
	public void init() {
		clear();
		direction = 2;
		weiba_direction = 2;
		for (int j = 10; j < 16; j++) {
			snake_body[10][j] = true;
		}
		snake_node.clear();
		start = new Node(10, 10);
		snake_node.add(start);
		for (int j = 11; j < 16; j++) {
			Node node = new Node(10, j);
			snake_node.add(node);
		}
		init_pic();
		again = true;
		isEaten = false;
		head_x = 10;
		head_y = 10;
		weiba_x = 10;
		weiba_y = 15;
		creatfood();
		repaint();
	}

	// 控制蛇方向
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			changeDirection(up);
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			changeDirection(down);
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			changeDirection(left);
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			changeDirection(right);
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void changeDirection(int newDirection) {
		if (direction % 2 != newDirection % 2) {
			direction = newDirection;
		}
	}

	// 生成新的蛇的身体
	public void snake_move() {
		if (direction == 1) {
			head_x--;
		} else if (direction == 3) {
			head_x++;
		} else if (direction == 2) {
			head_y--;
		} else if (direction == 4) {
			head_y++;
		}
		// 判断游戏是否结束
		is_over();
		if (again) {
			eat();
			if (isEaten == false) {
				for (int i = snake_node.size() - 1; i > 0; i--) {
					Node node = (Node) (snake_node.get(i - 1));
					snake_node.get(i).setPosition(node.getX(), node.getY());
				}
				start.setPosition(head_x, head_y);
				clear();
				for (int i = 0; i < snake_node.size(); i++) {
					snake_body[snake_node.get(i).getX()][snake_node.get(i)
							.getY()] = true;
				}

			} else {
				Node newstart = new Node(food_x, food_y);
				snake_node.add(0, newstart);
				start = newstart;
				clear();
				for (int i = 0; i < snake_node.size(); i++) {
					snake_body[snake_node.get(i).getX()][snake_node.get(i)
							.getY()] = true;
				}
				creatfood();
			}
			panduan_weiba();

		} else {
			JOptionPane.showMessageDialog(null, "游戏结束");
		}
	}

	// 判断尾巴的显示
	public void panduan_weiba() {
		if (weiba_y > snake_node.getLast().getY()) {
			weiba_direction = 2;
		} else if (weiba_y < snake_node.getLast().getY()) {
			weiba_direction = 4;
		} else if (weiba_x < snake_node.getLast().getX()) {
			weiba_direction = 3;
		} else if (weiba_x > snake_node.getLast().getX()) {
			weiba_direction = 1;
		}
		weiba_x = snake_node.getLast().getX();
		weiba_y = snake_node.getLast().getY();
	}

	// 判断是否吃到食物
	public boolean eat() {
		isEaten = false;
		if ((head_x == food_x) && (head_y == food_y)) {
			isEaten = true;
		}
		return isEaten;
	}

	// 将boolean数组全部初始化
	public void clear() {
		for (int i = 0; i < panel_Height / Node_Width; i++) {
			for (int j = 0; j < panel_Width / Node_Width; j++) {
				snake_body[i][j] = false;
			}
		}
		for (int i = 0; i < map_node.size(); i++) {
			Node node = (Node) (map_node.get(i));
			snake_body[node.getX()][node.getY()] = true;
		}
	}

	// 重绘图像
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 绘制地图;
		g.drawImage(map_group[0].getBackgroundImage(), 0, 0, this);

		for (int i = 0; i < panel_Height / Node_Width; i++) {
			for (int j = 0; j < panel_Width / Node_Width; j++) {
				if (snake_body[i][j] == true) {
					if ((i == head_x) && (j == head_y)) {
						g.drawImage(snake_head_img[direction], Node_Width * j,
								Node_Width * i, this);
					} else if ((i == weiba_x) && (j == weiba_y)) {
						g.drawImage(snake_weiba_img[weiba_direction],
								Node_Width * j, Node_Width * i, this);
					} else {
						g.drawImage(snake_body_img, Node_Width * j, Node_Width
								* i, this);
					}
				}
			}
		}
		for (int i = 0; i < map_node.size(); i++) {
			Node node = (Node) (map_node.get(i));
			g.drawImage(map_group[0].getBalkImage(), Node_Width * node.getY(),
					Node_Width * node.getX(), this);
		}
		// 画出食物
		g.drawImage(food_img, Node_Width * food_y, Node_Width * food_x, this);
	}

	// 判断游戏是否结束
	public boolean is_over() {
		if (head_x < 0 || head_x >= (panel_Height / Node_Width) || head_y < 0
				|| head_y >= (panel_Width / Node_Width)) {
			again = false;
		} else {
			if (snake_body[head_x][head_y] == true) {
				again = false;
			}
		}
		return again;
	}

	// 生成新的食物
	public void creatfood() {
		do {
			food_x = (int) (new Random().nextInt(panel_Height / Node_Width - 1));
			food_y = (int) (new Random().nextInt(panel_Width / Node_Width - 1));
		} while (snake_body[food_x][food_y] == true);
	}
}
