package br.com.alura.forum.model

import br.com.alura.forum.enums.StatusTopico
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.ArrayList

@Entity
data class Topico (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var titulo: String,
    var mensagem: String,
    val dataCriacao: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    val curso: Curso,

    @ManyToOne
    val autor: Usuario,

    @Enumerated(value = EnumType.STRING)
    val status: StatusTopico = StatusTopico.NAO_RESPONDIDO,

    @OneToMany(mappedBy = "topico")
    val resposta: List<Resposta> = ArrayList()
)