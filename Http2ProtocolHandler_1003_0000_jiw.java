// 代码生成时间: 2025-10-03 00:00:21
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class Http2ProtocolHandler {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Http2ProtocolHandler.class)
                .initializers((context) -> {
                    context.getEnvironment().getPropertySources().addLast(new CommandLineArgsPropertySource(args));
                })
                .run(args);
    }

    /**
     * Customize the Netty server to enable HTTP/2 support.
     * @return A customizer to configure the server.
     */
    @Bean
    NettyServerCustomizer nettyServerCustomizer() {
        return server -> server
                .enableSsl(sslContextBuilder -> sslContextBuilder
                        .keyManager((keyStorePath, keyStorePassword) -> null))
                .setPort(8443) // Set the port to 8443 for HTTPS
                .childOption(ChannelOption.SO_REUSEADDR, true);
    }

    /**
     * A REST controller for handling HTTP/2 requests.
     */
    @RestController
    @RequestMapping("/api/v1")
    public static class Http2RestController {

        /**
         * A sample endpoint that returns a greeting message.
         * @return A Mono<ResponseEntity<String>> containing the greeting message.
         */
        @GetMapping("/greet")
        public Mono<ResponseEntity<String>> greet() {
            return Mono.just(ResponseEntity.ok("Hello, World!"));
        }

        /**
         * Error handling for any unexpected errors.
         * @param ex The exception that occurred.
         * @return A Mono<ResponseEntity<String>> with the error details.
         */
        @ExceptionHandler
        public Mono<ResponseEntity<String>> handleException(RuntimeException ex) {
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage()));
        }
    }
}
