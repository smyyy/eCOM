package com.ericsson.ecom.mo;

/**
 * Created with IntelliJ IDEA.
 * User: eranibl
 * Date: 2013-05-07
 * Time: 07:37
 * To change this template use File | Preferences | File Templates.
 */
public class MoAttribute {
    public static final int MoAttributeType_INT8 = 1;
    public static final int MoAttributeType_INT16 = 2;
    public static final int MoAttributeType_INT32 = 3;
    public static final int MoAttributeType_INT64 = 4;
    public static final int MoAttributeType_UINT8 = 5;
    public static final int MoAttributeType_UINT16 = 6;
    public static final int MoAttributeType_UINT32 = 7;
    public static final int MoAttributeType_UINT64 = 8;
    public static final int MoAttributeType_STRING = 9;
    public static final int MoAttributeType_BOOL = 10;
    public static final int MoAttributeType_REFERENCE = 11;
    public static final int MoAttributeType_ENUM = 12;
    public static final int MoAttributeType_DERIVED = 13;
    public static final int MoAttributeType_STRUCT = 14;
    public static final int MoAttributeType_VOID = 15;

    public MoAttribute() {

    }

    public MoAttribute(MoAttribute sourceMoAttribute) {
        name = sourceMoAttribute.getName();
        values = sourceMoAttribute.getValues();
        type = sourceMoAttribute.getType();
        isReadOnly = sourceMoAttribute.isReadOnly();
        isMandatory = sourceMoAttribute.isMandatory();
        isKey = sourceMoAttribute.isKey();
    }

    public MoAttribute(String attrName, int attrType, Object[] attrValue) {
        name = attrName;
        type = attrType;
        values = attrValue;
    } // MoAttribute

    private String name;
    private Object[] values = new Object[0];

    private int type = MoAttributeType_STRING;

    private boolean isReadOnly = false;
    private boolean isKey = false;

    private boolean isMandatory = false;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }

    public boolean isKey() {
        return isKey;
    }

    public void setKey(boolean isKey) {
        this.isKey = isKey;
    }

    public int getNoOfValues() {
        if ( values == null ) {
            return 0;
        } else {
            return values.length;
        }
    }

    public boolean isMultiValued() {
        return values.length > 1;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
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

