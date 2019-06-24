package br.com.franciscochaves.util;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import br.com.franciscochaves.model.Candidato;
import br.com.franciscochaves.model.Eleitor;

public class VotacaoServant extends  UnicastRemoteObject implements Votacao,Serializable{

	private List<Candidato> candidatos;
	
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
		
		return false;
	}
	
	

}
