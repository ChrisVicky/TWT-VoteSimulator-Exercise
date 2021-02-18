package TWT.Homework.VoteSimulator.service;

import TWT.Homework.VoteSimulator.utils.APIResponse;

import java.util.List;

public interface VoteService {
    public APIResponse getAllQuestion();
    public APIResponse getAllChoice();
    public APIResponse getUserVote(String name, String code);
    public APIResponse addVote(String name, String code, String question, List<String> choiceList);
    public APIResponse deleteVote(String name, String code, int voteId);
}
