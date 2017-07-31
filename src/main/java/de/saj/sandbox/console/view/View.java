package de.saj.sandbox.console.view;

import de.saj.sandbox.console.view.panel.MainPanel;
import de.saj.sandbox.console.model.Model;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private Model model;

    public View(Model model) throws HeadlessException {
        super(MainPanel.TITLE);
        this.model = model;

        setContentPane(new MainPanel(this));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
