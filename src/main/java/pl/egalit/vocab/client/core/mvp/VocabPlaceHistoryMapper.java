package pl.egalit.vocab.client.core.mvp;

import pl.egalit.vocab.client.places.ChangeCoursePlace;
import pl.egalit.vocab.client.places.ChangeLectorPlace;
import pl.egalit.vocab.client.places.CoursePlace;
import pl.egalit.vocab.client.places.LoginPlace;
import pl.egalit.vocab.client.places.MyAccountPlace;
import pl.egalit.vocab.client.places.SearchCoursePlace;
import pl.egalit.vocab.client.places.SearchLectorPlace;
import pl.egalit.vocab.client.places.SettingsPlace;
import pl.egalit.vocab.client.places.StartPlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ SearchLectorPlace.Tokenizer.class,
		ChangeLectorPlace.Tokenizer.class, LoginPlace.Tokenizer.class,
		StartPlace.Tokenizer.class, CoursePlace.Tokenizer.class,
		SettingsPlace.Tokenizer.class, SearchCoursePlace.Tokenizer.class,
		ChangeCoursePlace.Tokenizer.class, MyAccountPlace.Tokenizer.class })
public interface VocabPlaceHistoryMapper extends PlaceHistoryMapper {

}
