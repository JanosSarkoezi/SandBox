package de.saj.sandbox.console.view.listener;

import org.eclipse.swt.widgets.Text;

import java.io.IOException;
import java.io.OutputStream;

public class TextOutputStream extends OutputStream {
    private Text textArea;

    public TextOutputStream(Text textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) throws IOException {
        textArea.append(String.valueOf((char) b));
    }
}
