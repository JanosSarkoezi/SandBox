package de.saj.sandbox.console;

import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InvalidNameException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class CertificateDialog extends TitleAreaDialog {

    private X509Certificate certificate;
    private static final String MESSAGE_CERTIFICATE
            = "Dem Zertifikat wird nicht vertraut, weil es vom Aussteller selbst signiert wurde. Möchten Sie diesem Zertifikat zukünftig vertrauen?";
    private static final String TITLE_CERTIFICATE = "Selbst signiertes Zertifikat";

    /**
     * Create the dialog.
     *
     * @param parentShell
     */
    public CertificateDialog(Shell parentShell, X509Certificate certificate) {
        super(parentShell);
        this.certificate = certificate;
    }

    /**
     * Create contents of the dialog.
     *
     * @param parent
     * @return
     */
    @Override
    protected Control createDialogArea(Composite parent) {
        setTitle(TITLE_CERTIFICATE);
        setMessage(MESSAGE_CERTIFICATE);
        Composite container = (Composite) super.createDialogArea(parent);

        Composite area = new Composite(container, SWT.NONE);
        area.setLayout(new GridLayout(2, false));
        area.setLayoutData(new GridData(GridData.FILL_BOTH));

        CertificateReader reader = null;
        try {
            reader = new CertificateReader(certificate);
        } catch (InvalidNameException ex) {
            Logger.getLogger(CertificateDialog.class.getName()).log(Level.SEVERE, null, ex);
        }

        createHeading("Ausgestellt an", area);
        createRow("Allgemeiner Name (CN)", reader == null ? null : reader.getSubjectCommonName(), area);
        createRow("Organisation (O)", reader == null ? null : reader.getSubjectOrganisation(), area);
        createRow("Organisations Einheit (OU)", reader == null ? null : reader.getSubjectOrganisationUnit(), area);
        createRow(null, null, area);

        createHeading("Ausgestellt von", area);
        createRow("Allgemeiner Name (CN)", reader == null ? null : reader.getIssuerCommonName(), area);
        createRow("Organisation (O)", reader == null ? null : reader.getIssuerOrganisation(), area);
        createRow("Organisations Einheit (OU)", reader == null ? null : reader.getIssuerOrganisationUnit(), area);
        createRow(null, null, area);

        createHeading("Periode der Gültigkeit", area);
        createRow("Beginn", reader == null ? null : reader.getNotBefore(), area);
        createRow("Ende", reader == null ? null : reader.getNotAfter(), area);
        createRow(null, null, area);

        createHeading("Fingerabdrücke", area);
        createRow("SHA-256 Fingerabdruck", reader == null ? null : splitSHA256(reader.getFingerPrint("SHA-256")), area);
        createRow("SHA-1 Fingerabdruck", reader == null ? null : reader.getFingerPrint("SHA-1"), area);

        return container;
    }

    private String splitSHA256(String value) {
        String[] split = value.split("(?<=\\G.{48})");

        StringBuilder buffer = new StringBuilder();
        for (String string : split) {
            buffer.append(string);
            buffer.append("\n");
        }

        return buffer.toString();
    }

    private void createRow(String leftText, String rightText, Composite container) {
        Label left = new Label(container, SWT.NONE);
        left.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
        if (leftText != null) {
            left.setText(leftText);
        }

        Label right = new Label(container, SWT.NONE);
        if (rightText != null) {
            right.setText(rightText);
        }
    }

    private void createHeading(String text, Composite container) {
        Label label = new Label(container, SWT.NONE);
        label.setText(text);

        FontData fontData = label.getFont().getFontData()[0];
        fontData.setStyle(SWT.BOLD);
        label.setFont(new Font(label.getDisplay(), fontData));

        // placeholder
        new Label(container, SWT.NONE);
    }

    /**
     * Create contents of the button bar.
     *
     * @param parent
     */
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
        createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
    }

    /**
     * Return the initial size of the dialog.
     */
    @Override
    protected Point getInitialSize() {
        return new Point(720, 500);
    }
}
