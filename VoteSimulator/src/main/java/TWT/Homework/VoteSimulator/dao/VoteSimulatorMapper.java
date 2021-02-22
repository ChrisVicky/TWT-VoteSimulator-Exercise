package TWT.Homework.VoteSimulator.dao;

import TWT.Homework.VoteSimulator.entity.AnswerSchema;
import TWT.Homework.VoteSimulator.entity.ChoiceSchema;
import TWT.Homework.VoteSimulator.entity.QuestionSchema;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VoteSimulatorMapper {
    public void addQuestion(String question, int userId);
    public void addChoice(String choice, int choiceId, int voteId);
    public void addAnswer(int voteId, int choiceId, int userId);

    public void deleteQuestion(int voteId);
    public void deleteChoice(int voteId);
    public void deleteAnswer(int voteId);

    public void updateChoiceTimes(String choice, int choiceId);


    public List<QuestionSchema> getAllQuestion();
    public List<ChoiceSchema> getAllChoice();
    public List<Integer> getMyVoteId(int userId);
    public List<Integer> getAllVoteId();
    public List<String> getQuestion(int voteId);
    public List<ChoiceSchema> getVoteChoice(int voteId);
    public int getVoteId(String question);
    public List<Integer> getUserId(int voteId, String question);
    public List<String> getChoice(int choiceId, int voteId);
    public List<AnswerSchema> existAnswer(int voteId, int userId);

    public void updateQuestion(String question, int voteId);
    public int getMaxChoiceId(int voteId);
    public int getChoiceId(String choice, int voteId);

}
