package io.musicdiscovery.user.infrastructure.adapters.input.rest.model.response;

import java.util.List;

import io.musicdiscovery.user.domain.model.enums.Genre;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	@Schema(description = "Unique identifier of the user")
    private String id;

    @Schema(description = "Name of the user")
    private String name;

    @Schema(description = "Email address of the user")
    private String email;

    @ArraySchema(schema = @Schema(implementation = Genre.class, description = "List of preferred music genres chosen by the user"))
    private List<Genre> preferredGenre;

    @Schema(description = "List of the user's favorite artists")
    private List<String> favoriteArtist;
}
