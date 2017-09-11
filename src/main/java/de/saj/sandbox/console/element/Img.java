package de.saj.sandbox.console.element;

import de.saj.sandbox.console.container.ElementLeaf;

public class Img extends ElementLeaf {
    public Img(String internalId) {
        super(internalId);
    }

    public Img() {
        super("img");
    }

    @Override
    public String getTag() {
        return "img";
    }

    @Override
    public boolean needClosingTag() {
        return false;
    }
}
