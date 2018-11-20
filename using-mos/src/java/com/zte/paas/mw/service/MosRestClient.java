/*
 * Copyright © 2016 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.paas.mw.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;

@FeignClient("mos")
public interface MosRestClient {
    @GetMapping("/**")
    Result<Mo> get(HttpServletRequest request);
}
