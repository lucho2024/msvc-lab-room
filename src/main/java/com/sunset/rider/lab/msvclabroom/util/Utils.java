package com.sunset.rider.lab.msvclabroom.util;



import com.sunset.rider.lab.exceptions.exception.HeaderExeption;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {


    public static void validHeaders(Map<String, String> headers,List<String> headersValid) {

        List<String> headersOut = new ArrayList<>();

        List<String> headersin = new ArrayList<>();
        headers.entrySet().stream().forEach(k -> {
            headersin.add(k.getKey().toUpperCase());

        });

        headersValid.forEach(h -> {
            if (!headersin.contains(h.toUpperCase())) {
                headersOut.add(h.toUpperCase());
            }
        });

        if(!headersOut.isEmpty()){
            Map<String,Object> headersRe= new HashMap<>();

            headersRe.put("Error","Headers are missing : "+headersOut.toString());
            headersRe.put("timestamp", LocalDateTime.now());

            throw new HeaderExeption(headersRe);

        }

    }
}
