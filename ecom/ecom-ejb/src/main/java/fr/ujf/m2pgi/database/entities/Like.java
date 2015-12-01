package fr.ujf.m2pgi.database.entities;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "likes")
public class Like implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@Id @ManyToOne @JoinColumn(name = "memberID")
  private Member member;

  @Id @ManyToOne @JoinColumn(name = "photoID")
  private Photo photo;

  public Member getMember() {
    return member;
  }

  public void setMember(Member member) {
    this.member = member;
  }

  public Photo getPhoto() {
    return photo;
  }

  public void setPhoto(Photo photo) {
    this.photo = photo;
  }
}
