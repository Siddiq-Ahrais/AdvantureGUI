//package pack;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ComponentAdapter;
//import java.awt.event.ComponentEvent;
//
//public class Respon {
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Responsive Text Size");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(600, 400);
//
//        JLabel label = new JLabel("Resizable Text", SwingConstants.CENTER);
//        label.setFont(new Font("Arial", Font.PLAIN, 20)); // Initial font size
//        frame.add(label);
//
//        // Resize listener to adjust font size based on window size
//        frame.addComponentListener(new ComponentAdapter() {
//            public void componentResized(ComponentEvent e) {
//                int width = frame.getWidth();
//                int height = frame.getHeight();
//
//                // Calculate font size based on window size (tweak as needed)
//                int fontSize = Math.min(width, height) / 10;
//                label.setFont(new Font("Arial", Font.PLAIN, fontSize));
//            }
//        });
//
//        frame.setVisible(true);
//    }
//}
