package de.saj.sandbox.console;

import java.util.Locale;

/**
 * @author saj
 */
public class Processor {
    public void process() {
        String test = "AsDf";
        String a = "";
        a = a + test.toLowerCase(Locale.ROOT);
        System.out.println(a);
    }
}
