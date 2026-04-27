package pack;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Icon;

/**
 * Procedurally drawn bag/backpack icon for the inventory button.
 */
public class BagIcon implements Icon {
	private final int size;
	private final Color color;

	public BagIcon(int size, Color color) {
		this.size = size;
		this.color = color;
	}

	@Override
	public int getIconWidth() {
		return size;
	}

	@Override
	public int getIconHeight() {
		return size;
	}

	@Override
	public void paintIcon(java.awt.Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(color);
		g2.setStroke(new BasicStroke(Math.max(2f, size / 16f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		int bodyX = x + (int)(size * 0.18);
		int bodyY = y + (int)(size * 0.28);
		int bodyW = (int)(size * 0.64);
		int bodyH = (int)(size * 0.62);

		g2.drawRoundRect(bodyX, bodyY, bodyW, bodyH, (int)(size * 0.16), (int)(size * 0.16));
		g2.drawArc(x + (int)(size * 0.33), y + (int)(size * 0.08), (int)(size * 0.34), (int)(size * 0.34), 0, 180);
		g2.drawLine(x + (int)(size * 0.34), y + (int)(size * 0.38), x + (int)(size * 0.34), y + (int)(size * 0.74));
		g2.drawLine(x + (int)(size * 0.66), y + (int)(size * 0.38), x + (int)(size * 0.66), y + (int)(size * 0.74));
		g2.drawLine(x + (int)(size * 0.24), y + (int)(size * 0.56), x + (int)(size * 0.76), y + (int)(size * 0.56));
		g2.dispose();
	}
}
