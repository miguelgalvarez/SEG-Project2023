package com.example.myapplication;

import android.view.View;

/**
 * Class used for list items within the club manager edit event fragment
 * This class is used for mutable attributes meaning ones which can be changed
 *
 * @author Linden Sheehy
 * @version 1.0
 */
public class MutableAttribute {

    private final String attributeType;
    private String attributeValue;
    public MutableAttributeList.ViewHolder viewHolder;

    MutableAttribute(String attributeType, String attributeValue) {
        this.attributeType = attributeType;
        this.attributeValue = attributeValue;
    }

    public void setValue(String value) {
        this.attributeValue = value;
    }

    public void setItemView(MutableAttributeList.ViewHolder view) {
        this.viewHolder = view;
    }

    public String getType() {
        return this.attributeType;
    }
    public String getValue() {
        return this.attributeValue;
    }
}
