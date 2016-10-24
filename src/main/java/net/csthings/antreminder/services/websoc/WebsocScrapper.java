package net.csthings.antreminder.services.websoc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.core.util.MultivaluedMapImpl;

import net.csthings.antreminder.services.ServiceException;
import net.csthings.antreminder.services.rest.RestClientService;

public class WebsocScrapper {
    Logger LOG = LoggerFactory.getLogger(WebsocScrapper.class);
    private final String expectedTitle;

    private RestClientService restService;

    public WebsocScrapper(String url, String expectedTitle) {
        restService = new RestClientService(url);
        this.expectedTitle = expectedTitle;
    }

    public String getDeptAttr(String dept) {
        return "";
    }

    public void courseStatusMatches() {

    }

    /**
     * Searches webreg for a department
     *
     * @param dept
     * @param codes (left: courseCode, right: comma separated status)
     * @throws ServiceException 
     */
    public Document searchDept(String dept) throws ServiceException {
        // FIXME: add caching
        LOG.info("Searching for all classes in dept: {}", dept);

        MultivaluedMap form = getBaseForm();
        form.add("Dept", dept);

        String html = restService.post("", form);
        return Jsoup.parse(html, "ISO-8859-1");
    }

    public Document searchCodes(Object... codes) throws ServiceException {
        // FIXME: add caching
        LOG.info("Searching for all classes with codes: {}", codes);

        MultivaluedMap form = getBaseForm();
        form.add("CourseCodes", StringUtils.join(codes, ','));

        String html = restService.post("", form);
        return Jsoup.parse(html, "ISO-8859-1");
    }

    public Map<String, String> getMatchingClasses(Document document, Map<String, Set<String>> requestedStatus) {
        Map<String, String> result = new HashMap<>();
        Element titleBar = document.select(".title-bar > h1").first();
        if (titleBar == null || titleBar.text() == null) {
            LOG.error("Title Bar or it's text is null");
            return result;
        }

        if (!titleBar.text().equals(expectedTitle)) {
            LOG.error("Search Title Mismatch. Expected {}, found {}", expectedTitle, titleBar.text());
            return result;
        }

        List<Element> rows = document.select(".course-list > table > tbody tr");
        if (rows.isEmpty())
            return result;
        Iterator<Element> rowIter = rows.iterator();

        while (rowIter.hasNext()) {
            Element row = rowIter.next();
            Element codeElement;
            Element statusElement;
            String code = "";
            String status = "";
            try {
                codeElement = row.getElementsByTag("td").first();
                statusElement = row.getElementsByTag("td").last();
                code = codeElement.text();
                status = statusElement.text().toUpperCase();
            }
            catch (NumberFormatException e) {
                LOG.trace("Couldn't parse to int", e);
                continue;
            }
            catch (NoSuchElementException | NullPointerException e) {
                LOG.trace("Couldnt find element", e);
                continue;
            }

            Set<String> statusList = requestedStatus.getOrDefault(code, null);

            if (statusList != null && statusList.contains(status)) {
                LOG.debug("Found a match. Course {} is {}", code, status);
                result.put(code, status);
                requestedStatus.remove(code);
                if (requestedStatus.isEmpty())
                    break;
            }
        }

        return result;
    }

    private MultivaluedMap getBaseForm() {
        MultivaluedMap form = new MultivaluedMapImpl();
        form.add("Submit", "Display Web Results");
        form.add("YearTerm", "2016-92"); // Important
        return form;
    }
}
