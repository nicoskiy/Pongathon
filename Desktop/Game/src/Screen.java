public class Screen {

	int width, height;
	int[] pixel;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixel = new int[width * height];
	}

	public void test(int xOff, int yOff) {

		for (int x = 0; x < width; x++) {
			int xx = xOff + x;
			for (int y = 0; y < height; y++) {
				int yy = yOff + y;

				pixel[x + y * width] = (x & xx) + (y & yy);
			}
		}
	}

	public void line(int x, int y, int x2, int y2) {
		double slope = (y - y2) / (x - x2);
		double b = y / (slope * x);
		for (long xx = 0; xx < width; xx++) {
			long yy = (int) (slope * xx + b);
			//System.out.println(yy);
			//System.out.println(xx * slope + b);

			if (yy > 0 && yy < height) {
				pixel[(int) (xx + yy * width)] = 0xFFFFFFF;
			}
		}
	}

	public void text() {

	}

	public void draw() {
		for (int i = 0; i < pixel.length; i++) {
			pixel[i] = (int) (Math.random() * 0xFFFFFF + 1);
		}
	}
	public void clear() {
		for(int i = 0 ; i < pixel.length ; i++) {
			pixel[i] = 0x0;
		}
	}
	
	public void addSprite(PixelSprite sprite) {
		int xx = 0,yy = 0;
		for (int x = sprite.getX(); x < sprite.getX() + sprite.getWidth(); xx++, x++) {
			for (int y = sprite.getY(); y < sprite.getY() + sprite.getHeight(); yy++,y++) {
				if(!(sprite.sprite[(x - sprite.getX() ) + (y -sprite.getY() ) * sprite.getWidth()] == 0xFF00FF))
				pixel[x + y * width] = sprite.sprite[(x - sprite.getX() ) + (y -sprite.getY() ) * sprite.getWidth()];
			}
		}
	}
}
