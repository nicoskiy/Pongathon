
public class PixelSprite extends Entity{
	int[] sprite;
	
	
	public PixelSprite() {
		super();
	}
	public PixelSprite(int width, int height) {
		this.setWidth(width);
		this.setHeight(height);
		sprite = new int[width * height];
	}
	public void setLocation(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	public boolean setPixelSprite(int[] arg) {
		if(!(sprite.length == arg.length))
			return false;
		for(int i = 0 ; i < sprite.length ; i++) {
			sprite[i] = arg[i];
		}
		return true;
	}
	
	
}
