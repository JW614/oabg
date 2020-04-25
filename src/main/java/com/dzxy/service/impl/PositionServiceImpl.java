package com.dzxy.service.impl;

import com.dzxy.dao.PositionDao;
import com.dzxy.pojo.Position;
import com.dzxy.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PositionServiceImpl implements PositionService {
    @Autowired
    private PositionDao positionDao;

    public int add(Position position) {

        return positionDao.save(position);
    }

    public List<Position> findAll() {

        return this.positionDao.findAll();
    }

    public int delete(int posid) {
        return this.positionDao.delete(posid);
    }

    public Position findById(int posid) {

        return this.positionDao.findById(posid);
    }

    public int update(Position position) {

        return this.positionDao.update(position);
    }

}
