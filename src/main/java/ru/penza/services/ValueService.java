package ru.penza.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.penza.models.Value;
import ru.penza.repositories.ValueRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ValueService {

    @Autowired
    private ValueRepository valueRepository;


    public Map<Object, Map<Object, Object>> getAllValuesMap() {
        List<Object[]> vals = valueRepository.getAllValues();

        Map<Object, Map<Object, Object>> motors = new HashMap<>();
        Map<Object, Object> tools;
        Map<Object, Object> values;

        Long hereTimeStamp = System.currentTimeMillis();

        for (Object[] obj : vals) {
            String motor = (String) obj[0];
            String tool = (String) obj[1];
            Double val = (Double) obj[2];
            Long ts = Long.parseLong(obj[3].toString());

            tools = new HashMap<>();
            values = new HashMap<>();

            values.put("value", val);
            values.put("timestamp", ts);

            if (motors.get(motor) != null) {
                motors.get(motor).put(tool, values);
            } else {
                tools.put(tool, values);
                motors.put(motor, tools);
            }
        }
        return motors;
    }


    public Integer delValueByEngineId(Long id) {
        return valueRepository.deleteValue(id);
    }


    public Map<Long, Double> getLastVals(Long then, Long now, String motor, String tool) {
        List<Value> valsFromDB = valueRepository.getLastValues(then * 1000, now * 1000, motor, tool);
        Map<Long, Double> map = new HashMap<>();

        for (Value value : valsFromDB) {
            map.put(value.getTimestamp() / 1000, value.getValue());
        }

        return map;
    }

}



//        List<Map<Object, Object>> values = new ArrayList<>();
//        List<Value> valsFromDB = valueRepository.getLastValues(2000L, 1633132314L, motor, tool);
//            map = new HashMap<>();
//            map.put("timestamp", value.getTimestamp() / 1000);
//            map.put("value", value.getValue());
//            values.add(map);
