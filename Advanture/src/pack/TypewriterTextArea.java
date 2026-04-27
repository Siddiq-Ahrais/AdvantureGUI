package pack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 * Custom JTextArea that displays text character-by-character
 * with a typewriter animation effect.
 */
public class TypewriterTextArea extends JTextArea {
	private Timer typeTimer;

	@Override
	public void setText(String text) {
		if(typeTimer!=null && typeTimer.isRunning()) {
			typeTimer.stop();
		}
		final String full = (text==null) ? "" : text;
		super.setText("");
		if(full.isEmpty()) {
			return;
		}
		final int[] i = new int[] {0};
		typeTimer = new Timer(9, null);
		typeTimer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				i[0]++;
				superSet(full.substring(0, Math.min(i[0], full.length())));
				if(i[0] >= full.length()) {
					typeTimer.stop();
				}
			}
		});
		typeTimer.start();
	}

	/**
	 * Set text immediately without typewriter animation.
	 */
	public void setImmediateText(String text) {
		if(typeTimer!=null && typeTimer.isRunning()) {
			typeTimer.stop();
		}
		super.setText(text==null ? "" : text);
	}

	private void superSet(String text) {
		super.setText(text);
	}
}
