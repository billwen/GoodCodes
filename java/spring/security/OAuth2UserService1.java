@Bean
public OAuth2UserService<OAuth2UserRequest, OAuth2User> OAuth2UserService1(WebClient rest) {
    DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
    return (request) -> {
        OAuth2User user = delegate.loadUser(request);
        if (!"github".equals(request.getClientRegistration().getRegistrationId())) {
        	return user;
        }

        OAuth2AuthorizedClient client = new OAuth2AuthorizedClient(request.getClientRegistration(), user.getName(), request.getAccessToken());
        String url = user.getAttribute("organizations_url");
        List<Map<String, Object>> orgs = rest
                .get().uri(url)
                .attributes(oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToMono(List.class)
                .block();

        if (orgs.stream().anyMatch(org -> "spring-projects".equals(org.get("login")))) {
            return user;
        }

        throw new OAuth2AuthenticationException(new OAuth2Error("invalid_token", "Not in Spring Team", ""));
    };
}

@Bean
public WebClient rest(ClientRegistrationRepository clients, OAuth2AuthorizedClientRepository authz) {
    ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 = new ServletOAuth2AuthorizedClientExchangeFilterFunction(clients, authz);
    return WebClient.builder()
            .filter(oauth2).build();
}