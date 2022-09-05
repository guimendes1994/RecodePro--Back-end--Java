package model.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import model.dao.LinhaAereaDao;

public class LinhaAerea implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private Integer linhaID;
	
	public LinhaAerea() {
	}

	public LinhaAerea(String nome, Integer linhaID) {
		
		this.nome = nome;
		this.linhaID = linhaID;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getLinhaID() {
		return linhaID;
	}

	public void setLinhaID(Integer linhaID) {
		this.linhaID = linhaID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(linhaID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinhaAerea other = (LinhaAerea) obj;
		return Objects.equals(linhaID, other.linhaID);
	}

	@Override
	public String toString() {
		return "Linhas Aereas \nNome = " + nome + "\nLinhaID = " + linhaID;
	}
	
	public void inserirLinhaAerea(LinhaAereaDao linhaAereaDao) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Cadastrar nova linha aerea!");
		System.out.print("Nome da linha aerea: ");
		String nome = sc.nextLine();
		LinhaAerea novalinha = new LinhaAerea(nome, null);
		linhaAereaDao.insert(novalinha);
		System.out.println("Linha aerea adicionada com sucesso!");
	}
	public void atualizarLinhaAerea(LinhaAerea linhaAerea, LinhaAereaDao linhaAereaDao) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Atualizar linha aerea!");
		System.out.print("Digite o ID da linha aerea: ");
		int id = sc.nextInt();
		linhaAerea = linhaAereaDao.findById(id);
		System.out.print("Digite o novo nome da linha aerea: ");
		String nome = sc.nextLine();
		sc.next();
		linhaAerea.setNome(nome);
		System.out.println("Atualização feita com sucesso!");
	}
	public void deletarLinhaPorId(Integer id, LinhaAereaDao linhaAereaDao) {
		linhaAereaDao.deleteById(id);
		System.out.println("Linha aerea deletada com sucesso!");
	}
	public void procurarLinhaPorId(Integer id,LinhaAerea linhaAerea, LinhaAereaDao linhaAereaDao) {
		linhaAerea = linhaAereaDao.findById(id);
		System.out.println(linhaAerea);
	}
	public void mostrarTodasLinha(LinhaAereaDao linhaAereaDao) {
		System.out.println("Mostrar todas as linhas aereas!");
		LinhaAerea linhaAerea = new LinhaAerea();
		List<LinhaAerea> list = linhaAereaDao.findByAll(linhaAerea);
		for(LinhaAerea linhas : list) {
			System.out.println(linhas);
		}
	}

}
