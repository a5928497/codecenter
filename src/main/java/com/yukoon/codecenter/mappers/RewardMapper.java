package com.yukoon.codecenter.mappers;

import com.yukoon.codecenter.entities.Reward;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RewardMapper {

    @Select("SELECT * FROM rewards ")
    public List<Reward> findAll();

    @Select("SELECT * FROM rewards WHERE id = #{id}")
    public Reward findById(Integer id);

    @Select("SELECT * FROM rewards WHERE reward_name LIKE #{reward_name}")
    public List<Reward> search(String reward_name);

    @Select("SELECT * FROM rewards WHERE status = 1")
    public List<Reward> findAllAlive();

    @Insert("INSERT INTO rewards (reward_name,status) VALUES (#{reward_name},1)")
    public void addReward(Reward reward);

    @Update("UPDATE rewards SET reward_name = #{reward_name} WHERE id =#{id}")
    public void updateReward(Reward reward);

    @Update("UPDATE rewards SET status = 1 WHERE id =#{id}")
    public void alive(Integer id);

    @Update("UPDATE rewards SET status = 2 WHERE id =#{id}")
    public void block(Integer id);

    @Delete("DELETE FROM rewards WHERE id = #{id}")
    public void delReward(Integer id);
}
