package br.com.alura.forum.controller

import br.com.alura.forum.dto.AtualizarTopicoFormDto
import br.com.alura.forum.dto.NovoTopicoFormDto
import br.com.alura.forum.dto.TopicoCategoriaDto
import br.com.alura.forum.dto.TopicoViewDto
import br.com.alura.forum.service.TopicoService
import jakarta.validation.Valid
import org.apache.coyote.Response
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@RestController
@RequestMapping("/topicos")
class TopicosController (
    private val service: TopicoService
    ){

    @GetMapping
    @Cacheable("topicos")
    fun listar(
        @RequestParam(required = false) nomeCurso: String?,
        @PageableDefault(size = 5, sort = ["dataCriacao"], direction = Sort.Direction.DESC) paginacao: Pageable
    ): Page<TopicoViewDto> {
        return service.listar(nomeCurso, paginacao);
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): TopicoViewDto {
        return service.buscarPorId(id);
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun cadastrar(
        @RequestBody @Valid form: NovoTopicoFormDto,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<TopicoViewDto> {
        var topicoViewDto = service.cadastrar(form);
        val uri = uriBuilder.path("/topicos/${topicoViewDto.id}").build().toUri()
        return ResponseEntity.created(uri).body(topicoViewDto)
     }

    @PutMapping
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun atualizar(@RequestBody @Valid form: AtualizarTopicoFormDto): ResponseEntity<TopicoViewDto> {
        val topicoViewDto = service.atualizar(form);
        return ResponseEntity.ok(topicoViewDto)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun deletar(@PathVariable id: Long) {
        return service.deletar(id);
    }

    @GetMapping("relatorio")
    fun relatorio(): List<TopicoCategoriaDto> {
        return service.relatorio()
    }
}