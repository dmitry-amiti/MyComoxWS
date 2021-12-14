package ru.penza.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.penza.forms.EngineForm;
import ru.penza.models.Engine;
import ru.penza.repositories.EngineRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EngineService {

    @Autowired
    private EngineRepository engineRepository;


    public List<String> getAllEnginesNames() {
        List<Engine> db_engines = engineRepository.findAll();
        List<String> engines = new ArrayList<>();
        for (Engine e : db_engines) {
            engines.add(e.getName());
        }
        return engines;
    }


    public String addEngine(EngineForm engineForm) {
        try {
            engineRepository.save(Engine.from(engineForm));
            return engineForm.getName() + " added to DB";
        } catch (Exception e) {
            return "0";
        }

    }

    public Optional<Engine> findEngineByName(String name) {
        return engineRepository.findOneByName(name);
    }



    public Integer delEngineById(Long id){
        return engineRepository.deleteEngine(id);
    }
}



