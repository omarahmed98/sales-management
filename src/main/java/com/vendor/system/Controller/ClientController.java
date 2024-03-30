package com.vendor.system.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vendor.system.DTO.ApiResponse;
import com.vendor.system.Entity.Client;
import com.vendor.system.Repository.ClientRepository;
import com.vendor.system.Service.ClientService;


@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("api/v1/client")
@Slf4j
public class ClientController {

    private final ClientService clientService;
    private final ClientRepository clientRepository;

    @Operation(summary = "get all clients")
    @GetMapping("")
    public ResponseEntity<?> getClients() {
        List<Client> clients = clientService.getallClients();
        return new ResponseEntity<>(new ApiResponse<>(true,clients,"Clients Retreived Successfully"),HttpStatus.OK);
    }

    @Operation(summary = "get client by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getClientDetails(@PathVariable("id") Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if(client == null)
        {
            return new ResponseEntity<>(new ApiResponse<>(false,null,"Client Not found"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse<>(true,client,"Client Retreived Successfully"),HttpStatus.OK);
    }

    @Operation(summary = "add client to list of clients")
    @PostMapping("")
    public ResponseEntity<?> addClient(@Valid @RequestBody Client client, BindingResult result) {
        try {
            if (result.hasErrors()) {
                Map<String, String> errors = new HashMap<>();
                for (FieldError fieldError : result.getFieldErrors()) {
                    errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
                return new ResponseEntity<>(new ApiResponse<>(false,errors,"Bad Request" ), HttpStatus.BAD_REQUEST);
            }            
            Client savedClient = clientService.saveClient(client);
            return new ResponseEntity<>(new ApiResponse<>(true,savedClient.getId(),"Client Added Successfully"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(false,null,"Internal server error"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update data of client")
    @PutMapping("")
    public ResponseEntity<?> updateClientData(@Valid @RequestBody Client client, BindingResult result) {
        try {
            if (result.hasErrors()) {
                Map<String, String> errors = new HashMap<>();
                for (FieldError fieldError : result.getFieldErrors()) {
                    errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
                return new ResponseEntity<>(new ApiResponse<>(false,errors,"Bad Request" ), HttpStatus.BAD_REQUEST);
            }            
            int savedClient = clientService.updateClient(client);
            if(savedClient==0)
            {
                return new ResponseEntity<>(new ApiResponse<>(false,null,"No rows affected"),HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiResponse<>(true,client.getId(),"Client Updated Successfully"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(false,null,"Internal server error"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "delete the client")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable("id") Long id){
        try {
            Client clientSeller = clientRepository.findById(id).orElse(null);
            if(clientSeller == null)
            {
                return new ResponseEntity<>(new ApiResponse<>(false,null,"Wrong Client Id"),HttpStatus.NOT_FOUND);
            }
            clientRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse<>(true,id,"Client delted Successfully"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(false,null,"Internal server error"),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
   
}
