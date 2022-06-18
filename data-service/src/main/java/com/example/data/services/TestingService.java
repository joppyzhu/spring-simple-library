package com.example.data.services;

import com.example.data.dao.TestingDao;
import com.example.data.models.Testing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestingService {
    @Autowired
    TestingDao testingDao;

    public long run() {
        int bookCount = 10;
        for(int i = 1; i <= bookCount; i++) {
            //Optional<Testing> dataOpt = testingDao.findById(i);
            //Testing data = dataOpt.get();
            //data.setTitle("Book Updated " + i);
            Iterable<Testing> dataItr = testingDao.findAll();
            for (Testing dt : dataItr) {
                if (dt.getId() == i) {
                    dt.setTitle("Book Test1 " + i);
                    testingDao.save(dt);
                }
            }
        }
        return 1;
    }
}
