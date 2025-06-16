package com.projeto.APITaskManager.repository;

import com.projeto.APITaskManager.dto.TarefaResponseDTO;
import com.projeto.APITaskManager.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TarefaRepository extends JpaRepository<Tarefa, UUID> {

    Optional<Tarefa> findById(Long id);
    List<Tarefa> findByStatus(String status);
}
