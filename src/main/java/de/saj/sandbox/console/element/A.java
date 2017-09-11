package de.saj.sandbox.console.element;

import de.saj.sandbox.console.container.ElementComposite;

public class A extends ElementComposite {
    public A(String internalId) {
        super(internalId);
    }

    public A() {
        super("a");
    }

    @Override
    public String getTag() {
        return "a";
    }

    @Override
    public boolean needClosingTag() {
        return true;
    }
}
