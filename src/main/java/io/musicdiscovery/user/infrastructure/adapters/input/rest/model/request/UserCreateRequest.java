package io.musicdiscovery.user.infrastructure.adapters.input.rest.model.request;

import java.util.List;

import io.musicdiscovery.user.domain.model.enums.Genre;
import io.musicdiscovery.user.domain.model.enums.Mood;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class UserCreateRequest {

	@Schema(description = "Name of the user")
	@NotBlank(message = "Field name cannot be empty or null.")
	private String name;

	@Schema(description = "Email address of the user")
	@NotBlank(message = "Field email cannot be empty or null.")
	private String email;
	
	@ArraySchema(schema = @Schema(implementation = Mood.class, description = "User mood"))
	private Mood mood;

	@ArraySchema(schema = @Schema(implementation = Genre.class, description = "List of preferred music genres chosen by the user"))
	@NotEmpty(message = "Field preferredGenre cannot be empty or null.")
	private List<Genre> preferredGenre;

	@Schema(description = "List of the user's favorite artists")
	@NotEmpty(message = "Field favoriteArtist cannot be empty or null.")
	private List<String> favoriteArtist;
}
