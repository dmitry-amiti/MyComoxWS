package ru.penza.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.penza.models.Tool;
import ru.penza.repositories.ToolRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ToolService {

    @Autowired
    private ToolRepository toolRepository;


    public Map<Object, Object> getAllCriticalsMap() {
        List<Tool> allTools = toolRepository.findAll();
        Map<Object, Object> dbtools = new HashMap<>();
        Map<Object, Object> value_unit;

        for (Tool tool : allTools) {
            value_unit = new HashMap<>();
            value_unit.put("value", tool.getCriticalValue());
            value_unit.put("unit", tool.getUnit());
            dbtools.put(tool.getName(), value_unit);
        }
        return dbtools;
    }



    public String updateTool(String name, Double value) {
        Integer result = toolRepository.updateCriticalByToolName(name, value);
        if (result > 0) {
            return name + " was updated to " + value;
        } else {
            return "Update error";
        }
    }
}
