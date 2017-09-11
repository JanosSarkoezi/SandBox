package de.saj.sandbox.console.element;

import de.saj.sandbox.console.container.ElementComposite;

public class Head extends ElementComposite {
    public Head(String internalId) {
        super(internalId);
    }

    public Head() {
        super("head");
    }

    @Override
    public String getTag() {
        return "head";
    }

    @Override
    public boolean needClosingTag() {
        return true;
    }
}
