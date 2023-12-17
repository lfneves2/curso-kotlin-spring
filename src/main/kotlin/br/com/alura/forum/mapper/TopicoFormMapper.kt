package br.com.alura.forum.mapper

import br.com.alura.forum.dto.NovoTopicoFormDto
import br.com.alura.forum.model.Topico
import br.com.alura.forum.service.CursoService
import br.com.alura.forum.service.UsuarioService
import org.springframework.stereotype.Component

@Component
class TopicoFormMapper(
    private val usuarioService: UsuarioService,
    private val cursoService: CursoService,
): Mapper<NovoTopicoFormDto, Topico> {
    override fun map(t: NovoTopicoFormDto): Topico {
        return Topico (
            titulo = t.titulo,
            mensagem = t.mensagem,
            curso = cursoService.buscarPorId(t.idCurso),
            autor = usuarioService.buscarPorId(t.idAutor)
        )
    }
}