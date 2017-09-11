package de.saj.sandbox.console.element;

import de.saj.sandbox.console.container.ElementComposite;

public class Div extends ElementComposite {
    public Div(String internalId) {
        super(internalId);
    }

    public Div() {
        super("div");
    }

    @Override
    public String getTag() {
        return "div";
    }

    @Override
    public boolean needClosingTag() {
        return true;
    }
}
