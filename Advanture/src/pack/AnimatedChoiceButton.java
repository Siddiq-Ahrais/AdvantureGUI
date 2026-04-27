package pack;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import javax.swing.JButton;
import javax.swing.Timer;

/**
 * Custom JButton with rounded rectangle rendering, hover scale animation,
 * popup entrance animation, and configurable colors.
 */
public class AnimatedChoiceButton extends JButton {
	private float scale = 1.0f;
	private Timer animTimer;
	private Color fillColor = new Color(18, 18, 18);
	private Color hoverFillColor = new Color(36, 36, 36);
	private Color borderColor = new Color(220, 220, 220);

	public AnimatedChoiceButton() {
		setOpaque(false);
		setFocusPainted(false);
		setBorderPainted(false);
		setContentAreaFilled(false);
		setForeground(Color.white);
		setBackground(Color.black);
		setRolloverEnabled(true);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				animateTo(1.04f, 90);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				animateTo(1.0f, 100);
			}
		});
	}

	public void playPopup() {
		scale = 0.86f;
		repaint();
		animateTo(1.0f, 120);
	}

	public void setButtonColors(Color fill, Color text, Color border) {
		fillColor = fill;
		hoverFillColor = fill.brighter();
		borderColor = border;
		setForeground(text);
		repaint();
	}

	private void animateTo(float target, int durationMs) {
		if(animTimer!=null && animTimer.isRunning()) {
			animTimer.stop();
		}
		final int frames = 10;
		final float start = scale;
		final float delta = target - start;
		animTimer = new Timer(Math.max(10, durationMs / frames), null);
		animTimer.addActionListener(new ActionListener() {
			int step = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				step++;
				float t = step / (float) frames;
				// easeOut: fast start, smooth deceleration
				float eased = 1.0f - (1.0f - t) * (1.0f - t);
				scale = start + (delta * eased);
				repaint();
				if(step>=frames) {
					scale = target;
					animTimer.stop();
				}
			}
		});
		animTimer.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int w = getWidth();
		int h = getHeight();

		// Reserve margin so the scaled shape never exceeds component bounds.
		// At maxScale the drawn rect fills to the edge; at lower scales it shrinks inward.
		float maxScale = 1.04f;
		int mx = (int) Math.ceil(w * (1.0f - 1.0f / maxScale) / 2.0f) + 1;
		int my = (int) Math.ceil(h * (1.0f - 1.0f / maxScale) / 2.0f) + 1;
		int bw = w - mx * 2;
		int bh = h - my * 2;

		AffineTransform old = g2.getTransform();
		g2.translate(w / 2.0, h / 2.0);
		g2.scale(scale, scale);
		g2.translate(-w / 2.0, -h / 2.0);

		Color base = getModel().isRollover() ? hoverFillColor : fillColor;
		g2.setColor(base);
		g2.fillRoundRect(mx, my, bw, bh, Math.max(4, bh), Math.max(4, bh));
		g2.setColor(borderColor);
		g2.setStroke(new BasicStroke(2f));
		g2.drawRoundRect(mx, my, bw, bh, Math.max(4, bh), Math.max(4, bh));

		super.paintComponent(g2);
		g2.setTransform(old);
		g2.dispose();
	}
}
