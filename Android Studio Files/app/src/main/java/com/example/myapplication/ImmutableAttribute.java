package com.example.myapplication;

/**
 * Class used for list items within the club manager edit event fragment
 * This class is used for mutable attributes meaning ones which can be changed
 *
 * @author Linden Sheehy
 * @version 1.0
 */
public class ImmutableAttribute {

    private final String attributeType;
    private final String attributeValue;

    ImmutableAttribute(String attributeType, String attributeValue) {
        this.attributeType = attributeType;
        this.attributeValue = attributeValue;
    }

    public String getType() {
        return this.attributeType;
    }
    public String getValue() {
        return this.attributeValue;
    }
}
