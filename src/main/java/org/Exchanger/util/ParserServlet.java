package org.Exchanger.util;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.Exchanger.Enum.StatusMessage;
import org.Exchanger.dto.CurrencyDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLDecoder;

import static java.lang.Integer.parseInt;

public class ParserServlet {
    private static final Gson GSON = new Gson();

    public static String requestToJson(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();
        try(BufferedReader reader = request.getReader()){
            String line;
            while((line = reader.readLine()) != null){
                builder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }

    public static String objToJson(Object obj) {
        return new Gson().toJson(obj);
    }

    public static String objToJson(Object obj, Type typeObj) {
        return new Gson().toJson(obj, typeObj);
    }

    public static CurrencyDTO curFromJson(HttpServletRequest request) {
        return GSON.fromJson(requestToJson(request), CurrencyDTO.class);
    }

    public static CurrencyDTO curFromXml(HttpServletRequest request) {
        CurrencyDTO cur = new CurrencyDTO();

        cur.setCode(request.getParameter("code"));
        cur.setName(request.getParameter("name"));
        cur.setSign(request.getParameter("sign"));

        return cur;
    }

    public static String[] splitToPair(String codes) {
        char[] charArr = codes.toCharArray();
        String baseCurrencyCode = "";
        String targetCurrencyCode = "";

        for (int i = 0; i < 3; i++) {
            baseCurrencyCode += String.valueOf(charArr[i]);
        }
        for (int i = 3; i < 6; i++) {
            targetCurrencyCode += String.valueOf(charArr[i]);
        }

        return new String[]{baseCurrencyCode, targetCurrencyCode};
    }

    public static String paramFromXml(HttpServletRequest request, String paramName) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        String body = requestBody.toString();

        String[] parameters = body.split("&");
        for (String param : parameters) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2 && paramName.equals(keyValue[0])) {
                return URLDecoder.decode(keyValue[1], "UTF-8");
            }
        }
        return null;
    }

    public static double parseDouble(String buf) throws NumberFormatException {
        if (buf == null || buf.isEmpty()) {
            throw new NumberFormatException();
        }
        double numb = Double.parseDouble(buf);
        if (numb < 0.000001 || numb > 100000000) {
            throw new NumberFormatException();
        }
        return numb;
    }
}
