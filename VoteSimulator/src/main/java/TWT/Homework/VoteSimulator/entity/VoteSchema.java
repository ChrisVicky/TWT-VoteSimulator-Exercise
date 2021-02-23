package TWT.Homework.VoteSimulator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteSchema {
    public int voteId;
    public String question;
    public List<ChoiceSchema> choiceList;
    public String userName;
    public int userId;
}
