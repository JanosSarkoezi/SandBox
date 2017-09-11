package de.saj.sandbox.console.element;

import de.saj.sandbox.console.container.ElementComposite;

public class Td extends ElementComposite {
    public Td(String internalId) {
        super(internalId);
    }

    public Td() {
        super("td");
    }

    @Override
    public String getTag() {
        return "td";
    }

    @Override
    public boolean needClosingTag() {
        return true;
    }
}
