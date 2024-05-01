package com.mangkyu.fitness;

import java.util.Objects;

import fitnesse.responders.run.SuiteResponder;
import fitnesse.wiki.PageCrawlerImpl;
import fitnesse.wiki.PageData;
import fitnesse.wiki.PathParser;
import fitnesse.wiki.WikiPage;
import fitnesse.wiki.WikiPagePath;

public final class TestableHtmlBuilder {

    private final PageData pageData;
    private final boolean includeSuiteSetup;

    public TestableHtmlBuilder(PageData pageData, boolean includeSuiteSetup) {
        this.pageData = pageData;
        this.includeSuiteSetup = includeSuiteSetup;
    }

    String buildHtml() throws Exception {
        WikiPage wikiPage = pageData().getWikiPage();
        StringBuffer buffer = new StringBuffer();

        if (pageData().hasAttribute("Test")) {
            if (includeSuiteSetup()) {
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

        buffer.append(pageData().getContent());
        if (pageData().hasAttribute("Test")) {
            WikiPage teardown = PageCrawlerImpl.getInheritedPage("TearDown", wikiPage);
            if (teardown != null) {
                WikiPagePath tearDownPath = wikiPage.getPageCrawler().getFullPath(teardown);
                String tearDownPathName = PathParser.render(tearDownPath);
                buffer.append("!include -teardown .").append(tearDownPathName).append("\n");
            }
            if (includeSuiteSetup()) {
                WikiPage suiteTeardown = PageCrawlerImpl.getInheritedPage(SuiteResponder.SUITE_TEARDOWN_NAME, wikiPage);
                if (suiteTeardown != null) {
                    WikiPagePath pagePath = wikiPage.getPageCrawler().getFullPath(suiteTeardown);
                    String pagePathName = PathParser.render(pagePath);
                    buffer.append("!include -teardown .").append(pagePathName).append("\n");
                }
            }
        }

        pageData().setContent(buffer.toString());
        return pageData().getHtml();
    }

    public PageData pageData() {
        return pageData;
    }

    public boolean includeSuiteSetup() {
        return includeSuiteSetup;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (TestableHtmlBuilder) obj;
        return Objects.equals(this.pageData, that.pageData) &&
            this.includeSuiteSetup == that.includeSuiteSetup;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageData, includeSuiteSetup);
    }

    @Override
    public String toString() {
        return "TestableHtmlBuilder[" +
            "pageData=" + pageData + ", " +
            "includeSuiteSetup=" + includeSuiteSetup + ']';
    }

}