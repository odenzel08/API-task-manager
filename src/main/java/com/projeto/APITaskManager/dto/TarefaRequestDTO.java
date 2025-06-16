package com.projeto.APITaskManager.dto;

import com.projeto.APITaskManager.model.StatusTarefa;
import com.projeto.APITaskManager.model.User;

import java.util.UUID;

public record TarefaRequestDTO (String titulo,
                                StatusTarefa status,
                                String descricao,
                                User idUsuario) {
}
