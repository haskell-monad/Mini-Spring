package spring.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadoop on 2017-12-30.
 */
public class PropertyValues {
    private final List<PropertyValue> propertyValueList = new ArrayList<>();
    public void addPropertyValue(PropertyValue propertyValue){
        this.propertyValueList.add(propertyValue);
    }
    public List<PropertyValue> getPropertyValueList() {
        return propertyValueList;
    }
}
