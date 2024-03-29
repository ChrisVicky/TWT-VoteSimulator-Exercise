package TWT.Homework.VoteSimulator.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import TWT.Homework.VoteSimulator.entity.User;

import java.util.List;

@Repository
@Mapper
public interface UserSchemaMapper {
    public List<User> getAllUser();
    public List<String> getAllUserName();
    public List<String> getUserCode(String name);
    public List<String> getManagerCode(String managerName);
    public List<Integer> getUserId(String name);
    public List<String> getUserName(int userId);
    public int addUser(String name, String code);
    public int updateUserCode(String name, String newCode);
    public int updateUserName(String name, String newName);
    public int updateUserNameCode(String name, String newName, String newCode);
    public int deleteUser(String name, String code);
}
