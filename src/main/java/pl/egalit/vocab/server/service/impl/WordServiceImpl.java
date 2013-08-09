package pl.egalit.vocab.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import pl.egalit.vocab.server.controller.TaskWorker;
import pl.egalit.vocab.server.dao.MulticastDao;
import pl.egalit.vocab.server.dao.RegistrationInfoDao;
import pl.egalit.vocab.server.dao.WordDao;
import pl.egalit.vocab.server.dao.WordsUnitDao;
import pl.egalit.vocab.server.entity.Course;
import pl.egalit.vocab.server.entity.Multicast;
import pl.egalit.vocab.server.entity.RegistrationInfo;
import pl.egalit.vocab.server.entity.School;
import pl.egalit.vocab.server.entity.Word;
import pl.egalit.vocab.server.entity.WordsUnit;
import pl.egalit.vocab.server.security.GwtMethod;
import pl.egalit.vocab.server.security.VocabUser;
import pl.egalit.vocab.server.service.WordService;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

@Service
public class WordServiceImpl extends AbstractService implements WordService {

	@Autowired
	private WordDao wordDao;

	@Autowired
	private WordsUnitDao wordsUnitDao;

	@Autowired
	private MulticastDao multicastDao;

	@Autowired
	private RegistrationInfoDao registrationInfoDao;

	private final Queue queue = QueueFactory.getDefaultQueue();

	@Override
	@GwtMethod
	@Secured("ROLE_USER")
	public void saveAndSend(List<Word> words, long courseId) {
		VocabUser vocabUser = getVocabUser();
		Ref<WordsUnit> wordsUnitRef = wordsUnitDao.saveWordsUnit(
				vocabUser.getSchoolId(), courseId, vocabUser.getUsername());
		wordDao.saveAll(words, wordsUnitRef);
		List<String> regIds = findRegIds(vocabUser.getSchoolKey());
		Multicast multicast = new Multicast();
		multicast.setRegIds(regIds);
		multicast.setId(wordsUnitRef.getKey().getId());
		multicast.setCourseKey(Key.create(vocabUser.getSchoolKey(),
				Course.class, courseId));
		multicastDao.save(multicast);
		TaskOptions taskOptions = TaskOptions.Builder.withUrl("/task/send")
				.param(TaskWorker.PARAMETER_MULTICAST, "" + multicast.getId())
				.method(Method.GET);
		queue.add(taskOptions);

	}

	private List<String> findRegIds(Key<School> schoolKey) {
		List<String> result = Lists.newArrayList();
		List<RegistrationInfo> registrationInfos = registrationInfoDao
				.listByProperty("schoolId", schoolKey.getId());
		for (RegistrationInfo r : registrationInfos) {
			result.add(r.getDeviceRegistrationId());
		}
		return result;
	}

	@Override
	@GwtMethod
	@Secured("ROLE_USER")
	public List<Word> getWordsForWordsUnit(long courseId, long wordsUnitId) {
		VocabUser vocabUser = getVocabUser();

		return wordDao.getWordsForWordsUnit(vocabUser.getSchoolId(), courseId,
				wordsUnitId);
	}

}
