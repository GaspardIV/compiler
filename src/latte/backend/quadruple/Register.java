package latte.backend.quadruple;

public class Register  {
    public String name;
    public Register(String name_) {
        name = name_;
    }

    @Override
    public String toString() {
        return "%" + name;
    }
}
