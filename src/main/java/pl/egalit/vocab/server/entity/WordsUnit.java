package pl.egalit.vocab.server.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class WordsUnit extends DatastoreObject implements Serializable {

	private static final long serialVersionUID = 1L;
	@Parent
	@Load
	private Key<Course> course;

	@Index
	@Load
	private List<Ref<Word>> words;

	private String lector;

	@Index
	private Date addedDate;

	public String getLector() {
		return lector;
	}

	public void setLector(String lector) {
		this.lector = lector;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public List<Ref<Word>> getWords() {
		return words;
	}

	public void setWords(List<Ref<Word>> words) {
		this.words = words;
	}

	public Key<Course> getCourse() {
		return course;
	}

	public void setCourse(Key<Course> course) {
		this.course = course;
	}

}