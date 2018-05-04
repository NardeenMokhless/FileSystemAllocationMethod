import java.util.ArrayList;

public class Block {

    private int index;
    private static final int size = 1;
    private boolean status;
    private ArrayList<Integer> indices;

    public Block() {
        indices = new ArrayList<>();
    }

    public Block(int index ) {
        this.index = index;
        status = true;
        indices = new ArrayList<>();
    }
    public Block(int index, boolean status) {
        this.index = index;
        this.status = status;
        indices = new ArrayList<>();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public static int getSize() {
        return size;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<Integer> getIndices() {
        return indices;
    }

    public void setIndices(ArrayList<Integer> indices) {
        this.indices = indices;
    }

}
