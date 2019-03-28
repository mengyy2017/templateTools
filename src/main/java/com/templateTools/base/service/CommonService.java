package com.templateTools.base.service;

import com.templateTools.base.entity.BaseEntity;
import com.templateTools.base.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;
import java.util.List;

public abstract class CommonService<T extends BaseEntity> {

    @Autowired
    protected CommonMapper<T> commonMapper;

    public T selectByPrimaryKey(String primaryKey){
        return commonMapper.selectByPrimaryKey(primaryKey);
    }

    public T selectOne(T t){
        return commonMapper.selectOne(t);
    }

    public T selectOneByExample(Example example){
        return commonMapper.selectOneByExample(example);
    }

    public List<T> select(T t){
        return commonMapper.select(t);
    }

    public List<T> selectByExample(Example example) {
        return commonMapper.selectByExample(example);
    }

    public List<T> selectAll(){
        return commonMapper.selectAll();
    }

    public int insert(T t){
        return commonMapper.insert(t);
    }

    public int insertSelective(T t){
        return commonMapper.insertSelective(t);
    }

    public int insertList(List<T> list){
        return commonMapper.insertList(list);
    }

    public int updateByPrimaryKey(T t){
        return commonMapper.updateByPrimaryKey(t);
    }

    public int updateByPrimaryKeySelective(T t){
        return commonMapper.updateByPrimaryKeySelective(t);
    }

    public int updateByExample(T t, Example example){
        return commonMapper.updateByExample(t, example);
    }

    public int updateByExampleSelective(T t, Example example){
        return commonMapper.updateByExampleSelective(t, example);
    }

    public int deleteByPrimaryKey(String primaryKey){
        return commonMapper.deleteByPrimaryKey(primaryKey);
    }

    public int delete(T t){
        return commonMapper.delete(t);
    }

    public int delete(Example example){
        return commonMapper.deleteByExample(example);
    }

    public int updateOrSaveSelective(T t) {
        if (t.getId() == null)
            return insert(t);
        else
            return updateByPrimaryKeySelective(t);
    }

}
