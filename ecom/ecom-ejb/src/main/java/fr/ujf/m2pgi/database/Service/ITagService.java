package fr.ujf.m2pgi.database.Service;

import fr.ujf.m2pgi.database.DTO.TagCountDTO;

import java.util.List;

/**
 * Created by AZOUZI Marwen on 12/12/15.
 */
public interface ITagService {

  List<TagCountDTO> getAllTags();

	List<TagCountDTO> getTop10Tags();

	List<TagCountDTO> getTrends();

}
