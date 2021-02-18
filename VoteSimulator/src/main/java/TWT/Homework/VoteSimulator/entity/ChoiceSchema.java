package TWT.Homework.VoteSimulator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceSchema {
    public int voteId;
    public int choiceId;
    public String choice;
    public int times;
}
