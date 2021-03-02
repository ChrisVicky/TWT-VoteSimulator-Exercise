package TWT.Homework.VoteSimulator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    public int userId;
    public int questionId;
    public int choiceId;
}
