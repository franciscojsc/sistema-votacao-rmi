package br.com.franciscochaves.client;

import java.net.InetAddress;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

import br.com.franciscochaves.model.Candidato;
import br.com.franciscochaves.model.Eleitor;
import br.com.franciscochaves.util.Votacao;

public class VotacaoCliente {

	public static void main(String[] args) {

		Scanner teclado;
		Votacao votacao;
		Eleitor eleitor;
		Candidato candidato;
		List<Candidato> candidatos;
		String ipDaMaquina;
		Registry registry;

		try {

			registry = LocateRegistry.getRegistry(8080);

			votacao = (Votacao) registry.lookup("localhost/Votacao");

			ipDaMaquina = InetAddress.getLocalHost().getHostAddress();
			eleitor = new Eleitor(ipDaMaquina);

			candidatos = votacao.getCandidatos();

			listarCandidatos(candidatos);

			teclado = new Scanner(System.in);

			while (true) {

				System.out.println("Digite o número do candidato para realizar sua votação:");

				int voto = teclado.nextInt();

				candidato = verificarCandidato(voto, votacao.getCandidatos());

				if (candidato != null) {

					if (votacao.setVoto(eleitor, candidato)) {

						System.out.println("\n");
						System.out.println(votacao.getApuracaoEleicao());

					} else {

						System.out.println("Atigiu o limite de votos");
						System.out.println(votacao.getApuracaoEleicao());
					}

				} else {

					System.out.println("Candidato não existe");

				}
			}

		} catch (AccessException e) {

			System.out.println("Erro de Acesso" + e.getMessage());

		} catch (RemoteException e) {

			System.out.println(e.getMessage());

		} catch (NotBoundException e) {

			System.out.println("Erro de Not Bound." + e.getMessage());

		} catch (Exception e) {

			System.out.println("Client: " + e.getMessage());

		}

	}

	private static Candidato verificarCandidato(int voto, List<Candidato> candidatos) {

		for (Candidato c : candidatos) {

			if (c.getNumero() == voto) {

				return c;

			}

		}

		return null;
	}

	private static void listarCandidatos(List<Candidato> candidatos) {

		System.out.println("************************************\n");
		System.out.println("*********    Candidatos    **********\n");

		for (Candidato c : candidatos) {

			System.out.println("[ Candidato: " +c.getNome() + " ] --------------------------- [ Número: " + c.getNumero() + " ]");

		}

		System.out.println("\n************************************");

	}

}
