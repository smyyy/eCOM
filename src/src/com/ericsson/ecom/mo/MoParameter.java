package com.ericsson.ecom.mo;

/**
 * Created with IntelliJ IDEA.
 * User: eranibl
 * Date: 2013-05-07
 * Time: 07:36
 * To change this template use File | Preferences | File Templates.
 */
public class MoParameter {

    public MoParameter(String name, Object[] values, int type) {
        this.name = name;
        this.values = values;
        this.type = type;
    }

    public MoParameter(MoParameter sourceMoParameter) {
        name = sourceMoParameter.getName();
        values = sourceMoParameter.getValues();
        type = sourceMoParameter.getType();
    }

    private String name;
    private Object[] values = new Object[0];

    private int type = MoAttribute.MoAttributeType_STRING;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNoOfValues() {
        return values.length;
    }

    public boolean isMultiValued() {
        return values.length > 1;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isValueSet() {
        return getNoOfValues() > 0;
    }

    public Object getValue() {
        if (isValueSet())
            return values[0];
        return
                null;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValue(Object value) {
        this.values = new Object[1];
        this.values[0] = value;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

}
