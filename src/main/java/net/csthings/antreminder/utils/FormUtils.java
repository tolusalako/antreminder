/**
 * Copyright (c) 2016-2017 Toluwanimi Salako. http://csthings.net

 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package net.csthings.antreminder.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.core.util.MultivaluedMapImpl;

public final class FormUtils {

    public static MultivaluedMap toMultiValuedMap(Map<?, ?> map) {
        MultivaluedMap formData = new MultivaluedMapImpl();
        for (Object k : map.keySet()) {
            LinkedList<String> o = (LinkedList<String>) map.get(k);
            formData.add(k, o.get(0));
        }
        return formData;
    }

    /**
     * Extracts the very first values from each of the multivalued objects
     * @param map
     * @return
     */
    public static Map<String, Object> toSingleValuedMap(Map<?, ?> map) {
        Map formData = new HashMap();
        for (Entry<?, ?> k : map.entrySet()) {
            LinkedList<String> o = (LinkedList<String>) k.getValue();
            formData.put(k.getKey(), o.get(0));
        }
        return formData;
    }

}
