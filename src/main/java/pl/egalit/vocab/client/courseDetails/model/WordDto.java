package pl.egalit.vocab.client.courseDetails.model;

import pl.egalit.vocab.client.requestfactory.WordProxy;

public class WordDto {

	private String answer;
	private String expression;
	private String example;

	public WordDto(final WordProxy proxy) {
		this.answer = proxy.getAnswer();
		this.example = proxy.getExample();
		this.expression = proxy.getExpression();
	}

	public WordDto() {
		// TODO Auto-generated constructor stub
	}

	public String getAnswer() {
		return answer;
	}

	public String getExpression() {
		return expression;
	}

	public String getExample() {
		return example;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setExample(String example) {
		this.example = example;
	}

}
