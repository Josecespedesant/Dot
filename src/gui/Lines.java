package gui;

import javax.swing.*;
import java.awt.*;

public final class Lines extends JPanel{
	public int xi;
	public int xf;
	public int yi;
	public int yf;
	
	public Lines(int xi, int xf, int yi, int yf) {
		xi = this.xi;
		xf = this.xf;
		yi = this.yi;
		yf = this.yf;
	}
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.drawLine(xi, yi, xf, yf);
	}

}
