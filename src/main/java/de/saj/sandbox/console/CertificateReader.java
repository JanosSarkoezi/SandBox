package de.saj.sandbox.console;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;

/**
 * @author saj
 */
public class CertificateReader {

    private X509Certificate certificate;
    private Map<String, Object> subjectPrinzipals = new HashMap<>();
    private Map<String, Object> issuerPrinzipals = new HashMap<>();

    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    public CertificateReader(X509Certificate certificate) throws InvalidNameException {
        this.certificate = certificate;
        handleSubjectPrinzipal(certificate);
        handleIssuerPrinzipal(certificate);

    }

    public String getSubjectCommonName() {
        return handleValue(subjectPrinzipals.get("CN"));
    }

    public String getSubjectOrganisation() {
        return handleValue(subjectPrinzipals.get("O"));
    }

    public String getSubjectOrganisationUnit() {
        return handleValue(subjectPrinzipals.get("OU"));
    }

    public String getIssuerCommonName() {
        return handleValue(issuerPrinzipals.get("CN"));
    }

    public String getIssuerOrganisation() {
        return handleValue(issuerPrinzipals.get("O"));
    }

    public String getIssuerOrganisationUnit() {
        return handleValue(issuerPrinzipals.get("OU"));
    }

    public String getNotBefore() {
        return format.format(certificate.getNotBefore());
    }

    public String getNotAfter() {
        return format.format(certificate.getNotAfter());
    }

    public String getFingerPrint(String algorithm) {
        byte[] digest = new byte[0];

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            byte[] der = certificate.getEncoded();
            messageDigest.update(der);
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException | CertificateEncodingException ex) {
            Logger.getLogger(CertificateReader.class.getName()).log(Level.SEVERE, null, ex);
            return "<Konnte nicht gepast werden>";
        }

        return hexify(digest);
    }

    private void handleSubjectPrinzipal(X509Certificate certificate) throws InvalidNameException {
        String name = certificate.getSubjectX500Principal().getName();
        List<Rdn> rdns = new LdapName(name).getRdns();
        for (Rdn rdn : rdns) {
            subjectPrinzipals.put(rdn.getType(), rdn.getValue());
        }
    }

    private void handleIssuerPrinzipal(X509Certificate certificate) throws InvalidNameException {
        String name = certificate.getIssuerX500Principal().getName();
        List<Rdn> rdns = new LdapName(name).getRdns();
        for (Rdn rdn : rdns) {
            issuerPrinzipals.put(rdn.getType(), rdn.getValue());
        }
    }

    private String handleValue(Object object) {
        String value = null;
        if (object == null) {
            value = "<Nicht Teil des Zertifikats>";
        } else {
            value = object.toString();
        }

        return value;
    }

    private String hexify(byte[] bytes) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        StringBuffer buf = new StringBuffer(bytes.length * 2);

        for (int i = 0; i < bytes.length; ++i) {
            buf.append(hexDigits[(bytes[i] & 0xf0) >> 4]);
            buf.append(hexDigits[bytes[i] & 0x0f]);
            if (i < bytes.length - 1) {
                buf.append(':');
            }
        }

        return buf.toString();
    }
}
