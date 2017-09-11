package de.saj.sandbox.console.element;

import de.saj.sandbox.console.container.ElementComposite;

public class Tr extends ElementComposite {
    public Tr(String internalId) {
        super(internalId);
    }

    public Tr() {
        super("tr");
    }

    @Override
    public String getTag() {
        return "tr";
    }

    @Override
    public boolean needClosingTag() {
        return true;
    }
}
