package br.com.franciscochaves.util;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import br.com.franciscochaves.model.Candidato;
import br.com.franciscochaves.model.Eleitor;;

public interface Votacao extends Remote{

	public List<Candidato> getCandidatos() throws RemoteException ;
	public boolean setVoto(Eleitor eleitor, Candidato candidato) throws RemoteException;
	public String getApuracaoEleicao() throws RemoteException;
	
}
