package de.saj.sandbox.console.element;

import de.saj.sandbox.console.container.ElementLeaf;

public class H1 extends ElementLeaf {
    public H1(String internalId) {
        super(internalId);
    }

    public H1() {
        super("h1");
    }

    @Override
    public String getTag() {
        return "h1";
    }

    @Override
    public boolean needClosingTag() {
        return true;
    }
}
