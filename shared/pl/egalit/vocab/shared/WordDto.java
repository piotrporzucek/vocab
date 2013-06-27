package pl.egalit.vocab.shared;

import java.io.Serializable;
import java.util.Date;

public class WordDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String answer;
	private String expression;
	private String example;
	private Date lastShownOn;
	private Date nextShownOn;
	private long courseId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Date getLastShownOn() {
		return lastShownOn;
	}

	public void setLastShownOn(Date lastShownOn) {
		this.lastShownOn = lastShownOn;
	}

	public Date getNextShownOn() {
		return nextShownOn;
	}

	public void setNextShownOn(Date nextShownOn) {
		this.nextShownOn = nextShownOn;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

}
