/*
 * Copyright © 2016 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.paas.mw.model;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;

public interface Configurer {
    void config(Maybe<Integer> transId) throws MOSException;
}
