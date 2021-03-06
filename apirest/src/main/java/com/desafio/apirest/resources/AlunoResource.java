package com.desafio.apirest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.desafio.apirest.models.Aluno;

import com.desafio.apirest.repository.AlunoRepository;
import com.desafio.apirest.response.ResponseHandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Api(value = "API REST Desafio")
@CrossOrigin(origins = "*")
public class AlunoResource {

	@Autowired
	AlunoRepository alunoRepository;

	// endpoint para buscar todos os alunos cadastrados no banco de dados, utilizar @GET na requisição
	@GetMapping("/alunos")
	@ApiOperation(value = "Retorna uma lista de alunos")
	public ResponseEntity<Object> getAll() {
		if (alunoRepository.count() != 0) {
			List<Aluno> result = alunoRepository.findAll();
			return ResponseHandler.generateResponse("Dados encontrados com sucesso", HttpStatus.OK, result);
		} else {
			return ResponseHandler.generateResponse("Não existem alunos cadastrados", HttpStatus.OK, null);
		}

	}
	
	// endpoint para buscar alunos por id, utilizar @GET na requisição
	@GetMapping("/aluno/{id}")
	@ApiOperation(value = "Retorna um aluno filtrado por id")
	public ResponseEntity<Object> getById(@PathVariable(value = "id") long id) {
		if (alunoRepository.existsById(id)) {
			Aluno result = alunoRepository.findById(id);
			return ResponseHandler.generateResponse("Aluno encontrado com sucesso!", HttpStatus.OK, result);
		} else {
			return ResponseHandler.generateResponse("Não existe aluno cadastrado com esse ID", HttpStatus.OK, null);
		}

	}
	
	// endpoint para criar aluno, utilizar @POST na requisição e não passar ID no json por já ser autogenerated
	@PostMapping("/aluno/create")
	@ApiOperation(value = "Cria um novo aluno via json")
	public ResponseEntity<Object> create(@RequestBody Aluno aluno) {
		Aluno result = alunoRepository.save(aluno);
		if (result.getNome() != null && result.getDataDeNascimento() != null) {
			return ResponseHandler.generateResponse("Aluno cadastrado com sucesso!", HttpStatus.OK, result);
		} else {
			return ResponseHandler.generateResponse("Todos os campos precisam ser enviados pelo json", HttpStatus.OK,
					result);
		}

	}
	
	// endpoint para deletar aluno, utilizar @DELETE na requisição
	@DeleteMapping("/aluno/delete/{id}")
	@ApiOperation(value = "Deleta um aluno via id")
	public ResponseEntity<Object> deleteById(@PathVariable("id") long id) {
		if (alunoRepository.existsById(id)) {
			Aluno result = alunoRepository.deleteById(id);
			return ResponseHandler.generateResponse("Aluno excluído com sucesso!", HttpStatus.OK, result);
		} else {
			return ResponseHandler.generateResponse("Não existe aluno cadastrado com esse ID", HttpStatus.OK, null);
		}
	}

	// endpoint para atualizar aluno, utilizar @PUT na requisição
	@PutMapping("/aluno/update")
	@ApiOperation(value = "Atualiza um aluno via json")
	public ResponseEntity<Object> update(@RequestBody Aluno aluno) {
		Aluno result = alunoRepository.save(aluno);
		return ResponseHandler.generateResponse("Aluno atualizado com sucesso!", HttpStatus.OK, result);
	}

}
