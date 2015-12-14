package fr.ujf.m2pgi.database.DAO;

import fr.ujf.m2pgi.database.entities.Tag;
import fr.ujf.m2pgi.database.DTO.TagCountDTO;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Query;

public class TagDAOImpl extends GeneriqueDAOImpl<Tag> implements ITagDAO {

  public Tag getByString(String token) {
    Query query = entityManager.createQuery("select t FROM Tag t WHERE t.name=:name");
    query.setParameter("name", token);
    List<Tag> tags = query.getResultList();
    if (tags != null && tags.size() == 1) {
      Tag tag = tags.get(0);
      return tag;
    }
    return null;
  }

  public List<TagCountDTO> getAllTags() {
    String string = "SELECT NEW fr.ujf.m2pgi.database.DTO.TagCountDTO(t.id, t.name, COUNT(t.id) as cnt) "+
    "FROM Tags ts LEFT JOIN ts.tag t GROUP BY t.id ORDER BY cnt DESC";//
    Query query = entityManager.createQuery(string, TagCountDTO.class);
    return query.getResultList();
  }

  public List<TagCountDTO> getTop10Tags() {
    String string = "SELECT NEW fr.ujf.m2pgi.database.DTO.TagCountDTO(t.id, t.name, COUNT(t.id) as cnt) "+
    "FROM Tags ts LEFT JOIN ts.tag t GROUP BY t.id ORDER BY cnt DESC";//
    Query query = entityManager.createQuery(string, TagCountDTO.class);
    query.setMaxResults(10);
    return query.getResultList();
  }

  public List<TagCountDTO> getTrends() {
    String string = "SELECT NEW fr.ujf.m2pgi.database.DTO.TagCountDTO(t.id, t.name, COUNT(t.id) as cnt) "+
    "FROM Tags ts LEFT JOIN ts.tag t WHERE ts.tagged > :date GROUP BY t.id ORDER BY cnt DESC";//
    Query query = entityManager.createQuery(string, TagCountDTO.class);
    query.setParameter("date", new Date(System.currentTimeMillis() - 30*24*60*60*1000));
    query.setMaxResults(10);
    return query.getResultList();
  }

  public List<Tag> getTags(List<String> tokens) {
    List<Tag> tags = new ArrayList<Tag>();
    for (String token : tokens) {
      token = token.toLowerCase();
      Tag tag = getByString(token);
      if (tag != null) {
        tags.add(tag);
      } else {
        tag = new Tag();
        tag.setName(token);
        tags.add(create(tag));
      }
    }
    return tags;
  }
}
