package de.saj.sandbox.console.element;

import de.saj.sandbox.console.container.ElementComposite;

public class Li extends ElementComposite {
    public Li(String internalId) {
        super(internalId);
    }

    public Li() {
        super("li");
    }

    @Override
    public String getTag() {
        return "li";
    }

    @Override
    public boolean needClosingTag() {
        return true;
    }
}
