package model.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import model.dao.ClienteDao;

public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer cliente_ID;
	private String nome;
	private Integer cpf;
	private String email;
	private Integer telefone;
	private Integer viagem_ID;
	
	private Viagem viagem;
	private LinhaAerea linha;
	
	public Cliente() {
	}
	
	public Cliente(Integer cliente_ID, String nome, Integer cpf, String email, Integer telefone, Integer viagem_ID, Viagem viagem) {
		this.cliente_ID = cliente_ID;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.telefone = telefone;
		this.viagem_ID = viagem_ID;
		this.viagem = viagem;
	}
	
	
	public LinhaAerea getLinha() {
		return linha;
	}

	public void setLinha(LinhaAerea linha) {
		this.linha = linha;
	}

	public Viagem getViagem() {
		return viagem;
	}

	public void setViagem(Viagem viagem) {
		this.viagem = viagem;
	}

	public Integer getViagem_ID() {
		return viagem_ID;
	}

	public void setViagem_ID(Integer viagem_ID) {
		this.viagem_ID = viagem_ID;
	}

	public Integer getCliente_ID() {
		return cliente_ID;
	}

	public void setCliente_ID(Integer cliente_ID) {
		this.cliente_ID = cliente_ID;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCpf() {
		return cpf;
	}

	public void setCpf(Integer cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getTelefone() {
		return telefone;
	}

	public void setTelefone(Integer telefone) {
		this.telefone = telefone;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cliente_ID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cliente_ID, other.cliente_ID);
	}

	@Override
	public String toString() {
		return "CLIENTE\n\ncliente_ID = " + cliente_ID + "\nnome = " + nome + "\ncpf = " + cpf + "\nemail = " + email
				+ "\ntelefone = " + telefone + "\n\nVIAGEM \n" +  getViagem() + "\n========================= \n";
	}
	
	public static void inserirCliente(ClienteDao clienteDao) {
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Cadastro novo cliente!");
			System.out.print("Nome: ");
			String nome = sc.nextLine();
			System.out.print("CPF: ");
			int cpf = sc.nextInt();
			System.out.print("Email: ");
			sc.nextLine();
			String email = sc.nextLine();
			System.out.print("Telefone: ");
			int telefone = sc.nextInt();
			System.out.print("ID da viagem: ");
			int id = sc.nextInt();
			
			
			Viagem viagem = new Viagem();
			Cliente novoCliente = new Cliente(null, nome, cpf, email, telefone, id, viagem);
			clienteDao.insert(novoCliente);
			System.out.println("Cliente adicionado com sucesso!");
	}

	public static void atualizarCliente(Cliente cliente, ClienteDao clienteDao) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Atualização de cliente!");
		System.out.print("Digite o ID do cliente: ");
		int id = sc.nextInt();
		cliente = clienteDao.findById(id);
		System.out.println("O que deseja alterar?");
		System.out.println("1 - Email \n2 - Telefone\n3 - Todas as opções");
		int resp = sc.nextInt();
		if(resp > 3) {
			System.out.println("Escolha invalida. Tente novamente");
		}
		else {
			switch (resp) {
			case 1:
				System.out.print("Digite o novo Email: ");
				sc.nextLine();
				String email = sc.nextLine();
				cliente.setEmail(email);
				break;
			case 2:
				System.out.print("Digite o novo telefone: ");
				sc.nextLine();
				int telefone = sc.nextInt();
				cliente.setTelefone(telefone);
				break;
			case 3:
				System.out.print("Digite o novo Email: ");
				sc.nextLine();
				email = sc.nextLine();
				cliente.setEmail(email);
				System.out.print("Digite o novo telefone: ");
				telefone = sc.nextInt();
				cliente.setTelefone(telefone);
				break;
			}
		}
		clienteDao.update(cliente);
		System.out.println("Atualização finalizada!");
	}

	public void deletarClientePorId(Integer id, ClienteDao clienteDao) {
		clienteDao.deleteByID(id);
		System.out.println("Deletado com sucesso!");
	}

	public void procurarClientePorId(Integer id, Cliente cliente, ClienteDao clienteDao) {
		cliente = clienteDao.findById(id);
		System.out.println(cliente);
	}

	public void mostrarTodosClientes(Cliente cliente, ClienteDao clienteDao) {
		System.out.println("Mostrar todos os clientes!");
		cliente = new Cliente();
		List<Cliente> list = clienteDao.findAll(cliente);
		for (Cliente clientes : list) {
			System.out.println(clientes);
		}
		
	}
}


