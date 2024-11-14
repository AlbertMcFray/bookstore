package org.example.lab2_test.bookstore.controller;

import lombok.AllArgsConstructor;
import org.example.lab2_test.bookstore.entity.Client;
import org.example.lab2_test.bookstore.service.ClientService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/client-api")
public class ClientRestController {
    //should be interface
    private final ClientService clientService;

    @GetMapping
    public Flux<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public Mono<Client> getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @PostMapping
    public Mono<Client> createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    @PutMapping("/{id}")
    public Mono<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        return clientService.updateClient(id, client);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteClient(@PathVariable Long id) {
        return clientService.deleteClient(id);
    }
}
