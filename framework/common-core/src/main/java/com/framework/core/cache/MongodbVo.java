package com.framework.core.cache;


/**
 * 缓存操作对象
 * 
 * @Class Name CacheVo
 * @Author guowenqin
 * @Create In 2014年11月11日
 */
public class MongodbVo implements CacheVo{

    private int type;

    private String key;

    private String field;

    private String value;
    
    private Object valueObject;
    

    public Object getValueObject() {
		return valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}

	public MongodbVo() {
    }

    @Deprecated
    public MongodbVo(int type, String key) {
        this.type = type;
        this.key = key;
    }

    public MongodbVo(int type, String key, String field) {
        this.type = type;
        this.key = key;
        this.field = field;
    }

    public MongodbVo(int type, String key, String field, String value) {
        this.type = type;
        this.key = key;
        this.field = field;
        this.value = value;
    }
    
    public MongodbVo(int type, Object valueObject) {
        this.type = type;
        this.valueObject = valueObject;
    }    

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
