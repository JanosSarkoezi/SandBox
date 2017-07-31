package de.saj.sandbox.console.view.action;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.eclipse.draw2d.geometry.Rectangle;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class SaveSvgAction extends AbstractAction {
    private Container container;
    private JTextArea outputSVG;

    public SaveSvgAction(Container container, JTextArea outputSVG) {
        this.container = container;
        this.outputSVG = outputSVG;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Printing SVG ...");

        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();

        String svgNS = "http://www.w3.org/2000/svg";
        Document document = domImpl.createDocument(svgNS, "svg", null);
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

//        Rectangle bounds = new Rectangle(0, 0, 800, 600);
//        GraphicsToGraphics2DAdaptor graphicsAdaptor = new GraphicsToGraphics2DAdaptor(svgGenerator, bounds);
//        container.paint(graphicsAdaptor);
        container.paint(svgGenerator);

        boolean useCSS = false;
        Writer out = null;

        try {
//            out = new OutputStreamWriter(System.out, "UTF-8");
            out = new OutputStreamWriter(new TextAreaOutputStream(outputSVG), "UTF-8");
            svgGenerator.stream(out, useCSS);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (SVGGraphics2DIOException e1) {
            e1.printStackTrace();
        }
    }
}
