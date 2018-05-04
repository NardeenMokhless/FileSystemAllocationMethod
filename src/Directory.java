import java.util.ArrayList;

public class Directory {
    private String directoryPath;
    private String directoryName;
    private ArrayList<file> files;
    private ArrayList<Directory> subDirectories;
    private boolean deleted = false;

    public Directory() {
        this.directoryPath = "";
        directoryName = "";
        this.deleted = false;
        files = new ArrayList<file>();
        subDirectories = new ArrayList<Directory>();
    }

    public Directory(String directoryPath) {
        this.directoryPath = directoryPath;
        this.deleted = false;
        files = new ArrayList<file>();
        subDirectories = new ArrayList<Directory>();

        String[] arr = directoryPath.split("/");
        directoryName = arr[arr.length - 1];
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
        String[] arr = directoryPath.split("/");
        directoryName = arr[arr.length - 1];
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public ArrayList<file> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<file> files) {
        this.files = files;
    }

    public ArrayList<Directory> getSubDirectories() {
        return subDirectories;
    }

    public void setSubDirectories(ArrayList<Directory> subDirectories) {
        this.subDirectories = subDirectories;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int searchByDirectoryName(String name) {
        for (int i = 0; i < subDirectories.size(); i++) {
            if (subDirectories.get(i).directoryName.equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public int searchByFileName(String name) {
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).getFileName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public void BFS(Directory root) {
        Queue q = new Queue(1000);
        q.Add(root);
        String tab = " ";

        while (q.Num() > 0) {
            Directory Temp = q.Pop();
            System.out.println( "< " + Temp.getDirectoryName() + " >");
            for (int i = 0; i < Temp.getFiles().size(); i++) {
                System.out.println(tab +"- " + Temp.getFiles().get(i).getFileName());
            }
            for (int i = 0; i < Temp.getSubDirectories().size(); i++) {
                q.Add(Temp.getSubDirectories().get(i));
            }
        }
        q.Destroy();

    }

    public String s = "";
    public void printDirectory(Directory root ,String s) {
        System.out.println(s+"< "+root.getDirectoryName()+" >");
        s += "  ";
        for (int i = 0; i < root.getFiles().size() ; i++) {
            System.out.println(s +"* "+root.getFiles().get(i).getFileName()+".");
        }
        for (int i = 0; i < root.getSubDirectories().size(); i++) {
            printDirectory(root.getSubDirectories().get(i),s);
        }

    }
}
