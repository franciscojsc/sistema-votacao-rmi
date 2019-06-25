package br.com.franciscochaves.util;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import br.com.franciscochaves.model.Candidato;
import br.com.franciscochaves.model.Eleitor;
import br.com.franciscochaves.model.Voto;

public class VotacaoServant extends  UnicastRemoteObject implements Votacao,Serializable{

	private List<Candidato> candidatos;
	private List<Voto> votos = new ArrayList<Voto>();
	private int quantidadeMaximaVoto = 5;
	
	private static final long serialVersionUID = 1L;
	
	public VotacaoServant() throws RemoteException {
		super();
	}
	
	public VotacaoServant(List<Candidato> candidatos) throws RemoteException {
		super();
		this.candidatos = candidatos;
	}
	
	@Override
	public List<Candidato> getCandidatos() throws RemoteException {
		
		return this.candidatos;
	}
	
	@Override
	public boolean setVoto(Eleitor eleitor, Candidato candidato) throws RemoteException {
		
		if(isEleitor(eleitor)) {
			
			Voto voto = new Voto(eleitor, candidato);
			votos.add(voto);
			
			return true;
		}
		
		return false;
	}

	@Override
	public String getApuracaoEleicao() throws RemoteException {
		
		return contabilizarVoto(votos, candidatos);
		
	}
	
	@Override
	public boolean isEleitor(Eleitor eleitor) {
		
		int quantidadeDeVotos = contarVotoEleitor(votos, eleitor);
		
		if(quantidadeDeVotos < quantidadeMaximaVoto) {
			
			return true;
		}
		
		return false;
	}
	
	private int contarVotoEleitor(List<Voto> votos, Eleitor eleitor) {
		int contVotos = 0;

		for (Voto voto : votos) {
			if (voto.getEleito().getId().equals(eleitor.getId())) {
				contVotos++;
			}
		}
		return contVotos;
	}
	
	private String contabilizarVoto(List<Voto> votos, List<Candidato> candidatos) {

		StringBuilder apuracao = new StringBuilder();
		
		for (Candidato c : candidatos) {
			
			int cont = 0;
			
			for (Voto v : votos) {
				
				if (v.getCandidato().getNumero() == c.getNumero()) {
					
					cont++;
					
				}
				
			}
			
			apuracao.append("[ Candidato: " + c.getNome() + " ], [ Número:  " + c.getNumero() + " ], [ Votos: " + cont + " ]\n");
			
		}
		return apuracao.toString();

	}

	
}
