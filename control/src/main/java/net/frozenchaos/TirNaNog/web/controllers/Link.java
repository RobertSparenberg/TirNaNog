package net.frozenchaos.TirNaNog.web.controllers;

public class Link {
    private final String link;
    private final String text;

    public Link(String link, String text) {
        this.link = link;
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public String getText() {
        return text;
    }
}
