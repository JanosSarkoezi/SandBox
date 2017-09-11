package de.saj.sandbox.console.visitor;

import de.saj.sandbox.console.container.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author saj
 */
public class FindVisitor implements Visitor {
    private Class clazz;
    private String internalId;
    private List<Element> elements = new ArrayList<>();

    public FindVisitor(Class clazz) {
        this.clazz = clazz;
    }

    public FindVisitor(String internalId) {
        this.internalId = internalId;
    }

    @Override
    public void enter(Element element) {

    }

    @Override
    public void visit(Element element) {
        if(clazz != null && clazz == element.getClass()) {
            elements.add(element);
        } else if(internalId != null && internalId.equals(element.getInternalId())) {
            elements.add(element);
        }
    }

    @Override
    public void leave(Element element) {

    }

    public List<Element> getElements() {
        return elements;
    }

    public Element getElement() {
        return elements.iterator().next();
    }
}
