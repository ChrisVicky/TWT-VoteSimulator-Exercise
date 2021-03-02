package TWT.Homework.VoteSimulator.dao;

import TWT.Homework.VoteSimulator.entity.Answer;
import TWT.Homework.VoteSimulator.entity.Choice;
import TWT.Homework.VoteSimulator.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VoteSimulatorMapper {
    public void addQuestion(String question, int userId);
    public void addChoice(String choice, int choiceId, int questionId);
    public void addAnswer(int questionId, int choiceId, int userId);

    public void deleteQuestion(int questionId);
    public void deleteOneAnswer(int questionId, int choiceId);
    public void updateChoiceTimes(int questionId, int choiceId);



    public List<Question> getAllQuestion();
    public List<Choice> getAllChoice();
    public List<Integer> getMyQuestionId(int userId);
    public List<Integer> getAllQuestionId();
    public List<String> getQuestion(int questionId);
    public List<Choice> getVoteChoice(int questionId);
    public String getVoteQuestion(int questionId);
    public int getQuestionId(String question);
    public List<Integer> getUserId(int questionId, String question);
    public List<String> getChoice(int choiceId, int questionId);
    public List<Answer> existAnswer(int questionId, int userId);
    public int getChoiceId(int userId, int questionId);
    public List<Integer> getMyParticipatedQuestionId(int userId);
    public int getExistence(int questionId);
    public int getChoiceTimes(int questionId, int choiceId);
}
