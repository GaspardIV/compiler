package latte.Absyn;

public class Null extends Type {
  public int line_num, col_num, offset;
  public Null() { }

//  public <R,A> R accept(latte.Absyn.Type.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
      return o instanceof Null || o.equals(this);
  }

  @Override
  public String toString() {
    return "Null";
  }

  public int hashCode() {
    return 37;
  }

    @Override
    public <R, A> R accept(Visitor<R, A> v, A arg) {
//        return v.visit(this, arg);

        return null;
    }
}
