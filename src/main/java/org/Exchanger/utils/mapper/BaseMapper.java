package org.Exchanger.utils.mapper;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;

public class BaseMapper {

    public static String toJson(Object obj) {
        return new Gson().toJson(obj);
    }

    public static String paramFromUrl(HttpServletRequest request, String paramName) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        String body = requestBody.toString();

        StringBuilder paramBody = new StringBuilder();
        String[] parameters = body.split("&");
        for (String param : parameters) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2 && paramName.equals(keyValue[0])) {
                paramBody.append(URLDecoder.decode(keyValue[1], "UTF-8"));
            }
        }
        return paramBody.toString();
    }
}
