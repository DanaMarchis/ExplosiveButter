package iss.networking.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bitten Apple on 07-Jun-17.
 */
public class File_DTO implements Serializable {

    private Map<Integer, byte[]> map = new HashMap<>();
    private String filepath;

    public File_DTO() {
    }

    public void put(byte[] bytes) {
        Integer max = 0;
        for (Integer key : map.keySet()) {
            if (key > max) {
                max = key;
            }
        }
        max++;
        map.put(max, bytes);
    }

    public Map<Integer, byte[]> getDictionary() {
        return map;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
