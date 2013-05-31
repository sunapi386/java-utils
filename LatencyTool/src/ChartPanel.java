import javax.swing.*;
import java.awt.*;

/*
 * A sample implementation of a panel that draws the chart. Extend this
 * class with drawing logic or use your own layouts and components.
 */
public class ChartPanel extends JPanel {

    public void paintComponent(Graphics gl) {
        Graphics2D g = (Graphics2D) gl;
        g.setColor(new Color(222, 222, 222));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(new Color(250, 0, 0));
        g.drawLine(10, 10, 10, 110);
        g.drawLine(10, 110, 260, 110);
    }
}

