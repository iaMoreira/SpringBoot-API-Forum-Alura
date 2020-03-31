package br.com.alura.forum.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.model.Curso;
import br.com.alura.forum.model.Topico;

@RestController
public class TopicosController {
	@RequestMapping("/topicos")
	
	public List<TopicoDto> lista(){
		Topico topico = new Topico("Duvida", "Duvida com Spring", new Curso("Spring", "Programação") );
		List<Topico> topicos = new ArrayList<Topico>();
		topicos.add(topico);
		topicos.add(topico);
		topicos.add(topico);
		return TopicoDto.converter(topicos);
	}
}
