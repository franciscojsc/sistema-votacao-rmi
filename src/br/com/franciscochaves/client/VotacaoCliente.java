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

			if (votacao.isEleitor(eleitor)) {

				candidatos = votacao.getCandidatos();

				listarCandidatos(candidatos);

				teclado = new Scanner(System.in);

				while (true) {

					int voto;

					while (true) {

						System.out.println("Digite o número do candidato para realizar sua votação:");

						String v = teclado.next();

						if (isNumero(v)) {

							voto = Integer.parseInt(v);
							break;

						} else {

							System.out.println("Somente número sem ponto ou vírgula!!!\n");

						}

					}

					candidato = verificarCandidato(voto, votacao.getCandidatos());

					if (candidato != null) {

						if (votacao.setVoto(eleitor, candidato)) {

							System.out.println("\nVoto realizado com sucesso!!!\n");
							System.out.println("Apuração dos votos:");
							System.out.println(votacao.getApuracaoEleicao());

							if (!votacao.isEleitor(eleitor)) {

								System.out.println("Você já realizou sua votação, atigiu o limite de votos!!!");
								break;

							}

						} else {

							System.err.println("Aconteceu algum problema!!! Tente novamnete.");

						}

					} else {

						System.out.println("\nCandidato não existe, tente novamente!!!\n");

					}
				}

			} else {

				System.out.println("Você já realizou sua votação, atigiu o limite de votos!!!");

			}

		} catch (AccessException e) {

			System.out.println("Erro de Acesso" + e.getMessage());

		} catch (RemoteException e) {

			System.err.println(e.getMessage());

		} catch (NotBoundException e) {

			System.err.println("Erro de Not Bound." + e.getMessage());

		} catch (Exception e) {

			System.err.println("Error - Client: " + e.getMessage());

		}

	}

	private static boolean isNumero(String v) {

		for (int i = 0; i < v.length(); i++) {
			if (!Character.isDigit(v.charAt(i))) {
				return false;
			}
		}

		return true;
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

			System.out.println(
					"[ Candidato: " + c.getNome() + " ] --------------------------- [ Número: " + c.getNumero() + " ]");

		}

		System.out.println("\n************************************");

	}

}
