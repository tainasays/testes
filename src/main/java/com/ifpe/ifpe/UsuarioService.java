/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifpe.ifpe;

/**
 *
 * @author thayn
 */
public class UsuarioService {
	private final UsuarioRepository repository;

	public UsuarioService(UsuarioRepository repository) {
		this.repository = repository;
	}

	public void cadastrarUsuario(Usuario usuario) {
		if (usuario == null ||
			isNullOrEmpty(usuario.getNome()) ||
			isNullOrEmpty(usuario.getEmail()) ||
			isNullOrEmpty(usuario.getCpf()) ||
			isNullOrEmpty(usuario.getEndereco()) ||
			isNullOrEmpty(usuario.getSenha())) {
			throw new IllegalArgumentException("Nenhum campo pode ser nulo ou vazio");
		}
		if (repository.existePorEmail(usuario.getEmail())) {
			throw new IllegalArgumentException("Email já cadastrado");
		}
		if (repository.existePorCpf(usuario.getCpf())) {
			throw new IllegalArgumentException("CPF já cadastrado");
		}
		if (usuario.getCpf().length() != 11) {
			throw new IllegalArgumentException("CPF deve ter 11 dígitos");
		}
		if (usuario.getSenha().length() < 8) {
			throw new IllegalArgumentException("Senha deve ter no mínimo 8 caracteres");
		}
		repository.salvar(usuario);
	}

	public void deletarUsuarioPorCpf(String cpf) {
		if (isNullOrEmpty(cpf)) {
			throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
		}
		if (!repository.existePorCpf(cpf)) {
			throw new IllegalArgumentException("Usuário não encontrado para o CPF informado");
		}
		repository.deletarPorCpf(cpf);
	}

	private boolean isNullOrEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}
}
