package de.saj.sandbox.console.visitor;


import de.saj.sandbox.console.container.Element;

public interface Visitor {
    public void enter(Element element);
    public void visit(Element element);
    public void leave(Element element);
}
