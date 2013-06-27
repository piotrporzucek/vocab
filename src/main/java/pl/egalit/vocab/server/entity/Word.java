package pl.egalit.vocab.server.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Word extends DatastoreObject implements Serializable {

	private static final long serialVersionUID = 1L;
	@Parent
	@Load
	private Ref<WordsUnit> wordsUnit;

	@Index
	private long lastUpdateTime;

	@NotNull
	private String answer;
	@NotNull
	private String expression;
	@NotNull
	private String example;

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
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

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public Ref<WordsUnit> getWordsUnit() {
		return wordsUnit;
	}

	public void setWordsUnit(Ref<WordsUnit> wordsUnit) {
		this.wordsUnit = wordsUnit;
	}

}
