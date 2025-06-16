package com.projeto.APITaskManager.service;

import com.projeto.APITaskManager.dto.TarefaRequestDTO;
import com.projeto.APITaskManager.dto.TarefaResponseDTO;
import com.projeto.APITaskManager.model.StatusTarefa;
import com.projeto.APITaskManager.model.Tarefa;
import com.projeto.APITaskManager.model.User;
import com.projeto.APITaskManager.repository.TarefaRepository;
import com.projeto.APITaskManager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository repository;
    private final UserRepository userRepository;

    public List<TarefaResponseDTO> buscarTodas (){

        List<Tarefa> tarefas = repository.findAll();

        List<TarefaResponseDTO> lista = tarefas.stream()
                .map(TarefaResponseDTO::mapearParaResponse)
                .collect(Collectors.toList());
        return lista;
    }

    public TarefaResponseDTO encontrarPorId (Long id){
        var tarefaOptional = repository.findById(id);
        if (tarefaOptional.isPresent()){
            Tarefa tarefa = tarefaOptional.get();
            return TarefaResponseDTO.mapearParaResponse(tarefa);
        }
        return null;
    }

    public Tarefa salvarTarefa(TarefaRequestDTO dto){
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(dto.titulo());
        tarefa.setStatus(dto.status());
        tarefa.setDescricao(dto.descricao());
        tarefa.setUsuario(dto.idUsuario());
        return repository.save(tarefa);
    }

    public TarefaResponseDTO atualizarTarefa(Long id, TarefaRequestDTO requestDTO) {
        var tarefaOptional = repository.findById(id);

        if (tarefaOptional.isPresent()) {
            Tarefa tarefa = tarefaOptional.get();

            tarefa.setTitulo(requestDTO.titulo());
            tarefa.setStatus(requestDTO.status());
            tarefa.setDescricao(requestDTO.descricao());

            if (requestDTO.idUsuario() != null) {
                User usuario = userRepository.findById(requestDTO.idUsuario().getId())
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
                tarefa.setUsuario(usuario);
            }
            repository.save(tarefa);
            return TarefaResponseDTO.mapearParaResponse(tarefa);
        } else {
            throw new RuntimeException("Tarefa não encontrada com o ID: " + id);
        }
    }

    public void deletarPorId(Long id){
        var tarefaParaDeletar = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID inválido."));
        repository.delete(tarefaParaDeletar);
    }
}
