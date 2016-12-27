package net.csthings.antreminder.websoc;

import java.io.IOException;

import org.testng.annotations.Test;

import net.csthings.antreminder.services.websoc.WebsocScrapper;

@Test
public class ScrapperTests {

    public void getLatestTermTest() throws IOException {
        WebsocScrapper ws = new WebsocScrapper("https://www.reg.uci.edu/perl/WebSoc", "Schedule of Classes");
        System.out.println(ws.getLatestTerm());
    }

}
