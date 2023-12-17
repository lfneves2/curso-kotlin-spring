package br.com.alura.forum.service

import br.com.alura.forum.dto.AtualizarTopicoFormDto
import br.com.alura.forum.dto.NovoTopicoFormDto
import br.com.alura.forum.dto.TopicoCategoriaDto
import br.com.alura.forum.dto.TopicoViewDto
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.TopicoFormMapper
import br.com.alura.forum.mapper.TopicoViewMapper
import br.com.alura.forum.model.Topico
import br.com.alura.forum.repository.TopicoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class TopicoService (
    private val repository: TopicoRepository,
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper,
    private val notFoundMessage: String = "Topico nao encontrado!"
) {

    fun listar(
        nomeCurso: String?,
        paginacao: Pageable
    ): Page<TopicoViewDto> {
        val topicos = if (nomeCurso == null) {
            repository.findAll(paginacao)
        } else {
            repository.findByCursoNome(nomeCurso, paginacao)
        }

        return topicos.map {
            t -> topicoViewMapper.map(t)
        }
    }

    fun buscarPorId(id: Long): TopicoViewDto {
        val topico = repository.findById(id).orElseThrow{NotFoundException(notFoundMessage)}
        return topicoViewMapper.map(topico)
    }

    fun cadastrar(topicoDto: NovoTopicoFormDto): TopicoViewDto {
        val topico = topicoFormMapper.map(topicoDto)
        repository.save(topico)
        return topicoViewMapper.map(topico);
    }

    fun atualizar(form: AtualizarTopicoFormDto): TopicoViewDto {
        val topico = repository.findById(form.id).orElseThrow {NotFoundException(notFoundMessage)}

        topico.titulo = form.titulo
        topico.mensagem = form.mensagem

        return topicoViewMapper.map(topico)

    }

    fun deletar(id: Long) {
        repository.deleteById(id)
    }

    fun relatorio(): List<TopicoCategoriaDto>{
        return repository.relatorio()
    }
}