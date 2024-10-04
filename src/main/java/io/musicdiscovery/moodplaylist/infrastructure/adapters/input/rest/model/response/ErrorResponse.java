package io.musicdiscovery.user.infrastructure.adapters.input.rest.model.response;

import java.time.LocalDateTime;
import java.util.List;

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
public class ErrorResponse {

	private String code;
	private String message;
	private List<String> details;
	private LocalDateTime timestamp;
}
