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
    private final WikiPage wikiPage;
    private final StringBuffer buffer;

    public TestableHtmlBuilder(PageData pageData, boolean includeSuiteSetup) {
        this.pageData = pageData;
        this.includeSuiteSetup = includeSuiteSetup;
        wikiPage = pageData().getWikiPage();
        buffer = new StringBuffer();
    }

    String buildHtml() throws Exception {

        if (pageData().hasAttribute("Test")) {
            if (includeSuiteSetup()) {
                includeInheritedPage(SuiteResponder.SUITE_SETUP_NAME, "setup");
            }
            includeInheritedPage("SetUp", "setup");
        }

        buffer.append(pageData().getContent());
        if (pageData().hasAttribute("Test")) {
            includeInheritedPage("TearDown", "teardown");
            if (includeSuiteSetup()) {
                includeInheritedPage(SuiteResponder.SUITE_TEARDOWN_NAME, "teardown");
            }
        }

        pageData().setContent(buffer.toString());
        return pageData().getHtml();
    }

    private void includeInheritedPage(String TearDown, String teardownMode) throws Exception {
        WikiPage teardown = PageCrawlerImpl.getInheritedPage(TearDown, wikiPage);
        if (teardown != null) {
            includePage(teardown, teardownMode);
        }
    }

    private void includePage(WikiPage teardown, String teardownMode) throws Exception {
        WikiPagePath tearDownPath = wikiPage.getPageCrawler().getFullPath(teardown);
        String tearDownPathName = PathParser.render(tearDownPath);
        buffer.append("!include -" + teardownMode + " .").append(tearDownPathName).append("\n");
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