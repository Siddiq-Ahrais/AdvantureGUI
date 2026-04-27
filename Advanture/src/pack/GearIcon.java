package pack;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Icon;

/**
 * Procedurally drawn gear/settings icon with 8 teeth.
 */
public class GearIcon implements Icon {
	private final int size;
	private final Color color;

	public GearIcon(int size, Color color) {
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
		int center = size / 2;
		int coreR = Math.max(3, size / 7);
		int ringR = Math.max(6, size / 3);
		int toothW = Math.max(2, size / 8);
		int toothH = Math.max(3, size / 7);

		g2.translate(x + center, y + center);
		for(int i=0;i<8;i++) {
			g2.rotate(Math.PI / 4.0);
			g2.drawRoundRect(-toothW / 2, -ringR - toothH, toothW, toothH, 2, 2);
		}
		g2.drawOval(-ringR, -ringR, ringR * 2, ringR * 2);
		g2.drawOval(-coreR, -coreR, coreR * 2, coreR * 2);
		g2.dispose();
	}
}
