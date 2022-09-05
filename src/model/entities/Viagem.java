package model.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import model.dao.ViagemDao;

public class Viagem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer viagem_ID;
	private String origem;
	private String destino;
	private Integer codigo_linha;
	
	private LinhaAerea linhaAerea;
	
	public Viagem() {
	}

	public Viagem(Integer viagem_ID, String origem, String destino, Integer codigo_linha, LinhaAerea linhaAerea) {
		super();
		this.viagem_ID = viagem_ID;
		this.origem = origem;
		this.destino = destino;
		this.codigo_linha = codigo_linha;
		this.linhaAerea = linhaAerea;
	}
	
	public LinhaAerea getLinhaAerea() {
		return linhaAerea;
	}

	public void setLinhaAerea(LinhaAerea linhaAerea) {
		this.linhaAerea = linhaAerea;
	}

	public Integer getCodigo_linha() {
		return codigo_linha;
	}

	public void setCodigo_linha(Integer codigo_linha) {
		this.codigo_linha = codigo_linha;
	}

	public Integer getViagem_ID() {
		return viagem_ID;
	}

	public void setViagem_ID(Integer viagem_ID) {
		this.viagem_ID = viagem_ID;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	@Override
	public int hashCode() {
		return Objects.hash(viagem_ID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Viagem other = (Viagem) obj;
		return Objects.equals(viagem_ID, other.viagem_ID);
	}

	@Override
	public String toString() {
		return "\nviagem_ID = " + viagem_ID + "\norigem = " + origem + "\ndestino = " + destino + "\ncodigo linha aerea = " + codigo_linha + "\n=====================\n";
	}
	
	public void inserirViagem(ViagemDao viagemDao) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Cadastrar nova viagem!");
		System.out.print("Origem: ");
		String origem = sc.nextLine();
		System.out.print("Destino: ");
		String destino = sc.nextLine();
		System.out.print("Codigo da linha aerea: ");
		int codigoLinha = sc.nextInt();
		
		LinhaAerea linha = new LinhaAerea();
		Viagem novaViagem = new Viagem(viagem_ID, origem, destino, codigo_linha, linha);
		viagemDao.insert(novaViagem);
		System.out.println("Viagem adicionada com sucesso!");
	}
	
	public void atualizarViagem(Viagem viagem, ViagemDao viagemDao) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Atualizar viagem!");
		System.out.print("Digite o ID da viagem:");
		int id = sc.nextInt();
		viagem = viagemDao.findById(id);
		System.out.println("O que deseja atualizar");
		System.out.println("1 - Origem \n2 - Destino\n3 - Todas as opções");
		int resp = sc.nextInt();
		if (resp > 3) {
			System.out.println("Escolha uma opção valida.");
		}
		else {
			switch (resp) {
			case 1:
				System.out.print("Digite uma nova origem: ");
				sc.nextLine();
				String origem = sc.nextLine();
				viagem.setOrigem(origem);
				break;
			case 2:
				System.out.print("Digite um novo destino: ");
				sc.nextLine();
				String destino = sc.nextLine();
				viagem.setDestino(destino);
				break;
			case 3:
				System.out.print("Digite uma nova origem: ");
				sc.nextLine();
				origem = sc.nextLine();
				viagem.setOrigem(origem);
				System.out.print("Digite um novo destino: ");
				destino = sc.nextLine();
				viagem.setDestino(destino);
				break;
			}
		}
		System.out.println("Atualização feita com sucesso!");
	}
	
	public void deletarViagemPorId(Integer id, ViagemDao viagemDao) {
		viagemDao.deleteByID(id);
		System.out.println("Viagem deletada com sucesso!");
	}
	public void procurarViagemPorId(Integer id, Viagem viagem, ViagemDao viagemDao) {
		viagem = viagemDao.findById(id);
		System.out.println(viagem);
	}
	public void mostrarTodasViagens(ViagemDao viagemDao) {
		System.out.println("Mostrar todas as viagens!");
		Viagem viagem = new Viagem();
		List<Viagem> list = viagemDao.findAll(viagem);
		for (Viagem viagens : list) {
			System.out.println(viagens);
		}
	}
	
}
