package latte.backend.quadruple;

import latte.Absyn.Int;
import latte.Absyn.Type;

import java.util.Objects;

public class ConstValue {
    Type type;
    String value;

    public ConstValue(int value) {
        type = new Int();
        this.value = Integer.toString(value);
    }
    public ConstValue(boolean value) {
        type = new latte.Absyn.Bool();
        this.value = Boolean.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConstValue that = (ConstValue) o;
        return Objects.equals(type, that.type) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    @Override
    public String toString() {
        return value;
    }

    public int getInt() {
        return Integer.parseInt(value);
    }

    public boolean getBool() {
        return Boolean.parseBoolean(value);
    }
}
