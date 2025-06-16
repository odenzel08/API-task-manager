package com.projeto.APITaskManager.dto;

import com.projeto.APITaskManager.model.StatusTarefa;
import com.projeto.APITaskManager.model.Tarefa;

import java.time.LocalDateTime;

public record TarefaResponseDTO(Long id,
                                String titulo,
                                StatusTarefa status,
                                LocalDateTime dataCriacao,
                                String descricao){

    public static TarefaResponseDTO mapearParaResponse(Tarefa tarefa){
        TarefaResponseDTO dto = new TarefaResponseDTO(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getStatus(),
                tarefa.getDataCriacao(),
                tarefa.getDescricao()
        );
        return dto;
    }
}
