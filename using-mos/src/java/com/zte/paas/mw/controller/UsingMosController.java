package com.zte.paas.mw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;
import com.zte.paas.mw.service.MosRestClient;

@RestController
public class UsingMosController {
    @Autowired
    MosRestClient client;

    @GetMapping("/**")
    public Result<Mo> get(HttpServletRequest request) {
        return client.get(request);
    }
}
