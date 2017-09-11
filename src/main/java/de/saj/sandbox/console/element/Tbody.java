package de.saj.sandbox.console.element;

import de.saj.sandbox.console.container.ElementComposite;

public class Tbody extends ElementComposite {
    public Tbody(String internalId) {
        super(internalId);
    }

    public Tbody() {
        super("tbody");
    }

    @Override
    public String getTag() {
        return "tbody";
    }

    @Override
    public boolean needClosingTag() {
        return true;
    }
}
