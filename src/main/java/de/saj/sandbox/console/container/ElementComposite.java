package de.saj.sandbox.console.container;

import de.saj.sandbox.console.visitor.Visitor;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author saj
 */
public abstract class ElementComposite extends Element {

    @Expose
    private List<Element> children = new ArrayList<Element>();

    public ElementComposite(String internalId) {
        super(internalId);
    }

    public ElementComposite add(Element element) {
        children.add(element);
        element.setParent(this);
        return this;
    }

    @Override
    public ElementComposite content(String content) {
        super.content(content);
        return this;
    }

    @Override
    public ElementComposite newLine() {
        return (ElementComposite ) super.newLine();
    }

    public ElementComposite attribute(String key, String value) {
        super.attribute(key, value);
        return this;
    }

    public List<Element> getChildren() {
        return children;
    }

    public void remove(Element container) {
        children.remove(container);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.enter(this);
        visitor.visit(this);

        for (Element child : children) {
            child.accept(visitor);
        }

        visitor.leave(this);
    }
}
