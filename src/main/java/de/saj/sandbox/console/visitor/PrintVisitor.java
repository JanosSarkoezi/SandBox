package de.saj.sandbox.console.visitor;

import de.saj.sandbox.console.container.Attribute;
import de.saj.sandbox.console.container.Element;

import java.util.List;

public class PrintVisitor implements Visitor {
    private int indentationLevel = 0;

    @Override
    public void enter(Element element) {
        List<Attribute> attributes = element.getAttributes();

        StringBuffer buffer = new StringBuffer();
        buffer.append('<');
        buffer.append(element.getTag());

        if (attributes.isEmpty() == false) {
            for (Attribute attribute : attributes) {
                buffer.append(' ');
                buffer.append(attribute.getKey());
                buffer.append("=\"");
                buffer.append(attribute.getValue());
                buffer.append('\"');
            }
        }

        buffer.append('>');

        if (element.getParent() != null) {
            indent(element.getParent());
        }

        System.out.print(buffer.toString());
        indentationLevel++;
    }

    @Override
    public void visit(Element element) {
        if (element.getContent() != null) {
            System.out.print(element.getContent());
        }
    }

    @Override
    public void leave(Element element) {
        indentationLevel--;
        StringBuffer buffer = new StringBuffer();

        if (element.needClosingTag()) {
            buffer.append("</");
            buffer.append(element.getTag());
            buffer.append('>');

            if (element.getParent() != null) {
                indent(element);
            } else {
                System.out.println();
            }
        }

        System.out.print(buffer.toString());
    }

    private void indent(Element element) {
        if (element.isNewLine()) {
            System.out.println();
            for (int i = 0; i < indentationLevel; i++) {
                System.out.print("  ");
            }
        }
    }
}
