/*
 * Copyright (C) 2016 Saurabh Rane
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.eveningoutpost.dexdrip.Glycemiq.FoodApi;

import com.fatsecret.platform.services.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class helps in building requests for sending them to the Edamam rest api
 *
 * @author Saurabh Rane
 * @version 2.0
 */
public class RequestBuilder {
    /**
     * A value Edamam API issues to you which helps this API identify you
     */
    final private String APP_ID;

    /**
     * A secret Edamam API issues to you which helps this API establish that it really is you
     */
    final private String APP_KEY;

    /**
     * Request URL
     * <p>
     * The URL to make API calls is https://api.edamam.com/api/nutrition-data
     */
    final private String APP_URL = "https://api.edamam.com/api/nutrition-data";

    /**
     * The HTTP Method supported by Edamam API
     * <p>
     * This API only supports GET method
     */
    final private String HTTP_METHOD = "GET";


    /**
     * Constructor to set values for APP_KEY and APP_SECRET
     *
     * @param APP_ID  a value Edamam API issues to you which helps this API identify you
     * @param APP_KEY a secret Edamam API issues to you which helps this API establish that it really is you
     */
    public RequestBuilder(String APP_ID, String APP_KEY) {
        this.APP_ID = APP_ID;
        this.APP_KEY = APP_KEY;
    }

    public String getUrl(String ingredient) {
        List<String> params = new ArrayList<>();
        String[] template = new String[1];
        params.add("app_id=" + APP_ID);
        params.add("app_key=" + APP_KEY);
        params.add("ingr=" + encode(ingredient));

        return APP_URL + "?" + paramify(params.toArray(template));
    }

    /**
     * Returns the string generated using params and separator
     *
     * @param params    an array of parameter values as "key=value" pair
     * @param separator a separator for joining
     * @return the string by appending separator after each parameter from params except the last.
     */
    public String join(String[] params, String separator) {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < params.length; i++) {
            if (i > 0) {
                b.append(separator);
            }
            b.append(params[i]);
        }
        return b.toString();
    }

    /**
     * Returns string generated using params and "&amp;" for signature base and normalized parameters
     *
     * @param params an array of parameter values as "key=value" pair
     * @return the string by appending separator after each parameter from params except the last.
     */
    public String paramify(String[] params) {
        String[] p = Arrays.copyOf(params, params.length);
        Arrays.sort(p);
        return join(p, "&");
    }

    /**
     * Returns the percent-encoded string for the given url
     *
     * @param url URL which is to be encoded using percent-encoding
     * @return the encoded url
     */
    public String encode(String url) {
        if (url == null)
            return "";

        try {
            return URLEncoder.encode(url, "utf-8")
                    .replace("+", "%20")
                    .replace("!", "%21")
                    .replace("*", "%2A")
                    .replace("\\", "%27")
                    .replace("(", "%28")
                    .replace(")", "%29");
        } catch (UnsupportedEncodingException wow) {
            throw new RuntimeException(wow.getMessage(), wow);
        }
    }
}