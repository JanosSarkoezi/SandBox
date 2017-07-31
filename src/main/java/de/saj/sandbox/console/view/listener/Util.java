package de.saj.sandbox.console.view.listener;

/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    SAP AG - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/


import org.eclipse.swt.widgets.Display;

public class Util {

    private static Util util = new Util();
    private static Display display = new Display();

    public static Util getInstance() {
        return util;
    }

    public Display getDisplay() {
        return display;
    }

}
