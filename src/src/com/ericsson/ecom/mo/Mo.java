package com.ericsson.ecom.mo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: eranibl
 * Date: 2013-05-07
 * Time: 07:36
 * To change this template use File | Preferences | File Templates.
 */
public class Mo {

    @Override
    public String toString() {
        return "Mo [getDn()=" + getDn() + "]";
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((className == null) ? 0 : className.hashCode());
        return result;
    }


    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Mo other = (Mo) obj;
        return getDn().equals(other);
    }


    Mo() {
        // nothing
    }

    Mo(Mo toClone) {
        parentMo = toClone.getParentMo();
        className = toClone.getClassName();
        attributes = copyAttributes(toClone);
    }

    public Mo getParentMo() {
        return parentMo;
    }

    public void setParentMo(Mo parentMo) {
        this.parentMo = parentMo;
    }

    public static Mo createChild(Mo parentMo, String className,
                                 String keyAttributeName, String keyAttributeValue) {
        if (keyAttributeName==null || keyAttributeName.equals("")) {
            System.out.println("parentMo.getDn()="+parentMo.getDn());
        }
        Mo mo = new Mo();
        mo.setParentMo(parentMo);
        mo.setClassName(className);
        MoAttribute key = new MoAttribute();
        key.setName(keyAttributeName);
        key.setValue(keyAttributeValue);
        key.setType(MoAttribute.MoAttributeType_STRING);
        key.setKey(true);
        mo.setAttribute(key);
        return mo;
    }

    private Mo parentMo;
    private String className = "";

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    private Map<String, MoAttribute> attributes = new HashMap<String, MoAttribute>();

    public String getDn() {
        return getDn(false);
    }

    public String getDn(boolean relax3GPP) {
        if (parentMo == null)
            return getMyRdn(relax3GPP);
        return parentMo.getDn(relax3GPP) + "," + getMyRdn(relax3GPP);
    }

    private String getMyRdn(boolean relax3GPP) {
//		return className + "=" + getKeyAttribute().getValue();
        if (getKeyAttribute().getName().equalsIgnoreCase(className + "Id") || relax3GPP)
            return className + "=" + getKeyAttribute().getValue();
        else
            return className + "." + getKeyAttribute().getName() + "="
                    + getKeyAttribute().getValue();
    }

    Map<String, MoAttribute> getAttributes() {
        return attributes;
    }

    MoAttribute getAttribute(String name) {
        Iterator<String> iter = getAttributes().keySet().iterator();
        while (iter.hasNext()) {
            String moAttrName = iter.next();
            MoAttribute moAttr = getAttributes().get(moAttrName);
            if (moAttr.getName().equals(name))
                return moAttr;
        }
        return null;
    }

    MoAttribute getKeyAttribute() {
        Iterator<String> iter = getAttributes().keySet().iterator();
        while (iter.hasNext()) {
            String moAttrName = iter.next();
            MoAttribute moAttr = getAttributes().get(moAttrName);
            if (moAttr.isKey())
                return moAttr;
        }
        return null;
    }

    void setAttribute(MoAttribute value) {
        attributes.put(value.getName(), value);
    }

    // yes clone() should probably have been overridden, but this is good enough
    // main thing is to avoid a shallow copy
    private Map<String, MoAttribute> copyAttributes(Mo mo) {
        Map<String, MoAttribute> copiedAttributes = new HashMap<String, MoAttribute>();
        Iterator<String> iter = mo.getAttributes().keySet().iterator();
        while (iter.hasNext()) {
            String attributeName = iter.next();
            MoAttribute fromMoAttr = mo.getAttributes().get(attributeName);
            MoAttribute toMoAttr = new MoAttribute(fromMoAttr);
            copiedAttributes.put(attributeName, toMoAttr);
        }
        return copiedAttributes;
    }


    /**
     * Converts "Me=1,X.port=17,Y.qwe=321,Z=a" to "Me=1,X=17,Y=321,Z=a" etc.
     * @param dn
     * @return
     */
    public static String relax3GPPDn(String dn) {
        return dn; //.replaceAll("\\.+[a-zA-Z0-9_]+=", "=");
    }

}

