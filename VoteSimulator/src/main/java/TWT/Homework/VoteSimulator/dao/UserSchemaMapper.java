package TWT.Homework.VoteSimulator.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import TWT.Homework.VoteSimulator.entity.UserSchema;

import java.util.List;

@Repository
@Mapper
public interface UserSchemaMapper {
    public List<UserSchema> getAllUser();
    public List<String> getAllUserName();
    public List<String> getUserCode(String name);
    public List<String> getManagerCode(String managerName);
    public List<Integer> getUserId(String name);
    public int addUser(String name, String code);
    public int updateUserCode(String name, String code, String newCode);
    public int updateUserName(String name, String code, String newName);
    public int updateUserNameCode(String name, String code, String newName, String newCode);
    public int deleteUser(String name, String code);
}
