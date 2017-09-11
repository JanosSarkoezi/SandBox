package de.saj.sandbox.console.element;

import de.saj.sandbox.console.container.ElementLeaf;

public class Meta extends ElementLeaf {
    public Meta(String internalId) {
        super(internalId);
    }

    public Meta() {
        super("meta");
    }

    @Override
    public String getTag() {
        return "meta";
    }

    @Override
    public boolean needClosingTag() {
        return false;
    }
}
