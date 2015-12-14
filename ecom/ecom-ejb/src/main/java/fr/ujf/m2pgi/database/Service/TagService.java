package fr.ujf.m2pgi.database.Service;

import fr.ujf.m2pgi.database.DTO.TagCountDTO;
import fr.ujf.m2pgi.database.DAO.ITagDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AZOUZI Marwen on 12/12/15.
 */
@Stateless
public class TagService implements ITagService {

  /**
   *
   */
  @Inject
  private ITagDAO tagDAO;

  /**
   *
   */
  @Override
  public List<TagCountDTO> getAllTags() {
    return tagDAO.getAllTags();
  }

  /**
   *
   */
  @Override
  public List<TagCountDTO> getTop10Tags() {
    return tagDAO.getTop10Tags();
  }

  /**
   *
   */
  @Override
  public List<TagCountDTO> getTrends() {
    return tagDAO.getTrends();
  }
}
