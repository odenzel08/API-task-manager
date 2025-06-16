package com.projeto.APITaskManager.service;

import com.projeto.APITaskManager.dto.UserRequestDTO;
import com.projeto.APITaskManager.model.User;
import com.projeto.APITaskManager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User criarUser(UserRequestDTO userDTO) {
        User user = new User();
        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        userRepository.save(user);
        return user;
    }

    public List<User> buscarPorNome(String nome) {
        if (nome != null && !nome.isBlank()){
            return userRepository.findByName(nome);
        }
        else {
            return userRepository.findAll();
        }
    }

    public User buscarPorId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inexistente, insira um Id válido"));
    }

    public User atualizarUser(User user) {
        return userRepository.save(user);
    }

    public void deletarUser(Long id) {
        var userParaDeletar = userRepository.findById(id);

        if (userParaDeletar.isPresent()) {
            User user = userParaDeletar.get();

            if (user.getTarefas().isEmpty()) {
                userRepository.delete(user);
                return;
            } else {
                throw new IllegalArgumentException("Não foi possível deletar esse usuário, pois ele possui tarefas relacionadas");
            }
        }
        throw new NoSuchElementException("Usuário com ID " + id + " não encontrado");
    }
}