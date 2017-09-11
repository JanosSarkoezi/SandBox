package de.saj.sandbox.console.element;

import de.saj.sandbox.console.container.ElementComposite;

public class Table extends ElementComposite {
    public Table(String internalId) {
        super(internalId);
    }

    public Table() {
        super("table");
    }

    @Override
    public String getTag() {
        return "table";
    }

    @Override
    public boolean needClosingTag() {
        return true;
    }
}
