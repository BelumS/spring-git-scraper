package com.bemsa.scraper.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NonNull;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.bemsa.scraper.constants.ApiConstants.DATE_FORMAT;

@Getter
@JsonPropertyOrder({"user_name", "display_name", "avatar", "geo_location", "email", "url", "created_at", "repos"})
public final class GitUserUI {
    @NonNull
    @JsonProperty("user_name")
    private final String username;

    @NonNull
    @JsonProperty("display_name")
    private final String displayName;

    @NonNull
    private final String avatar;

    @NonNull
    @JsonProperty("geo_location")
    private final String geoLocation;

    private final String email;

    @NonNull
    private final String url;

    @NonNull
    @JsonProperty("created_at")
    private final String createdAt;

    @NonNull
    private final List<GitRepo> repos;

    public GitUserUI(GitUser gitUser) {
        this.username = gitUser.getLogin();
        this.displayName = gitUser.getName();
        this.email = gitUser.getEmail();
        this.avatar = gitUser.getAvatarUrl();
        this.geoLocation = gitUser.getLocation();
        this.url = gitUser.getUrl();
        this.createdAt = gitUser.getCreatedAt().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        this.repos = gitUser.getRepos();
    }
}
