package de.saj.sandbox.console.controller;

import de.saj.sandbox.console.model.Model;
import de.saj.sandbox.console.view.View;

public class Controller {
    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
    }
}
