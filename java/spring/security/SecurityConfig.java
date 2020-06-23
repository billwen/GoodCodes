package java.spring.security;

public class SecurityConfig {
    /**
     * Adding an Error Message
     * To support the retrieval of an error message, you’ll need to capture it when authentication fails. To achieve this, you can configure an AuthenticationFailureHandler, like so:
     * @param http
     * @throws Exception
     */
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            // ... existing configuration
            .oauth2Login(o -> o
                .failureHandler((request, response, exception) -> {
                    request.getSession().setAttribute("error.message", exception.getMessage());
                    handler.onAuthenticationFailure(request, response, exception);
                })
            );
    }
}