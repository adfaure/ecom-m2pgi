package fr.ujf.m2pgi.database.DAO;
import fr.ujf.m2pgi.database.entities.Follow;
import java.util.List;

/**
 *
 */
public interface IFollowDAO extends IGeneriqueDAO<Follow> {
	public Follow findFollowbyCoupleId(Long followerID, Long followedID);

}