import javax.swing.*;

/*
 * This skeleton app creates a window frame with a chart panel. Extend
 * this class and add more classes to complete the challenge.
 */
public class LatencyTool {
    private final JFrame frame;

    LatencyTool() {
        frame = new JFrame("LatencyTool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(300, 160);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
    }

    public void run() {
        frame.getContentPane().add(new JLabel("yahoo.com vs google.com latency"));
        frame.getContentPane().add(new ChartPanel());

        frame.setVisible(true);
    }

    public static void main(String args[]) {
        (new LatencyTool()).run();
    }
}

