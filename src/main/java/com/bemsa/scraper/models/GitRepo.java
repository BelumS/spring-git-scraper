package com.bemsa.scraper.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public final class GitRepo {

    @NonNull
    private String name;

    @NonNull
    private String url;
}
