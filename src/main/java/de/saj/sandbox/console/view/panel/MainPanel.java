package de.saj.sandbox.console.view.panel;

import de.saj.sandbox.console.view.action.DisposeWindowAction;
import de.saj.sandbox.console.view.action.SaveSvgAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainPanel extends JPanel {
    public static final int CANVAS_WIDTH = 640;
    public static final int CANVAS_HEIGHT = 480;
    public static final String TITLE = "...Title...";

    public MainPanel(Window window) {
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        setLayout(new BorderLayout());

        PaintPanel paintPanel = new PaintPanel();
        paintPanel.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        JTextArea outputSVG = new JTextArea();
        JScrollPane scrollPaneSVG = new JScrollPane(outputSVG);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
        centerPanel.add(paintPanel);
        centerPanel.add(scrollPaneSVG);

        JButton saveSvgButton = new JButton("Print as SVG");
        saveSvgButton.addActionListener(new SaveSvgAction(paintPanel, outputSVG));

        add(saveSvgButton, BorderLayout.PAGE_END);
        add(centerPanel, BorderLayout.CENTER);

        paintPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel");
        paintPanel.getActionMap().put("cancel", new DisposeWindowAction(window));
    }

//    @Override
//    public void paintComponent(Graphics g) {
//        new PaintPanel().paintComponent(g);
//    }
}
