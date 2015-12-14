package fr.ujf.m2pgi.database.entities;

import java.util.Collection;

import javax.persistence.*;

import java.io.Serializable;

/**
 *
 * @author AZOUZI Marwen
 *
 */
@Entity
@Table(name = "tag")
public class Tag implements Serializable {

  @Id
  @Column(name="id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name="name", nullable=false)
	private String name;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
  private Collection<Photo> photos;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Collection<Photo> getPhotos() {
    return photos;
  }

  public void setPhotos(Collection<Photo> photos) {
    this.photos = photos;
  }
}
