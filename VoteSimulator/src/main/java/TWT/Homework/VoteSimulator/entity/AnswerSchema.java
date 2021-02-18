package TWT.Homework.VoteSimulator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerSchema {
    public int userId;
    public int voteId;
    public int choiceId;
}
