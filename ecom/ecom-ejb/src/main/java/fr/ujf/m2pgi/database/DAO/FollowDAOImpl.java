package fr.ujf.m2pgi.database.DAO;
import java.util.List;

import fr.ujf.m2pgi.database.entities.Follow;
import javax.persistence.Query;


public class FollowDAOImpl extends GeneriqueDAOImpl<Follow> implements IFollowDAO {
	
	public Follow findFollowbyCoupleId(Long followerID, Long followedID) {
		Query query = entityManager.createQuery("select f FROM Follow f WHERE f.follower.memberID=:followerID and f.followed.memberID=:followedID");
		query.setParameter("followerID", followerID);
		query.setParameter("followedID", followedID);
		List<Follow> follows = query.getResultList();
		if (follows != null && follows.size() == 1) {
			Follow follow = follows.get(0);
			return follow ;
		}
		return null;
	}
}
