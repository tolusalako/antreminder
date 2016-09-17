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
