package net.csthings.common.utils;

/**
 * Created on: Jul 28, 2016
 * @author Toluwanimi Salako
 * Last edited: Jul 28, 2016
 * @purpose - TODO
 */
public class Pair<L, R> extends org.apache.commons.lang3.tuple.Pair<L, R> {
    private L left;
    private R right;

    public Pair(L left, R right) {
        super();
        this.left = left;
        this.right = right;
    }

    @Override
    public L getLeft() {
        return left;
    }

    @Override
    public R getRight() {
        return right;
    }

    @Override
    public R setValue(R value) {
        this.right = value;
        return right;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((left == null) ? 0 : left.hashCode());
        result = prime * result + ((right == null) ? 0 : right.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        Pair<L, R> other = (Pair<L, R>) obj;
        if (left == null) {
            if (other.left != null) return false;
        } else if (!left.equals(other.left)) return false;
        if (right == null) {
            if (other.right != null) return false;
        } else if (!right.equals(other.right)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Pair [left=" + left + ", right=" + right + "]";
    }

}
