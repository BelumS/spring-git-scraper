package com.bemsa.scraper.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public final class GitUser {
    @NonNull
    private String login;

    @NonNull
    private String name;

    @NonNull
    @JsonProperty("avatar_url")
    private String avatarUrl;

    @NonNull
    private String location;

    private String email;

    @NonNull
    @JsonProperty("html_url")
    private String url;

    @NonNull
    @JsonProperty("created_at")
    private ZonedDateTime createdAt;

    @NonNull
    @Setter
    private List<GitRepo> repos;
}
