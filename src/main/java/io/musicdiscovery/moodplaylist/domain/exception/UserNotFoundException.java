package io.musicdiscovery.user.domain.exception;


/**
 * Custom exception thrown when a user is not found in the system.
 */
public class UserNotFoundException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message) {
        super(message);
    }
}
