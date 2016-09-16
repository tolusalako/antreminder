package net.csthings.antreminder.websoc.utils;

import java.util.LinkedList;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.core.util.MultivaluedMapImpl;

public final class WebSocParser {

    // public static Pair<String, String> parseDeptElement(String dept) {
    // String[] result = dept.split("\\.");
    // return new Pair<String, String>(result[0], result[result.length - 1]);
    // }
    //
    // public static List<Pair<String, String>> parseDeptElement(List<String>
    // dept) {
    // List<Pair<String, String>> result = new ArrayList<>();
    // for (int i = 1; i < dept.size(); ++i) {
    // result.add(parseDeptElement(dept.get(i)));
    // }
    // return result;

    // }

    public static MultivaluedMap toMultivaluedMap(Map<?, ?> map) {
        MultivaluedMap formData = new MultivaluedMapImpl();
        for (Object k : map.keySet()) {
            LinkedList<String> o = (LinkedList<String>) map.get(k);
            formData.add(k, o.get(0));
        }
        return formData;
    }

}
