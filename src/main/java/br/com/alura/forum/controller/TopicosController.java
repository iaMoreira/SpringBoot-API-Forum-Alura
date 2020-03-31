package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhedoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRepository repository;
	@Autowired
	private CursoRepository cursoRepository;
	@GetMapping
	public List<TopicoDto> lista(String nomeCurso){
		List<Topico> topicos;
		if(nomeCurso == null) {
			topicos = repository.findAll();			
		}else {
			topicos = repository.findByCursoNome(nomeCurso);
		}
		return TopicoDto.converter(topicos);
	}
	
	@PostMapping
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriComponentsBuilder ) {
		Topico topico = form.converter(cursoRepository);
		repository.save(topico);
		
		URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhedoTopicoDto> detalhar(@PathVariable Long id) {
		Optional<Topico> topico = repository.findById(id);
		if(topico.isPresent()) {
			return ResponseEntity.ok(new DetalhedoTopicoDto(topico.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form, UriComponentsBuilder uriComponentsBuilder ){
		Optional<Topico> optional = repository.findById(id);
		if(optional.isPresent()) {
			Topico topico = form.atualizar(id, repository);
			return ResponseEntity.ok(new TopicoDto(topico));
		}
		return ResponseEntity.notFound().build();

	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> atualizar(@PathVariable Long id){
		Optional<Topico> optional = repository.findById(id);
		if(optional.isPresent()) {
			repository.deleteById(id);	
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();

	}
	
	
}
