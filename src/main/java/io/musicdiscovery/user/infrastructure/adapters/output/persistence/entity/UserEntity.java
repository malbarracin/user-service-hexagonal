package io.musicdiscovery.user.infrastructure.adapters.output.persistence.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import io.musicdiscovery.user.domain.model.enums.Genre;
import io.musicdiscovery.user.domain.model.enums.Mood;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;

/**
 * User entity representing a user in the system.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class UserEntity {
	@Id
	private String id;
	private String name;
	private String email;
	private Mood mood;
	private List<Genre> preferredGenre;
    private List<String> favoriteArtist;
}
