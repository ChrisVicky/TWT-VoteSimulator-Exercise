package TWT.Homework.VoteSimulator.dao;

import TWT.Homework.VoteSimulator.entity.ChoiceSchema;
import TWT.Homework.VoteSimulator.entity.QuestionSchema;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VoteSimulatorMapper {
    public List<QuestionSchema> getAllQuestion();
    public List<ChoiceSchema> getAllChoice();
    public List<Integer> getAllVoteId(int userId);
    public QuestionSchema getIndividualQuestion(int voteId);
    public List<ChoiceSchema> getIndividualVoteChoice(int voteId);
    public int getVoteIdThroughQuestion(String question, int userId);
    public int getUserIdThroughVoteId(int voteId);
    public int addQuestion(String question, int userId);
    public int addChoice(String choice, int choiceId, int voteId);
    public int deleteQuestion(int voteId);
    public int deleteChoice(int voteId);
}
