package de.saj.sandbox.console.element;

import de.saj.sandbox.console.container.ElementLeaf;

public class Title extends ElementLeaf {
    public Title(String internalId) {
        super(internalId);
    }

    public Title() {
        super("title");
    }

    @Override
    public String getTag() {
        return "title";
    }

    @Override
    public boolean needClosingTag() {
        return true;
    }
}
