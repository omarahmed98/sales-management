package com.vendor.system.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.stereotype.Service;
import com.vendor.system.Entity.Client;
import com.vendor.system.Repository.ClientRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {
private final ClientRepository clientRepository;
    public List<Client> getallClients()
    {
        return clientRepository.findAll();
    }
    public Client saveClient(Client client) {
        return clientRepository
                .save(client);         
    }
    public int updateClient(Client client) {
        return clientRepository
        .updateClient(client); 
    }
}
