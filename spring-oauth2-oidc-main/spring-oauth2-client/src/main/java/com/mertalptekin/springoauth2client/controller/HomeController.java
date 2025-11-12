package com.mertalptekin.springoauth2client.controller;

import com.nimbusds.oauth2.sdk.Scope;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


//    private String _projectName;
//    // Config serverdaki isimler ile eşleşmelidir.
//    private String _environmentName;

    private final OAuth2AuthorizedClientService authorizedClientService;

    // @Value("${projectName}") String projectName, @Value("${environmentName}") String environmentName
    public HomeController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
//        this._projectName = projectName;
//        this._environmentName = environmentName;
    }

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OidcUser principal, OAuth2AuthenticationToken authentication) {

//        System.out.println("env " + _environmentName + "projectName " + _projectName);


        if (principal != null) {
            // Token'dan gelen "claim"leri modele ekle
            model.addAttribute("username", principal.getClaim("sub")); // Genellikle kullanıcı adı
            model.addAttribute("claims", principal.getClaims());

            // Access Token

            // Access Token
            OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                    authentication.getAuthorizedClientRegistrationId(),
                    authentication.getName()
            );

            if (client != null && client.getAccessToken() != null) {
                String accessToken = client.getAccessToken().getTokenValue();
                model.addAttribute("accessToken", accessToken);
            }


        }
        return "index"; // index.html'i render et
    }
}

