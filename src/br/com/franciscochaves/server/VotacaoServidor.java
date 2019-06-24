package br.com.franciscochaves.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import br.com.franciscochaves.model.Candidato;
import br.com.franciscochaves.util.Votacao;
import br.com.franciscochaves.util.VotacaoServant;

public class VotacaoServidor {

	public static void main(String[] args) {

		List<Candidato> candidatos;
		Registry registry;
		Votacao votacao;
		
		try {
			
			registry = LocateRegistry.createRegistry(8080);
			
			candidatos = new ArrayList<Candidato>();

			candidatos.add(new Candidato("Zé", 1234));
			candidatos.add(new Candidato("Francisco", 4321));
			candidatos.add(new Candidato("Maria", 1111));
			candidatos.add(new Candidato("Carla", 3333));
			candidatos.add(new Candidato("Caio", 7777));
			candidatos.add(new Candidato("Lula", 8888));
			candidatos.add(new Candidato("Sicrano", 9090));
			candidatos.add(new Candidato("Fulano", 1900));
			candidatos.add(new Candidato("Bosolnaro", 2222));
			candidatos.add(new Candidato("Haddad", 8181));

			votacao = new VotacaoServant(candidatos);

			registry.rebind("localhost/Votacao", votacao);
			
			System.out.println("Server ready");

		} catch (Exception e) {
			
			System.err.println("Error Server " + e.getMessage());
			
		}

	}

}
