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
            String setupMode = "setup";
            if (includeSuiteSetup()) {
                WikiPage suiteSetup = PageCrawlerImpl.getInheritedPage(SuiteResponder.SUITE_SETUP_NAME, wikiPage);
                if (suiteSetup != null) {
                    includePage(suiteSetup, setupMode);
                }
            }
            WikiPage setup = PageCrawlerImpl.getInheritedPage("SetUp", wikiPage);
            if (setup != null) {
                includePage(setup, setupMode);
            }
        }

        buffer.append(pageData().getContent());
        if (pageData().hasAttribute("Test")) {
            WikiPage teardown = PageCrawlerImpl.getInheritedPage("TearDown", wikiPage);
            String teardownMode = "teardown";
            if (teardown != null) {
                includePage(teardown, teardownMode);
            }
            if (includeSuiteSetup()) {
                WikiPage suiteTeardown = PageCrawlerImpl.getInheritedPage(SuiteResponder.SUITE_TEARDOWN_NAME, wikiPage);
                if (suiteTeardown != null) {
                    includePage(suiteTeardown, teardownMode);
                }
            }
        }

        pageData().setContent(buffer.toString());
        return pageData().getHtml();
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