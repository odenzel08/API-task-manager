package com.projeto.APITaskManager.controller;

import com.projeto.APITaskManager.dto.UserRequestDTO;
import com.projeto.APITaskManager.model.User;
import com.projeto.APITaskManager.repository.UserRepository;
import com.projeto.APITaskManager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserRepository repository;

    @PostMapping
    public ResponseEntity<User> salvar (@RequestBody UserRequestDTO userRequestDTO) {
        var user = service.criarUser(userRequestDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<User>> pesquisar(
            @RequestParam(value = "name", required = false) String name){
        List<User> users = service.buscarPorNome(name);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> buscarPorId(@PathVariable Long id){
        User user = service.buscarPorId(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar (@PathVariable("id") Long id, @RequestBody UserRequestDTO userRequestDTO){
        var userEntidade = repository.findById(id);
        if (userEntidade.isPresent()){
            User user = userEntidade.get();
            user.setName(userRequestDTO.name());
            user.setEmail(userRequestDTO.email());
            service.atualizarUser(user);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarUser(@PathVariable("id") Long id){
        service.deletarUser(id);
        return ResponseEntity.noContent().build();
    }
}
