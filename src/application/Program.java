package application;

import java.util.Scanner;

import model.dao.ClienteDao;
import model.dao.DaoFactory;
import model.dao.LinhaAereaDao;
import model.dao.ViagemDao;
import model.entities.Cliente;
import model.entities.LinhaAerea;
import model.entities.Viagem;

public abstract class Program implements ClienteDao{

	public static void main(String[] args) {
		
		
		ClienteDao clienteDao = DaoFactory.createClienteDao();
		ViagemDao viagemDao = DaoFactory.createViagemDao();
		LinhaAereaDao linhaAereaDao = DaoFactory.createLinhaAereaDao();
		LinhaAerea linha = new LinhaAerea();
		Viagem viagem = new Viagem();
		Cliente cliente = new Cliente();
		Scanner sc = new Scanner(System.in);
		
		int a = 0;
		while(a != 2) {
			System.out.println("Escolha a opção desejada:");
			System.out.println("1 - AREA DO CLIENTE \n2 - AREA LINHAS AEREAS \n3 - AREA VIAGENS\n4 - SAIR");
			int opc = sc.nextInt();
			switch (opc) {
			case 1:
				int test = 0;
				while(test != 2) {	
					System.out.println("Escolha uma opção!");
					System.out.println("1 - INSERIR CLIENTE \n2 - ATUALIZAR CLIENTE \n3 - DELETAR CLIENTE \n4 - PESQUISAR CLIENTE \n5 - LISTA DE CLIENTES");
					int resp = sc.nextInt();
					switch (resp) {
					case 1:
						cliente.inserirCliente(clienteDao);
						break;
					case 2:
						cliente.atualizarCliente(cliente, clienteDao);
						break;
					case 3:
						System.out.print("Digite o ID do cliente:");
						int id = sc.nextInt();
						cliente.deletarClientePorId(id, clienteDao);
						break;
					case 4:
						System.out.print("Digite o ID do cliente:");
						id = sc.nextInt();
						cliente.procurarClientePorId(id, cliente, clienteDao);
						break;
					case 5:
						cliente.mostrarTodosClientes(cliente, clienteDao);
						break;
					}
					System.out.println("Deseja continuar?\n1 - SIM \n2 - NAO");
					sc.nextLine();
					test = sc.nextInt();
					if(test == 2) {
						break;
					}
				}
				continue;
			case 2:
				int teste = 0;
				while(teste != 2) {	
					System.out.println("Escolha uma opção!");
					System.out.println("1 - INSERIR LINHA AEREA \n2 - ATUALIZAR LINHA AEREA \n3 - DELETAR LINHA AEREA \n4 - PESQUISAR LINHA AEREA \n5 - LISTA DE LINHAS AEREAS");
					int resp = sc.nextInt();
					switch (resp) {
					case 1:
						linha.inserirLinhaAerea(linhaAereaDao);
						break;
					case 2:
						linha.atualizarLinhaAerea(linha, linhaAereaDao);
						break;
					case 3:
						System.out.print("Digite o ID da LINHA AEREA:");
						int id = sc.nextInt();
						linha.deletarLinhaPorId(id, linhaAereaDao);
						break;
					case 4:
						System.out.print("Digite o ID LINHA AEREA:");
						id = sc.nextInt();
						linha.procurarLinhaPorId(id, linha, linhaAereaDao);
						break;
					case 5:
						linha.mostrarTodasLinha(linhaAereaDao);
						break;
					}
					System.out.println("Deseja continuar?\n1 - SIM \n2 - NAO");
					sc.nextLine();
					teste = sc.nextInt();
					if(teste == 2) {
						break;
					}
				}
				continue;
			case 3:
				int testar = 0;
				while(testar != 2) {	
					System.out.println("Escolha uma opção!");
					System.out.println("1 - INSERIR VIAGEM \n2 - ATUALIZAR VIAGEM \n3 - DELETAR VIAGEM \n4 - PESQUISAR VIAGEM \n5 - LISTA DE VIAGENS");
					int resp = sc.nextInt();
					switch (resp) {
					case 1:
						viagem.inserirViagem(viagemDao);
						break;
					case 2:
						viagem.atualizarViagem(viagem, viagemDao);
						break;
					case 3:
						System.out.print("Digite o ID da viagem:");
						int id = sc.nextInt();
						viagem.deletarViagemPorId(id, viagemDao);
						break;
					case 4:
						System.out.print("Digite o ID da viagem:");
						id = sc.nextInt();
						viagem.procurarViagemPorId(id, viagem, viagemDao);
						break;
					case 5:
						viagem.mostrarTodasViagens(viagemDao);
						break;
					}
					System.out.println("Deseja continuar?\n1 - SIM \n2 - NAO");
					sc.nextLine();
					testar = sc.nextInt();
					if(testar == 2) {
						break;
					}
				}
				continue;
			case 4:
				break;
			}
		}
		System.out.println("Programa encerrando!");
		sc.close();
		
		
		
	}
		
	
			
			
}
		