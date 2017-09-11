package de.saj.sandbox.console.element;

import de.saj.sandbox.console.container.ElementLeaf;

public class H2 extends ElementLeaf {
    public H2(String internalId) {
        super(internalId);
    }

    public H2() {
        super("h2");
    }

    @Override
    public String getTag() {
        return "h2";
    }

    @Override
    public boolean needClosingTag() {
        return true;
    }
}
