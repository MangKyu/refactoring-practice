package com.mangkyu.fitness;

import fitnesse.responders.run.SuiteResponder;
import fitnesse.wiki.*;

public class FitnessExample {
    public String testableHtml(TestableHtmlBuilder testableHtmlBuilder) throws Exception {
        return buildHtml(testableHtmlBuilder);
    }

    private String buildHtml(TestableHtmlBuilder testableHtmlBuilder) throws Exception {
        WikiPage wikiPage = testableHtmlBuilder.pageData().getWikiPage();
        StringBuffer buffer = new StringBuffer();

        if (testableHtmlBuilder.pageData().hasAttribute("Test")) {
            if (testableHtmlBuilder.includeSuiteSetup()) {
                WikiPage suiteSetup = PageCrawlerImpl.getInheritedPage(SuiteResponder.SUITE_SETUP_NAME, wikiPage);
                if (suiteSetup != null) {
                    WikiPagePath pagePath = wikiPage.getPageCrawler().getFullPath(suiteSetup);
                    String pagePathName = PathParser.render(pagePath);
                    buffer.append("!include -setup .").append(pagePathName).append("\n");
                }
            }
            WikiPage setup = PageCrawlerImpl.getInheritedPage("SetUp", wikiPage);
            if (setup != null) {
                WikiPagePath setupPath = wikiPage.getPageCrawler().getFullPath(setup);
                String setupPathName = PathParser.render(setupPath);
                buffer.append("!include -setup .").append(setupPathName).append("\n");
            }
        }

        buffer.append(testableHtmlBuilder.pageData().getContent());
        if (testableHtmlBuilder.pageData().hasAttribute("Test")) {
            WikiPage teardown = PageCrawlerImpl.getInheritedPage("TearDown", wikiPage);
            if (teardown != null) {
                WikiPagePath tearDownPath = wikiPage.getPageCrawler().getFullPath(teardown);
                String tearDownPathName = PathParser.render(tearDownPath);
                buffer.append("!include -teardown .").append(tearDownPathName).append("\n");
            }
            if (testableHtmlBuilder.includeSuiteSetup()) {
                WikiPage suiteTeardown = PageCrawlerImpl.getInheritedPage(SuiteResponder.SUITE_TEARDOWN_NAME, wikiPage);
                if (suiteTeardown != null) {
                    WikiPagePath pagePath = wikiPage.getPageCrawler().getFullPath(suiteTeardown);
                    String pagePathName = PathParser.render(pagePath);
                    buffer.append("!include -teardown .").append(pagePathName).append("\n");
                }
            }
        }

        testableHtmlBuilder.pageData().setContent(buffer.toString());
        return testableHtmlBuilder.pageData().getHtml();
    }
}
