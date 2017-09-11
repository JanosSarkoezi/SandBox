package de.saj.sandbox.console.element;

import de.saj.sandbox.console.container.ElementComposite;

public class Ul extends ElementComposite {
    public Ul(String internalId) {
        super(internalId);
    }

    public Ul() {
        super("ul");
    }

    @Override
    public String getTag() {
        return "ul";
    }

    @Override
    public boolean needClosingTag() {
        return true;
    }
}
