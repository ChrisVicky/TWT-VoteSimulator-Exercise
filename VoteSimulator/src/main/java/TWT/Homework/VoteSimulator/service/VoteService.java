package TWT.Homework.VoteSimulator.service;

import TWT.Homework.VoteSimulator.utils.APIResponse;

import java.util.List;

public interface VoteService {
    public APIResponse getAllQuestion();
    public APIResponse getAllChoice();
    public APIResponse getAllVote();
    public APIResponse getUserVote(int userId);
    public APIResponse addVote(int userId, String question, List<String> choiceList);
    public APIResponse deleteVote(int userId, int voteId);
    public APIResponse deleteParticipation(int userId, int voteId);
    public APIResponse participateVote(int userId, int voteId, int choiceId);
}
