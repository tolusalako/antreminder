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
package net.csthings.antreminder.services.websoc;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.csthings.antreminder.services.reminder.util.ReminderStatus;

public final class WebsocUtils {
    public static Logger LOG = LoggerFactory.getLogger(WebsocUtils.class);
    public static final int TIMEOUT = 1100;

    public static Set<String> STATUS = new HashSet<>();

    public static Set<String> getAllStatus() {
        if (STATUS.isEmpty()) {
            STATUS.add(ReminderStatus.OPEN);
            STATUS.add(ReminderStatus.FULL);
            STATUS.add(ReminderStatus.WAITLIST);
            STATUS.add(ReminderStatus.NEWONLY);
        }
        return STATUS;
    }

    public static List<String> getDepartments(String url) {
        // FIX ME: should really cache
        LOG.info("Getting departments from {}", url);
        List<String> result = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.parse(new URL(url), TIMEOUT);
        }
        catch (IOException e) {
            LOG.error("Could not get departments", e);
            return result;
        }
        List<Element> deptSelectList = doc.select("tbody > tr > td > select[name=\"Dept\"] > option");
        deptSelectList.stream().map(e -> e.val().trim()).forEach(result::add);
        result.remove("ALL");
        LOG.info("Found {} departments excluding [ALL]", result.size());
        return result;
    }

}
