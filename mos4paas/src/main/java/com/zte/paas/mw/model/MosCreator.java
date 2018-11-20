/*
 * Copyright © 2016 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.paas.mw.model;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zte.mos.annotation.MoEnum;
import com.zte.mos.domain.MetaStringStore;
import com.zte.mos.lite.Mos;
import com.zte.mos.type.CommonTypeRegister;
import com.zte.mos.util.Scan;
import com.zte.mos.util.Singleton;
import com.zte.mos.util.scaner.MoEnumScanner;
import com.zte.mos.util.scaner.PreLoadScanner;
import com.zte.mos.util.tools.Prop;

@Configuration
public class MosCreator {
    @Bean
    public Mos create() throws Exception {
        loadMeta();
        Prop.setProperty("storage_keyword", "store");
        Prop.setProperty("storage_path", "store");
        Prop.setProperty("debug_bdb", "false");

        CommonTypeRegister.regAll();

        Mos mos = new Mos("Root", "role", "");
        Mos.setInstance(mos);

        return mos;
    }

    private static void loadMeta() throws Exception {
        Set<Class<Object>> set = new LinkedHashSet<>();
        set.addAll(Scan.getClasses("com.zte.mos.autogen"));
        set.addAll(Scan.getClasses("com.zte.app.smartlink"));

        new MoEnumScanner().scan(Scan.getClasses("com.zte.mos.autogen", MoEnum.class));
        Prop.setProperty("confpath", "./");
        Singleton.getInstance(MetaStringStore.class);

        new PreLoadScanner().scan(set);

        CommonTypeRegister.regAll();
    }
}
