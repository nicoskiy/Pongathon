import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

// By Nicholas Yarnall
/*
 Hello Judges... I'd like to say thank you so much
 for hosting this program. I have never collaborated with other
 students to program in my life! In fact I've only
 programmed for about 6 months. I think I got pretty far...
 But there is still a lot to learn about.
 Bu - Byeee!!!

 */
public class Main extends Canvas implements Runnable {
	public int width = 300, height = width / 16 * 9, scale = 3;
	public boolean running = false, pause = false;

	int x = 5, y = height / 2 - 15, x2 = width - 10, y2 = height / 2 - 15,
			xb = width / 2, yb = height / 2, Player1 = 0, Player2 = 0;
	public int tick = 0, frames = 0;

	int speed = 1, ySpeed = (int) (Math.random() * 5 - 3), bounce = 0;;

	public PixelSprite you;
	public PixelSprite me;
	public PixelSprite ball;
	public Keyboard key;
	public Screen screen;
	public Graphics g;
	public JFrame frame;
	public Thread thread;
	public BufferedImage image = new BufferedImage(width, height,
			BufferedImage.TYPE_INT_RGB);
	public int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer())
			.getData();

	public static void main(String[] arg) {
		Main game = new Main();
		System.out
				.println("\t\tWelcome to PONG!! \nOk so this is a game I made without any help....\nPlease don't sue me for using the idea of Pong D:...");
		double time = System.currentTimeMillis();
		while (!(System.currentTimeMillis() - time > 10000))
			;
		System.out
				.println("\n\n\n\n\tInstruction : \n\t_______________________________________________________\n\t-In the Title Bar there is an information bar which contains ... \n\tTitle - Tick - FPS - Bounces on Paddle - Score Up to 5 Points\n\t-This is a 2 player game so either get \n\tsomeother judge to use the arrow keys\n\tand the other to use the classic wasd keys... enjoy!\n\tGame will start shortly . . . .\n\tP.S Game may crash so please relaunch");
		time = System.currentTimeMillis();
		while (!(System.currentTimeMillis() - time > 30000)) ;
			
			
		

		game.start();
	}

	public void run() {
		long timer = System.currentTimeMillis();
		long before = System.nanoTime();
		double NANO = 1000000000.0 / 60.0;
		double delta = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - before) / NANO;
			before = now;
			while (delta >= 1) {

				delta--;
				tick();
				tick++;
			}

			render();
			frames++;
			if (System.currentTimeMillis() - timer >= 1000) {
				frame.setTitle("PONG v1.0 || Tick : " + tick + " Frames : "
						+ frames + " Bounce(s) :" + bounce + " Player 1 ->"
						+ Player1 + "-" + Player2 + "<- Player 2");
				frames = 0;
				tick = frames;
				timer += 1000;

			}

		}
	}

	public void tick() {

		// Player 1

		if (key.key[87] && !(y <= 0)) {
			y -= 1;
		}
		if (key.key[83] && !(y >= height - 30)) {
			y += 1;
		}
		if (key.key[38] && !(y2 <= 0)) {
			y2 -= 1;
		}
		if (key.key[40] && !(y2 >= height - 30)) {
			y2 += 1;
		}

		if (yb > y && yb < y + 30 && xb == x + 5) {
			speed = -speed;
			while ((ySpeed == 0))
				ySpeed = (int) (Math.random() * 3 - 2);
			bounce++;
		}

		if (yb > y2 && yb < y2 + 30 && xb + 3 == x2) {
			speed = -speed;
			while (ySpeed == 0)
				ySpeed = -ySpeed + (int) (Math.random() * 3 - 2);
			bounce++;
		}

		if (yb <= 0 + ySpeed || yb >= height - 3 + ySpeed)
			ySpeed = -ySpeed;
		xb += speed;
		yb += -ySpeed;

		if (xb >= width || xb <= 0) {
			if (Player1 > 3) {
				System.out.println("Player 1 WINS!!!!");
				System.exit(0);
			}
			if (Player2 > 3) {
				System.out.println("Player 2 WINS!!!!!");
				System.exit(0);
			}
			if (xb >= width)
				Player1++;
			if (xb <= 0) {
				Player2++;
			}
			xb = width / 2;
			yb = height / 2;
		}

		me.setLocation(x, y);
		you.setLocation(x2, y2);
		ball.setLocation(xb, yb);

	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		screen.clear();

		this.renderSprites();

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixel[i];
		}
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, frame.getWidth(), frame.getHeight(), null);
		g.dispose();
		bs.show();
	}

	public void start() {
		me = new PixelSprite(5, 30);
		you = new PixelSprite(5, 30);
		ball = new PixelSprite(3, 3);
		this.me();
		thread = new Thread(this);
		frame = new JFrame();
		screen = new Screen(width, height);
		key = new Keyboard();
		this.addKeyListener(key);
		frame.setDefaultCloseOperation(3);
		frame.setSize(width * scale, height * scale);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.requestFocusInWindow();
		frame.add(this);
		running = true;
		thread.start();
	}

	public void me() {
		for (int i = 0; i < me.sprite.length; i++) {
			me.sprite[i] = 0xDBDBDB;
		}
		for (int i = 0; i < you.sprite.length; i++) {
			you.sprite[i] = 0xDBDBDB;
		}
		for (int i = 0; i < ball.sprite.length; i++) {
			ball.sprite[i] = 0xFFFFFF;
		}

	}

	public void renderSprites() {
		this.screen.addSprite(me);
		this.screen.addSprite(you);
		this.screen.addSprite(ball);
	}

}