package aqajava.models;

import lombok.Data;

@Data
public class PostData {

    private String postTitle = "PostTitle " + Randomizer.randomAlphaNumericTitle();
    private String postContent = "PostContent " + Randomizer.randomAlphaNumericContent();
}
