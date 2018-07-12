package com.yukoon.codecenter.services;

import com.yukoon.codecenter.entities.Page;
import com.yukoon.codecenter.entities.Reward;
import com.yukoon.codecenter.mappers.RewardMapper;
import com.yukoon.codecenter.utils.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RewardService {
    @Autowired
    private RewardMapper rewardMapper;

    //分页查询所有礼品
    @Transactional
    public Page findAll(Integer pageNo,Integer pageSize) {
        return PageableUtil.page(pageNo,pageSize,rewardMapper.findAll());
    }

    //根据id查询单个礼品
    @Transactional
    public Reward findById(Integer id) {
        return rewardMapper.findById(id);
    }

    //添加礼品
    @Transactional
    public void addReward(Reward reward) {
        rewardMapper.addReward(reward);
    }

    //礼品上架
    @Transactional
    public void alive(Integer id) {
        rewardMapper.alive(id);
    }

    //礼品下架
    @Transactional
    public void block(Integer id) {
        rewardMapper.block(id);
    }

    //更新礼品信息
    @Transactional
    public void update(Reward reward) {
        rewardMapper.updateReward(reward);
    }
}
