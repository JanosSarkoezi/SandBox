package de.saj.sandbox.console.container;


import de.saj.sandbox.console.visitor.Visitor;

/**
 *
 * @author saj
 */
public abstract class ElementLeaf extends Element {

    public ElementLeaf(String internalId) {
        super(internalId);
    }

    @Override
    public void setParent(Element parent) {
        this.parent = parent;
    }

    public Element getParent() {
        return parent;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.enter(this);
        visitor.visit(this);
        visitor.leave(this);
    }
}
