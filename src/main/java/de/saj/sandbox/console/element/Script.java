package de.saj.sandbox.console.element;

import de.saj.sandbox.console.container.ElementLeaf;

public class Script extends ElementLeaf {
    public Script(String internalId) {
        super(internalId);
    }

    public Script() {
        super("script");
    }

    @Override
    public String getTag() {
        return "script";
    }

    @Override
    public boolean needClosingTag() {
        return true;
    }
}
