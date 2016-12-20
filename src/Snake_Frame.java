import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Snake_Frame extends JFrame {
	/**
	 * 
	 */
	private String[] flag = { "开始", "暂停" };
	private int index = 0;
	private static final long serialVersionUID = 1L;
	public Snake_Panel snake_panel;
	private Snake_Thread snakethread;
	private boolean Control_Frame = true;

	JMenu game = new JMenu("游戏");
	JMenu about = new JMenu("关于");
	JMenuItem author=new JMenuItem("作者");
	JMenuItem control = new JMenuItem(flag[0]);
	JMenuItem newGame = new JMenuItem("新游戏");
	JMenuItem exitGame = new JMenuItem("退出");

	public Snake_Frame(int Frame_Width, int Frame_Height) {
		super("贪吃蛇");
		setSize(Frame_Width, Frame_Height);
		setLayout(null);
		snake_panel = new Snake_Panel(600, 600);
		snakethread = new Snake_Thread(snake_panel);
		add(snake_panel);
		initMenu();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}

	// 初始化菜单
	public void initMenu() {
		JMenuBar menubar = new JMenuBar();
		newGame.setText("新游戏");
		// 退出游戏
		exitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		});
		control.addActionListener(new controlActionListener());
		newGame.addActionListener(new newgameActionListener());
		game.add(newGame);
		game.add(control);
		game.add(exitGame);
		about.add(author);
		menubar.add(game);
		menubar.add(about);
		this.setJMenuBar(menubar);
	}

	// 控制游戏的开始和暂停
	class controlActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if (index == 0) {
				if (Control_Frame) {
					snakethread.start();
					Control_Frame = false;
				} else {
					snakethread.Control_Thread = true;
				}

			} else if (index == 1) {
				snakethread.Control_Thread = false;
			}
			index = (index + 1) % 2;
			control.setText(flag[index]);
		}

	}

	// 新游戏
	class newgameActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			// 开始新的游戏
			//停止线程
			snakethread.Control_Thread=false;
			//绘图类初始化
			snakethread.Control_Panel=true;
			index=0;
			control.setText(flag[0]);
		}
	}
}
