package com.bemsa.scraper.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
@ToString
@Builder
public final class GitUser {
    @NonNull
    private final String username;

    @NonNull
    private final String displayName;

    @NonNull
    private final String avatar;

    @NonNull
    private final String geoLocation;

    private final String email;

    @NonNull
    private final String url;

    @NonNull
    @DateTimeFormat(style = "M")
    private final ZonedDateTime createdAt;

    @NonNull
    private final List<GitRepo> repos;
}
