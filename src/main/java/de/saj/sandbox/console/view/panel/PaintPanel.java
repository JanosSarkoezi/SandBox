package de.saj.sandbox.console.view.panel;

import javax.swing.*;
import java.awt.*;

public class PaintPanel extends JPanel {
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);

        g.setColor(Color.GRAY);
        g.drawRect(10, 10, 100, 50);
        g.drawRect(10, 100, 100, 50);
        g.drawOval(55, 75, 10, 10);
        g.drawLine(60, 60, 60, 75);
        g.drawLine(60, 85, 60, 100);
        g.drawString("A", 55, 35);
        g.drawString("B", 55, 125);
    }
}
