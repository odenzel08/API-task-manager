package com.projeto.APITaskManager.controller;

import com.projeto.APITaskManager.dto.TarefaRequestDTO;
import com.projeto.APITaskManager.dto.TarefaResponseDTO;
import com.projeto.APITaskManager.model.Tarefa;
import com.projeto.APITaskManager.service.TarefaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService service;

    @GetMapping
    public ResponseEntity<List<TarefaResponseDTO>> exibirTarefas (){
        var tarefas = service.buscarTodas();
        return ResponseEntity.ok(tarefas);
    }

    @GetMapping("{id}")
    public ResponseEntity<TarefaResponseDTO> buscarPorId (@PathVariable("id") Long id){
        var dto = service.encontrarPorId(id);
        if (dto == null){
            return ResponseEntity.unprocessableEntity().build();
        } else {
            return ResponseEntity.ok(dto);
        }
    }

    @PostMapping
    public ResponseEntity<Void> criarTarefa (@RequestBody TarefaRequestDTO tarefaDto){
        var tarefa = service.salvarTarefa(tarefaDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tarefa.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<TarefaResponseDTO> atualizar (@PathVariable("id") Long id,
                                                        @RequestBody TarefaRequestDTO tarefaRequestDTO){
        var dto = service.atualizarTarefa(id, tarefaRequestDTO);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarTarefa (@PathVariable("id") Long id){
        service.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

}
