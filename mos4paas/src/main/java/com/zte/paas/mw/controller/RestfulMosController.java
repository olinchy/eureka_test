package com.zte.paas.mw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.lite.Mos;
import com.zte.mos.message.ConfResult;
import com.zte.mos.message.Failure;
import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.util.DistinguishedList;
import com.zte.mos.util.tools.IDAllocator;
import com.zte.paas.mw.model.Configurer;
import com.zte.paas.mw.service.MosRestService;

@RestController
public class RestfulMosController implements MosRestService {
    static IDAllocator allocator = new IDAllocator(1, 256);
    @Autowired
    Mos mos;

    @GetMapping("/**")
    public Result<Mo> get(HttpServletRequest request) {
        String restOfTheUrl = (String) request.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        try {
            ManagementObject mo = mos.get(new DN("/").append(restOfTheUrl), new Maybe<>());
            return new Successful<>(mo == null ? new Mo() : mo.toMoClass());
        } catch (MOSException e) {
            return new Failure<>(e);
        }
    }

    @RequestMapping("/get")
    @Override
    public Result<Mo> get(@RequestParam String dn, @RequestParam Maybe<Integer> transId) {
        try {
            ManagementObject mo = mos.get(new DN("/").append(dn), transId);
            return new Successful<>(mo == null ? new Mo() : mo.toMoClass());
        } catch (MOSException e) {
            return new Failure<>(e);
        }
    }

    @RequestMapping("/del")
    @Override
    public ConfResult del(@RequestParam final String dn) throws MOSException {
        return config(dn, transId -> mos.del(new DN(dn), transId));
    }

    private ConfResult config(String dn, final Configurer configurer) throws MOSException {
        Maybe<Integer> transId = allocateTransId();
        DistinguishedList<String> list = new DistinguishedList<>();
        list.add(dn);

        try {
            configurer.config(transId);
            mos.commit(transId);

            return new ConfResult(list, transId);
        } catch (MOSException e) {
            mos.rollback(transId);
            return new ConfResult(e, list, transId);
        }
    }

    private Maybe<Integer> allocateTransId() {
        return new Maybe<>(allocator.allocate());
    }

    @RequestMapping("/update")
    @Override
    public ConfResult update(@RequestParam final ManagementObject mo) throws MOSException {
        return config(mo.dn().toString(), transId -> mos.set(mo, transId));
    }

    @RequestMapping("/add")
    @Override
    public ConfResult add(@RequestParam final ManagementObject mo) throws MOSException {
        return config(mo.dn().toString(), transId -> mos.add(mo, transId));
    }
}
