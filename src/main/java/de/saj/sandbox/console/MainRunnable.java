package de.saj.sandbox.console;

import de.saj.sandbox.console.controller.Controller;
import de.saj.sandbox.console.model.Model;
import de.saj.sandbox.console.view.View;

public class MainRunnable implements Runnable {
    @Override
    public void run() {
        Model model = new Model();
        View view  = new View(model);
        Controller controller = new Controller(view, model);
    }
}
