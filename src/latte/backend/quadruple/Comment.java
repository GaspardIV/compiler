package latte.backend.quadruple;

public class Comment extends Quadruple {
    private final String comment;

    public Comment(String comment) {
        super(null);
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "; " + comment;
    }
}
