package latte.Internal;

import latte.Absyn.Type;

public class Null extends Type {
    public Null() {
    }
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        return o instanceof Null || o.equals(this);
    }

    @Override
    public <R, A> R accept(Visitor<R, A> v, A arg) {
        throw new RuntimeException("not implemented");
    }
}

