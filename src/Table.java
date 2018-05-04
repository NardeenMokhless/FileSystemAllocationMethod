public class Table {
    private String path;
    private int start;

    public Table() {
        path = "";
        start = 0;
    }

    public Table(String path, int start) {
        this.path = path;
        this.start = start;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
