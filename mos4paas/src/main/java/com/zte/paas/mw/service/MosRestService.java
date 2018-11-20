/*
 * Copyright © 2016 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.paas.mw.service;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.ConfResult;
import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;

public interface MosRestService {
    Result<Mo> get(String dn, Maybe<Integer> transId);

    ConfResult del(String dn) throws MOSException;

    ConfResult update(ManagementObject mo) throws MOSException;

    ConfResult add(ManagementObject mo) throws MOSException;
}
