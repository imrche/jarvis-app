package org.rch.jarvisapp.bot.enums;

public enum Stickers {
    what("https://tlgrm.ru/_/stickers/8d5/cfe/8d5cfe72-af32-3354-89b7-4e38445019bc/6.webp"),
    wait("https://tlgrm.ru/_/stickers/8d5/cfe/8d5cfe72-af32-3354-89b7-4e38445019bc/1.webp"),
    notExactly("https://tlgrm.ru/_/stickers/8d5/cfe/8d5cfe72-af32-3354-89b7-4e38445019bc/192/33.webp");

    private String URL;

    Stickers(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }
}
