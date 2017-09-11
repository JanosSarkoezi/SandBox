package de.saj.sandbox.console.container;


import de.saj.sandbox.console.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author saj
 */
public abstract class Element {
    private List<Attribute> attributes = new ArrayList<>();
    private String content;
    private String internalId;
    private boolean newLine = false;

    public Element(String internalId) {
        this.internalId = internalId;
    }

    protected Element parent = null;

    public Element getParent() {
        return parent;
    }

    public void setParent(Element parent) {
        this.parent = parent;
    }

    public String getContent() {
        return content;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public Element attribute(String key, String value) {
        Attribute attribute = new Attribute(key, value);
        return attribute(attribute);
    }

    public Element content(String content) {
        this.content = content;
        return this;
    }

    public boolean isNewLine() {
        return newLine;
    }

    public Element newLine() {
        this.newLine = true;
        return this;
    }

    public String getInternalId() {
        return internalId;
    }

    public abstract void accept(Visitor visitor);

    public abstract String getTag();

    public abstract boolean needClosingTag();

    private Element attribute(Attribute attribute) {
        attributes.add(attribute);
        return this;
    }
}
