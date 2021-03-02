package TWT.Homework.VoteSimulator.service;

import TWT.Homework.VoteSimulator.utils.APIResponse;

import java.util.List;

public interface VoteService {
    public APIResponse getAllQuestion();
    public APIResponse getAllChoice();
    public APIResponse getAllVote();
    public APIResponse getResult(int questionId);
    public APIResponse getParticipation(int userId);
    public APIResponse getUserVote(int userId);
    public APIResponse addVote(int userId, String question, List<String> choiceList);
    public APIResponse deleteVote(int userId, int questionId);
    public APIResponse deleteParticipation(int userId, int questionId);
    public APIResponse participateVote(int userId, int questionId, int choiceId);
    public APIResponse reParticipate(int userId, int questionId, int choiceId);
}

