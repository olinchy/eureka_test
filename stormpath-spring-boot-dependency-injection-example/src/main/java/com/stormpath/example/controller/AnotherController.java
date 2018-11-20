/*
 * Copyright © 2016 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.stormpath.example.controller;

import com.stormpath.example.model.MeaningOfLife;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnotherController {
    @Autowired
    MeaningOfLife meaningOfLife;

    @RequestMapping("/meaningOfLife")
    public String meaningOfLife() {
        return meaningOfLife.getLifeString();
    }
}
