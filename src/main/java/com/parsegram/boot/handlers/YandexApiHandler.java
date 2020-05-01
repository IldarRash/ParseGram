package com.parsegram.boot.handlers;

import com.parsegram.boot.model.YandexClient;
import com.parsegram.boot.services.YandexService;
import com.parsegram.boot.utils.ServerRequestResponseConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;

@Component
public class YandexApiHandler extends AbstractHandler<YandexClient> {
    private final YandexService yandexService;

    public YandexApiHandler(YandexService yandexService) {
        super(new ServerRequestResponseConverter<>());
        this.yandexService = yandexService;
    }

    @Override
    public Mono<ServerResponse> get(ServerRequest request) {
        Mono<YandexClient> yandexApi = yandexService.findYandexProps();
        return getServerRequestResponseConverter().convertBodyToResponse(yandexApi);
    }

    @Override
    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        Optional<String> code = serverRequest.queryParam("code");
        return ServerResponse.permanentRedirect(URI.create("https://yandex.ru/"))
                .body(yandexService.acceptToYandex(Mono.just(code.get())), YandexClient.class);
    }

    @Override
    public Flux<YandexClient> getAll() {
        return null;
    }


//    public Mono<String> getYandexTree(ServerRequest sr) {
//        return yandexService.
//    }


}
