package br.com.franciscochaves.client;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import br.com.franciscochaves.model.Candidato;
import br.com.franciscochaves.util.Votacao;

public class VotacaoCliente {

	public static void main(String[] args) {

		Votacao votacao;
		List<Candidato> candidatos;
		Registry registry;

		try {

			registry = LocateRegistry.getRegistry(8080);
			//System.out.println("Encontrou o registry");

			votacao = (Votacao) registry.lookup("localhost/Votacao");

			candidatos = votacao.getCandidatos();

			listarCandidatos(candidatos);

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

	private static void listarCandidatos(List<Candidato> candidatos) {

		System.out.println("************************************\n");
		System.out.println("*********    Candidatos    **********\n");

		for (int i = 0; i < candidatos.size(); i++) {

			System.out.println(candidatos.get(i).getNome() +
					" --------------------------- " +
					candidatos.get(i).getNumero());

		}

		System.out.println("\n************************************");

	}

}
