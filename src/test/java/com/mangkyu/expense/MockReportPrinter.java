package com.mangkyu.expense;

public class MockReportPrinter implements ReportPrinter {

    private String printedText = "";

    public void print(String text) {
        printedText += text;
    }

    public String getText() {
        return printedText;
    }
}
