package com.sevenorcas.field.graph.wrapper;

import java.util.Hashtable;

public class Base implements GraphI {

    private Hashtable<String, String> fields;

    protected void encodeField(String key, Object value, StringBuffer sb){
        sb.append(encodeField(key, value, sb.length()>0));
    }

    protected String encodeField(String key, Object value){
        return encodeField(key, value, false);
    }

    protected String encodeField(String key, Object value, boolean delimiter){
        return (delimiter? DELIMIT_1 :"") + key + DELIMIT_2 + value;
    }

    protected void encodeList(Object value, StringBuffer sb){
        sb.append((sb.length()>0? DELIMIT_3 : "") + value);
    }

    protected void decode(String encoding){
        fields = new Hashtable<>();

        String[] sx = encoding.split(DELIMIT_1);
        for (int i=0; i<sx.length; i++){
            String[] sx1 = sx[i].split(DELIMIT_2);
            String k = sx1[0];
            String v = sx1[1];
            fields.put(k,v);
        }
    }

    protected <T> T getField (String key, T defaultValue) throws Exception{
        if (fields == null){
            throw new Exception("object not decoded");
        }
        if (fields.containsKey(key)){
            Object value = fields.get(key);
            if (defaultValue instanceof Integer){
                value = Integer.parseInt(value.toString());
            }
            else if (defaultValue instanceof Double){
                value = Double.parseDouble(value.toString());
            }
            return (T)value;
        }
        return defaultValue;
    }


}
