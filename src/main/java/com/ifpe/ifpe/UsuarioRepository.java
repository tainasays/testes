/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifpe.ifpe;

/**
 *
 * @author thayn
 */

public interface UsuarioRepository {
	void salvar(Usuario usuario);
	void deletarPorCpf(String cpf);
	boolean existePorEmail(String email);
	boolean existePorCpf(String cpf);
	Usuario buscarPorCpf(String cpf);
}
