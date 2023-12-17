package br.com.alura.forum.mapper

import br.com.alura.forum.dto.TopicoViewDto
import br.com.alura.forum.model.Topico
import org.springframework.stereotype.Component

@Component
class TopicoViewMapper: Mapper<Topico, TopicoViewDto> {
    override fun map(t: Topico): TopicoViewDto {
        return TopicoViewDto(
            id = t.id,
            titulo = t.titulo,
            mensagem = t.mensagem,
            status = t.status,
            dataCriacao = t.dataCriacao
        )
    }
}