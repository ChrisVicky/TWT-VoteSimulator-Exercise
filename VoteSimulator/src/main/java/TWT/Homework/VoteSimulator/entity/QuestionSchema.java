package TWT.Homework.VoteSimulator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionSchema {
    public int voteId;
    public String question;
    public int userId;
}
