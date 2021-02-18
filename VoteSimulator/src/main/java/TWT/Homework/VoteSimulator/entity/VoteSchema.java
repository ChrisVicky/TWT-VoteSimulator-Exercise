package TWT.Homework.VoteSimulator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteSchema {
    public QuestionSchema questionSchema;
    public List<ChoiceSchema> choiceList;
}
