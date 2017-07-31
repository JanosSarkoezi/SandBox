package de.saj.sandbox.console;

import de.saj.sandbox.console.view.listener.Util;
import de.saj.sandbox.console.view.listener.SaveSvgListener;
import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

public class GenealogyView {
    private LightweightSystem lightweightSystem;

    public void run() {
        Shell shell = new Shell(Util.getInstance().getDisplay());
        shell.setSize(365, 280);
        shell.setText("Genealogy");
        shell.setLayout(new GridLayout());

        Canvas canvas = createDiagram(shell);
        canvas.setLayoutData(new GridData(GridData.FILL_BOTH));

        Text textArea = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        textArea.setLayoutData(new GridData(GridData.FILL_BOTH));

        final Button saveAsSVG = new Button(shell, SWT.NONE);
        saveAsSVG.setText("Button With Text");
        saveAsSVG.addSelectionListener(new SaveSvgListener(lightweightSystem.getRootFigure(), textArea));

        Display display = shell.getDisplay();
        shell.open();
        while (!shell.isDisposed()) {
            while (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    private Canvas createDiagram(Composite parent) {

        // Create a root figure and simple layout to contain
        // all other figures
        Figure root = new Figure();
        root.setFont(parent.getFont());
        XYLayout layout = new XYLayout();
        root.setLayoutManager(layout);


        // Add the father "Andy"
        IFigure andy = createPersonFigure("Andy");
        root.add(andy);
        layout.setConstraint(andy, new Rectangle(new Point(10, 10), andy.getPreferredSize()));

        // Add the mother "Betty"
        IFigure betty = createPersonFigure("Betty");
        root.add(betty);
        layout.setConstraint(betty, new Rectangle(new Point(230, 10), betty.getPreferredSize()));

        // Add the son "Carl"
        IFigure carl = createPersonFigure("Carl");
        root.add(carl);
        layout.setConstraint(carl, new Rectangle(new Point(120, 120), carl.getPreferredSize()));


        // Create a canvas to display the root figure
        Canvas canvas = new Canvas(parent, SWT.DOUBLE_BUFFERED);
        canvas.setBackground(ColorConstants.white);
        lightweightSystem = new LightweightSystem(canvas);
        lightweightSystem.setContents(root);
        return canvas;
    }

    private IFigure createPersonFigure(String name) {
        RectangleFigure rectangleFigure = new RectangleFigure();
        rectangleFigure.setBackgroundColor(ColorConstants.lightGray);
        rectangleFigure.setLayoutManager(new ToolbarLayout());
        rectangleFigure.setPreferredSize(100, 100);
        rectangleFigure.add(new Label(name));
        return rectangleFigure;
    }
}