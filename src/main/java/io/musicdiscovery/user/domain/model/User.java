package io.musicdiscovery.user.domain.model;


import java.util.List;

import io.musicdiscovery.user.domain.model.enums.Genre;
import io.musicdiscovery.user.domain.model.enums.Mood;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * User Domain representing a user in the system.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String name;
    private String email;
    private Mood mood;
    private List<Genre> preferredGenre;
    private List<String> favoriteArtist;

}