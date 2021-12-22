package ru.penza.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.penza.models.Value;
import ru.penza.repositories.ValueRepository;

import java.util.ArrayList;
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

        Long hereTimeStamp = System.currentTimeMillis() / 1000;
        long hereTS = System.currentTimeMillis();
        System.out.println("herTS: " + hereTS);

        for (Object[] obj : vals) {
            String motor = (String) obj[0];
            String tool = (String) obj[1];
            Double val;
            Long ts = Long.parseLong(obj[3].toString()) / 1000;

            System.out.println("getTS: " + Long.parseLong(obj[3].toString()));

            if (ts.equals(hereTimeStamp) || (ts == hereTimeStamp - 1)) {
                val = (Double) obj[2];

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
        }
        System.out.println();
        return motors;
    }


    public Integer delValueByEngineId(Long id) {
        return valueRepository.deleteValue(id);
    }


    public List<Map> getLastVals(Long delta, String motor, String tool) {
        long now = System.currentTimeMillis() / 1000;     // сначала делим, чтобы округлить
        long then = now - delta;
//        System.out.println("then: " + then + "    now: " + now);

        List<Value> valuesFromDB = valueRepository.getLastValues(then * 1000, now * 1000, motor, tool);
        Map<Long, Double> map = new HashMap<>();
        Map<Object, Object> temp_map;
        List<Map> list = new ArrayList<>();

        for (Value value : valuesFromDB) {
            map.put(value.getTimestamp() / 1000, value.getValue());
        }
//        System.out.println(map.toString());

        for (Long i = then; i < now; i++) {
            temp_map = new HashMap<>();
            if (map.containsKey(i)) {
                temp_map.put("ts", i);
                temp_map.put("value", map.get(i));
            } else {
                temp_map.put("ts", i);
                temp_map.put("value", 0);
            }
            list.add(temp_map);
        }

        return list;
    }


//    public Map<Long, Double> getLastVals(Long then, Long now, String motor, String tool) {
//        List<Value> valsFromDB = valueRepository.getLastValues(then * 1000, now * 1000, motor, tool);
//        Map<Long, Double> map = new HashMap<>();
//
//        for (Value value : valsFromDB) {
//            map.put(value.getTimestamp() / 1000, value.getValue());
//        }
//
//        return map;
//    }


}


//        List<Map<Object, Object>> values = new ArrayList<>();
//        List<Value> valsFromDB = valueRepository.getLastValues(2000L, 1633132314L, motor, tool);
//            map = new HashMap<>();
//            map.put("timestamp", value.getTimestamp() / 1000);
//            map.put("value", value.getValue());
//            values.add(map);
