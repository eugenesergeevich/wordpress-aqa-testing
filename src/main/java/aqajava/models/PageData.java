package aqajava.models;

import lombok.Data;

@Data
public class PageData {

    private String pageTitle = "PageTitle " + Randomizer.randomAlphaNumericTitle();
    private String pageContent = "PageContent " + Randomizer.randomAlphaNumericContent();
}
