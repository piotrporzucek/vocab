package pl.egalit.vocab.shared;

import java.util.ArrayList;
import java.util.List;


public class ListWordsDto extends ArrayList<WordDto> implements HasCollection {

	private static final long serialVersionUID = -816256431175597648L;

	public ListWordsDto() {

	}

	public ListWordsDto(List<WordDto> list) {
		super(list);
	}

}
