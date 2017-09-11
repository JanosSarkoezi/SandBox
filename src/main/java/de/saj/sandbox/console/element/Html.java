package de.saj.sandbox.console.element;

import de.saj.sandbox.console.container.Element;
import de.saj.sandbox.console.container.ElementComposite;

public class Html extends ElementComposite {
    public Html(String internalId) {
        super(internalId);
    }

    public Html() {
        super("html");
    }

    @Override
    public String getTag() {
        return "html";
    }

    @Override
    public boolean needClosingTag() {
        return true;
    }

    @Override
    public ElementComposite add(Element element) {
        super.add(element);
        return this;
    }
}
