package io.github.kawajava.MMOEstateManager.admin.utils;

import com.github.slugify.Slugify;

public class SlugifyUtils {

    public static String slugifySlug(String slug) {
        Slugify slugify = new Slugify();
        return slugify.withCustomReplacement("_", "-")
                .slugify(slug);
    }

}
