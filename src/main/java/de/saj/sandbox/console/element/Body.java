package de.saj.sandbox.console.element;

import de.saj.sandbox.console.container.ElementComposite;

public class Body extends ElementComposite {
    public Body(String internalId) {
        super(internalId);
    }

    public Body() {
        super("body");
    }

    @Override
    public String getTag() {
        return "body";
    }

    @Override
    public boolean needClosingTag() {
        return true;
    }
}
