package com.bkash.priyam.keyvalue.dynamo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Tahniat Ashraf Priyam
 * @since 3/21/21
 */

@RestController
@RequiredArgsConstructor
public class KeyValueController {

    private final KeyValueRepository keyValueRepository;

    @GetMapping("/keyValues")
    public Flux<KeyValue> getKeyValueList() {

        return keyValueRepository.getAllKeyValues();
    }

    @GetMapping("/keyValues/{key}")
    public Mono<KeyValue> getKeyValue(@PathVariable String key) {
        return keyValueRepository.getKeyValue(key);
    }

    @DeleteMapping("/keyValues/{key}")
    public Mono<KeyValue> deleteKeyValue(@PathVariable String key) {
        return keyValueRepository.deleteKeyValue(key);
    }

    @PostMapping("/keyValues")
    public Mono<ResponseEntity> saveKeyValue(@RequestBody KeyValue keyValue) {
        return keyValueRepository.saveKeyValue(keyValue)
                .map(ResponseEntity::ok);
    }


}
