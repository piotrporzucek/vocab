package pl.egalit.vocab.shared;

import java.util.ArrayList;
import java.util.List;

public class ListSchoolsDto extends ArrayList<SchoolDto> implements
		HasCollection {

	private static final long serialVersionUID = -816256431175597648L;

	public ListSchoolsDto() {

	}

	public ListSchoolsDto(List<SchoolDto> list) {
		super(list);
	}

}
