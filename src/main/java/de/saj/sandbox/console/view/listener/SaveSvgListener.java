package de.saj.sandbox.console.view.listener;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Text;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class SaveSvgListener implements SelectionListener {
    private IFigure rootFigure;
    private Text textArea;

    public SaveSvgListener(IFigure rootFigure, Text textArea) {
        this.rootFigure = rootFigure;
        this.textArea = textArea;
    }

    @Override
    public void widgetSelected(SelectionEvent selectionEvent) {
        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();

        String svgNS = "http://www.w3.org/2000/svg";
        Document document = domImpl.createDocument(svgNS, "svg", null);
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

        Rectangle bounds = rootFigure.getBounds();
        IFigure figure = (IFigure) rootFigure.getChildren().get(0);
        GraphicsToGraphics2DAdaptor graphicsAdaptor = new GraphicsToGraphics2DAdaptor(svgGenerator, bounds);
        figure.paint(graphicsAdaptor);

        boolean useCSS = false;
        Writer out = null;

        try {
            out = new OutputStreamWriter(new TextOutputStream(textArea), "UTF-8");
            svgGenerator.stream(out, useCSS);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (SVGGraphics2DIOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent selectionEvent) {

    }
}
