package graphics;

import java.awt.Graphics;
import javax.swing.*;

public interface IDrawable {
	
	public final static String PICTURE_PATH = "C:\\Users\\Administrator\\Desktop\\studys\\JAVA\\thezoo\\photoszoo\\";
	public void loadImages(String nm);
	public void drawObject (Graphics g);
	public String getColor();

}
