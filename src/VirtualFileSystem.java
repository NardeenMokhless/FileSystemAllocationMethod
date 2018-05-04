import java.util.ArrayList;

public class VirtualFileSystem {

    private int blockSize;
    private int freeBlocks;
    private int allocatedBlocks;
    private int []freeBlocksIndex;
    private ArrayList<Table>allocatedBlocksTable;
    private Block[] Blocks;
    private Directory root;
    private AllocationMethod method;

    public VirtualFileSystem() {
        blockSize = 0;
        freeBlocks = 0;
        allocatedBlocks = 0;
        method = new Extent();
        freeBlocksIndex = new int [blockSize];
        allocatedBlocksTable = new ArrayList<Table>();
        Blocks = new Block[blockSize];
        root = new Directory();
    }

    public VirtualFileSystem(int blockSize, String type) {
        this.blockSize = blockSize;
        this.freeBlocks = blockSize;
        this.allocatedBlocks = 0;
        Blocks = new Block[blockSize];
        freeBlocksIndex = new int [blockSize];
        allocatedBlocksTable = new ArrayList<Table>();

        for (int i = 0; i < blockSize; i++) {
            Blocks[i] = new Block(-1);
            freeBlocksIndex[i] = i;
        }
        this.root = new Directory("root");
        if(type.equals("Extent"))
            method = new Extent();
        else
            method = new Indexed();
    }

    public AllocationMethod getMethod() {
        return method;
    }

    public void setMethod(AllocationMethod method) {
        this.method = method;
    }

    public int[] getFreeBlocksIndex() {
        return freeBlocksIndex;
    }

    public void setFreeBlocksIndex(int[] freeBlocksIndex) {
        this.freeBlocksIndex = freeBlocksIndex;
    }

    public ArrayList<Table> getAllocatedBlocksTable() {
        return allocatedBlocksTable;
    }

    public void setAllocatedBlocksTable(ArrayList<Table> allocatedBlocksTable) {
        this.allocatedBlocksTable = allocatedBlocksTable;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public int getFreeBlocks() {
        return freeBlocks;
    }

    public void setFreeBlocks(int freeBlocks) {
        this.freeBlocks = freeBlocks;
    }

    public int getAllocatedBlocks() {
        return allocatedBlocks;
    }

    public void setAllocatedBlocks(int allocatedBlocks) {
        this.allocatedBlocks = allocatedBlocks;
    }

    public Block[] getBlocks() {
        return Blocks;
    }

    public void setBlocks(Block[] blocks) {
        Blocks = blocks;
    }

    public Directory getRoot() {
        return root;
    }

    public void setRoot(Directory root) {
        this.root = root;
    }

    // 1 path,file   0 path,no file    -1 no path

    public int CheckFileName(String arr) { // 1 path,file   0 path,no file    -1 no path

        String[] path = arr.split("/");

        Directory Temp = root;
        if (!path[0].equals("root")) {
            return -1;
        }
        for (int i = 1; i < path.length - 1; i++) {
            if (Temp.searchByDirectoryName(path[i]) != -1) {
                Temp = Temp.getSubDirectories().get(Temp.searchByDirectoryName(path[i]));
            } else {
                return -1;
            }
        }
        if (Temp.searchByFileName(path[path.length - 1]) != -1) {
            return 1;
        }

        return 0;
    }

    public int CheckFolderName(String arr) {
        String[] path = arr.split("/");
        Directory Temp = root;
        if (!path[0].equals("root")) {
            return -1;
        }
        for (int i = 1; i < path.length - 1; i++) {
            if (Temp.searchByDirectoryName(path[i]) != -1) {
                Temp = Temp.getSubDirectories().get(Temp.searchByDirectoryName(path[i]));
            } else {
                return -1;
            }
        }
        if (Temp.searchByDirectoryName(path[path.length - 1]) != -1) {
            return 1;
        }


        return 0;

    }

    public boolean CreateFile(String Path) { // 1 path,file   0 path,no file    -1 no path
        int index = Path.lastIndexOf(' ');
        String p = Path.substring(0, index);
        int fileSize = Integer.parseInt(Path.substring(index + 1, Path.length()));

        if (freeBlocks >= fileSize ) {
            if (CheckFileName(p) == 0) {
                String[] path = p.split("/");
                Directory Temp = root;
                for (int i = 1; i < path.length - 1; i++) {
                    if (Temp.searchByDirectoryName(path[i]) != -1) {
                        Temp = Temp.getSubDirectories().get(Temp.searchByDirectoryName(path[i]));
                    }
                }

                if (Temp.searchByFileName(path[path.length - 1]) != -1) {
                    return false;
                }
                if(!method.allocate(this,p,fileSize))
                    return false;
                Temp.getFiles().add(new file(p, fileSize));
                return true;
            }

        }
        return false;
    }

    public boolean CreateFolder(String arr) {

        if (CheckFolderName(arr) == 0) {
            String[] path = arr.split("/");

            Directory Temp = root;
            if (!path[0].equals("root")) {
                return false;
            }
            for (int i = 1; i < path.length - 1; i++) {
                if (Temp.searchByDirectoryName(path[i]) != -1) {
                    Temp = Temp.getSubDirectories().get(Temp.searchByDirectoryName(path[i]));
                } else {
                    return false;
                }
            }
            if (Temp.searchByDirectoryName(path[path.length - 1]) != -1) {
                return false;
            }

            Temp.getSubDirectories().add(new Directory(arr));
            return true;


        }

        return false;
    }

    public boolean DeleteFile(String path) {
        if(CheckFileName(path) == 1)
        {
            String[] arr = path.split("/");
            Directory Temp = root;
            for (int i = 1; i < arr.length - 1; i++) {
                if (Temp.searchByDirectoryName(arr[i]) != -1) {
                    Temp = Temp.getSubDirectories().get(Temp.searchByDirectoryName(arr[i]));
                }
            }
            int index = Temp.searchByFileName(arr[arr.length-1]);
            if(!method.deallocate(this,path))
                return false;

            Temp.getFiles().remove(index);
            return true;
        }
        return false;
    }

    public boolean DeleteFolder (String path){

        if(CheckFolderName(path) == 1)
        {
            String[] arr = path.split("/");
            Directory Temp = root;
            for (int i = 1; i < arr.length - 1; i++) {
                if (Temp.searchByDirectoryName(arr[i]) != -1) {
                    Temp = Temp.getSubDirectories().get(Temp.searchByDirectoryName(arr[i]));
                }
            }
            int index = Temp.searchByDirectoryName(arr[arr.length-1]);

            int fileSize = Temp.getSubDirectories().get(index).getFiles().size();
            int directorySize = Temp.getSubDirectories().get(index).getSubDirectories().size();

            for (int i = fileSize - 1; i >= 0; i--)
                DeleteFile(Temp.getSubDirectories().get(index).getFiles().get(i).getFilePath());
            for (int i = directorySize -1 ; i >= 0 ; i--)
                DeleteFolder(Temp.getSubDirectories().get(index).getSubDirectories().get(i).getDirectoryPath());

            Temp.getSubDirectories().remove(index);
            return true;
        }

        return false;
    }

    public void DisplayDiskStatus() {
        System.out.println("Total disk space :" + blockSize +" KB");
        System.out.println("Empty space :" + freeBlocks +" KB");
        System.out.println("Allocated  space :" + allocatedBlocks +" KB");
        System.out.println("Empty Blocks :");
        for (int i = 0; i < freeBlocksIndex.length; i++) {
            if (freeBlocksIndex[i] != -1) { System.out.print("Block" + freeBlocksIndex[i] + " "); }
        }
        System.out.println();
        System.out.println("Allocated Blocks :");
        for (int i = 0; i < Blocks.length; i++) {
            if (!Blocks[i].getStatus()) { System.out.print("Block" + i + " "); }
        }
        System.out.println();
    }

    public void printDisk(){
        for (int i = 0; i < blockSize ; i++) {
            System.out.println(Blocks[i].getStatus() + " "+ Blocks[i].getIndex());
        }
        for (int i = 0; i < allocatedBlocksTable.size() ; i++) {
            System.out.println(allocatedBlocksTable.get(i).getPath()+" "+ allocatedBlocksTable.get(i).getStart());
        }
    }
}
