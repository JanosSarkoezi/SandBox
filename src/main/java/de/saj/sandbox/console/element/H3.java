package de.saj.sandbox.console.element;

import de.saj.sandbox.console.container.ElementLeaf;

public class H3 extends ElementLeaf {
    public H3(String internalId) {
        super(internalId);
    }

    public H3() {
        super("h3");
    }

    @Override
    public String getTag() {
        return "h3";
    }

    @Override
    public boolean needClosingTag() {
        return true;
    }
}
