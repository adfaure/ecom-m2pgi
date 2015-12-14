package fr.ujf.m2pgi.database.DAO;

import java.util.List;
import javax.ejb.Local;

import fr.ujf.m2pgi.database.entities.Tag;
import fr.ujf.m2pgi.database.DTO.TagCountDTO;

@Local
public interface ITagDAO extends IGeneriqueDAO<Tag> {

	Tag getByString(String token);

	List<TagCountDTO> getAllTags();
	List<TagCountDTO> getTop10Tags();
	List<TagCountDTO> getTrends();

	List<Tag> getTags(List<String> tokens);
}
