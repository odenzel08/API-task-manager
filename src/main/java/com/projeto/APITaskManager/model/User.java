package com.projeto.APITaskManager.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @JsonManagedReference //Não entrar num looping de cada usuario mostrar sua tarefa e cada tarefa mostrar seu usuario. "lado pai"
    private List<Tarefa> tarefas = new ArrayList<>();
}
