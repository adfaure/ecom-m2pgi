package fr.ujf.m2pgi.database.entities;

import fr.ujf.m2pgi.database.entities.Member;
import javax.persistence.*;


@Entity
@Table(name="follow")
public class Follow {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id")
	private Long id;
	
	@ManyToOne()
	@JoinColumn(name = "followerID", nullable = false)
	private Member follower;
	
	@ManyToOne()
	@JoinColumn(name = "followedID", nullable = false)
	private Member followed;
	
	public Follow(){}
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public Member getFollower(){
		return follower;
	}
	
	public void setFollower(Member follower){
		this.follower = follower;
	}
	
	public Member getFollowed(){
		return followed;
	}
	
	public void setFollowed(Member followed){
		this.followed = followed;
	}
}
