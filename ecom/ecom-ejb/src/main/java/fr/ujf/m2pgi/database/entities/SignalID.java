package fr.ujf.m2pgi.database.entities;

import java.io.Serializable;
//import fr.ujf.m2pgi.database.entities.Photo;
//import fr.ujf.m2pgi.database.entities.Member;

public class SignalID implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Photo photo;
	private Member member;
	
	public SignalID() {}
	
	public SignalID(Photo photo, Member member){
		this.photo = photo;
		this.member = member;
	}
	
	public Long getMemberID(){
		return member.getMemberID();
	}
	
	public Long getPhotoID(){
		return photo.getPhotoID();
	}
	
	@Override
	public int hashCode() {
		int photoID = ((Long) photo.getPhotoID()).intValue();
		int memberID = ((Long) member.getMemberID()).intValue();
		//Méthode de couplage de Cantor
		//https://en.wikipedia.org/wiki/Pairing_function#Cantor_pairing_function
		return (memberID + photoID + 1)*(photoID + memberID)/2 + photoID;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof SignalID){
			SignalID signalID = (SignalID) obj;
			return this.hashCode() == signalID.hashCode();
		}
		return false;
	}
	
}