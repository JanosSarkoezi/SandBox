package de.saj.sandbox.console.element;

import de.saj.sandbox.console.container.ElementLeaf;

public class Link extends ElementLeaf {
    public Link(String internalId) {
        super(internalId);
    }

    public Link() {
        super("link");
    }

    @Override
    public String getTag() {
        return "link";
    }

    @Override
    public boolean needClosingTag() {
        return false;
    }
}
