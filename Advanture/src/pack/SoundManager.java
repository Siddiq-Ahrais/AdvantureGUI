package pack;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

/**
 * Synthesizes and plays simple sound effects using Java's audio API.
 * No external .wav files needed — all sounds are generated programmatically.
 */
public class SoundManager {

	private static final float SAMPLE_RATE = 22050f;

	/**
	 * Play a short sword slash sound (white noise burst with fast decay).
	 */
	public static void playSlash() {
		playAsync(() -> {
			byte[] buf = new byte[(int)(SAMPLE_RATE * 0.15f)];
			java.util.Random rnd = new java.util.Random();
			for(int i = 0; i < buf.length; i++) {
				float decay = 1.0f - (float)i / buf.length;
				float highpass = (rnd.nextFloat() * 2 - 1) * decay * decay;
				buf[i] = (byte)(highpass * 80);
			}
			playBuffer(buf);
		});
	}

	/**
	 * Play a gulp/drink sound (low frequency sweep).
	 */
	public static void playGulp() {
		playAsync(() -> {
			byte[] buf = new byte[(int)(SAMPLE_RATE * 0.25f)];
			for(int i = 0; i < buf.length; i++) {
				float t = (float)i / SAMPLE_RATE;
				float decay = 1.0f - (float)i / buf.length;
				float freq = 180 + 120 * (float)Math.sin(t * 12);
				float val = (float)Math.sin(2 * Math.PI * freq * t) * decay;
				buf[i] = (byte)(val * 60);
			}
			playBuffer(buf);
		});
	}

	/**
	 * Play a short UI click sound.
	 */
	public static void playClick() {
		playAsync(() -> {
			byte[] buf = new byte[(int)(SAMPLE_RATE * 0.04f)];
			for(int i = 0; i < buf.length; i++) {
				float t = (float)i / SAMPLE_RATE;
				float decay = 1.0f - (float)i / buf.length;
				float val = (float)Math.sin(2 * Math.PI * 800 * t) * decay * decay;
				buf[i] = (byte)(val * 50);
			}
			playBuffer(buf);
		});
	}

	/**
	 * Play a damage/hit sound (buzz).
	 */
	public static void playHit() {
		playAsync(() -> {
			byte[] buf = new byte[(int)(SAMPLE_RATE * 0.12f)];
			java.util.Random rnd = new java.util.Random();
			for(int i = 0; i < buf.length; i++) {
				float t = (float)i / SAMPLE_RATE;
				float decay = 1.0f - (float)i / buf.length;
				float tone = (float)Math.sin(2 * Math.PI * 120 * t);
				float noise = (rnd.nextFloat() * 2 - 1) * 0.3f;
				buf[i] = (byte)((tone + noise) * decay * 55);
			}
			playBuffer(buf);
		});
	}

	/**
	 * Play a victory fanfare (ascending tones).
	 */
	public static void playVictory() {
		playAsync(() -> {
			float dur = 0.4f;
			byte[] buf = new byte[(int)(SAMPLE_RATE * dur)];
			float[] notes = {523.25f, 659.25f, 783.99f, 1046.50f};
			int segLen = buf.length / notes.length;
			for(int n = 0; n < notes.length; n++) {
				for(int i = 0; i < segLen; i++) {
					int idx = n * segLen + i;
					if(idx >= buf.length) break;
					float t = (float)i / SAMPLE_RATE;
					float decay = 1.0f - (float)i / segLen;
					float val = (float)Math.sin(2 * Math.PI * notes[n] * t) * decay;
					buf[idx] = (byte)(val * 50);
				}
			}
			playBuffer(buf);
		});
	}

	private static void playBuffer(byte[] buf) {
		try {
			AudioFormat fmt = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
			Clip clip = AudioSystem.getClip();
			clip.open(fmt, buf, 0, buf.length);
			clip.start();
			clip.addLineListener(event -> {
				if(event.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
					clip.close();
				}
			});
		} catch(LineUnavailableException e) {
			// Silently ignore — no sound hardware
		}
	}

	private static void playAsync(Runnable task) {
		new Thread(task, "SoundThread").start();
	}
}
