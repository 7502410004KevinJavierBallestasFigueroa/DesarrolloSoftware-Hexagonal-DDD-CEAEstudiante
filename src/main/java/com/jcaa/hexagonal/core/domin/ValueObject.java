package com.jcaa.hexagonal.core.domin;

import java.util.List;
import java.util.Objects;

public abstract class ValueObject {
    
    protected static boolean equalOperator(ValueObject left, ValueObject right) {
        if (left == null ^ right == null) {
            return false;
        }
        return left == right || (left != null && left.equals(right));
    }
    
    protected static boolean notEqualOperator(ValueObject left, ValueObject right) {
        return !equalOperator(left, right);
    }
    
    protected abstract List<Object> getEqualityComponents();
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        
        ValueObject other = (ValueObject) obj;
        return Objects.equals(getEqualityComponents(), other.getEqualityComponents());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getEqualityComponents().toArray());
    }
}

