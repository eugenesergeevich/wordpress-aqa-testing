package aqajava.models;

import lombok.Data;

@Data
public class CommentData {

    private String comment = "Comment " + Randomizer.randomAlphaNumericContent();
    private String name = "Name " + Randomizer.randomAlphaNumericTitle();
    private String email = Randomizer.randomAlphaNumericTitle() + "@mail.com";
}
