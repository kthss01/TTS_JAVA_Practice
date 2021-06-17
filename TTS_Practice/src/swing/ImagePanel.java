package swing;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	String imagePath = "";
	Dimension dimension;

	public ImagePanel(String imagePath, Dimension dimension) {
		this.imagePath = imagePath;
		this.dimension = dimension;

		setLayout(null);
		setSize(dimension);

//		System.out.println(dimension.width + " " + dimension.height);
	}

	public void paintComponent(Graphics g) {
		ImageIcon image = new ImageIcon(imagePath);
		g.drawImage(image.getImage(), 0, 0, dimension.width, dimension.height, null);

	}
}