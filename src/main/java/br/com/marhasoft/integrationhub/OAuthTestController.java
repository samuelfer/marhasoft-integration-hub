//package br.com.marhasoft.integrationhub;
//
//import br.com.marhasoft.oauth.client.api.AccessTokenService;
//import br.com.marhasoft.oauth.client.api.OAuthRestClientFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestClient;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/oauth")
//public class OAuthTestController {
//
//    private final OAuthRestClientFactory factory;
//    private final AccessTokenService accessTokenService;
//
//
//    @GetMapping("/token/{client}")
//    public String token(@PathVariable String client) {
//        return accessTokenService.getAccessToken(client);
//    }
//
//    @GetMapping("/token")
//    public String token() {
//        return accessTokenService.getAccessToken();
//    }
//
//    @GetMapping("/teste-rest-client")
//    public String testar() {
//
//        RestClient restClient = factory.create();
//
//        return restClient.get()
//                .uri("http://localhost:8080/oauth/echo")
//                .retrieve()
//                .body(String.class);
//    }
//
//    @GetMapping("/echo")
//    public String echo(
//            @RequestHeader("Authorization") String authorization) {
//
//        return authorization;
//    }
//}