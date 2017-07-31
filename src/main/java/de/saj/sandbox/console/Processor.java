package de.saj.sandbox.console;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Processor {
    private static final String CERTIFICATE_TYPE_X509 = "X.509";

    public void process() throws CertificateException {
        Display display = Display.getDefault();
        Shell shell = new Shell(display);

        X509Certificate certificate = createCertificateChainFromFile("/home/saj/wwwgooglede.crt");

        new CertificateDialog(shell, certificate).open();

        shell.pack();
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    private X509Certificate createCertificateChainFromFile(String certificatePath) throws CertificateException {
        // This requires the resources folder to be in the binary build and on the classpath.
        File certificateFile = new File(this.getClass().getClassLoader().getResource("wwwgooglede.crt").getFile());
        FileInputStream certificateInputStream = null;
        try {
            certificateInputStream = new FileInputStream(certificateFile);
        } catch (FileNotFoundException e) {
            Logger.getLogger(CertificateDialog.class.getName()).log(Level.SEVERE, null, e);
        }

        return (X509Certificate) CertificateFactory.getInstance(CERTIFICATE_TYPE_X509).generateCertificate(certificateInputStream);
    }
}
