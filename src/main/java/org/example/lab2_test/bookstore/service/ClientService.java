package org.example.lab2_test.bookstore.service;

import lombok.AllArgsConstructor;
import org.example.lab2_test.bookstore.entity.Client;
import org.example.lab2_test.bookstore.repository.ClientRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public Flux<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Mono<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Mono<Client> createClient(Client client) {
        return clientRepository.save(client);
    }

    public Mono<Client> updateClient(Long id, Client client) {
        return clientRepository.findById(id)
                .flatMap(existingClient -> {
                    existingClient.setFirstName(client.getFirstName());
                    existingClient.setLastName(client.getLastName());
                    existingClient.setAge(client.getAge());
                    return clientRepository.save(existingClient);
                });
    }

    public Mono<Void> deleteClient(Long id) {
        return clientRepository.deleteById(id);
    }
}
