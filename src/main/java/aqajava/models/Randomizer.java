package aqajava.models;

import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.RandomStringUtils;

@Log4j
public class Randomizer {

    public static String randomAlphaNumericTitle() {
        String randomTitle = RandomStringUtils.randomAlphanumeric(15);
        log.info("Generated random title or email " + randomTitle);
        return randomTitle;
    }

    public static String randomAlphaNumericContent() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            stringBuilder.append(RandomStringUtils.randomAlphanumeric(10));
            if (i != 7) {
                stringBuilder.append(" ");
            }
        }
        log.info("Generated random content " + stringBuilder.toString());
        return stringBuilder.toString();
    }
}
