package io.musicdiscovery.user.infrastructure.adapters.input.rest.model.request;

import io.musicdiscovery.user.domain.model.enums.Mood;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class UpdateMoodRequest {
	
	@ArraySchema(schema = @Schema(implementation = Mood.class, description = "User mood"))
	@NotBlank(message = "Field mood cannot be empty or null.")
	private Mood mood;
}
