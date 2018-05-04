import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Xerox {
    public Xerox() {}

    public void SaveRoot(Directory root , PrintWriter p){
        p.println(root.getDirectoryPath() + " " + root.getFiles().size() +" " + root.getSubDirectories().size());

        for (int i = 0; i < root.getFiles().size() ; i++) {
            p.println(root.getFiles().get(i).getFilePath() +" "+ root.getFiles().size());
        }
        for (int i = 0; i < root.getSubDirectories().size(); i++) {
            SaveRoot(root.getSubDirectories().get(i),p);
        }

    }
    public void LoadRoot(Directory root ,Scanner sc) {
        root.setDirectoryPath(sc.next());
        int filesSize = sc.nextInt();
        int directoriesSize = sc.nextInt();

        for (int i = 0; i < filesSize ; i++) {
            String filePath = sc.next();
            int fileSize = sc.nextInt();
            file file = new file(filePath,fileSize);
            root.getFiles().add(file);
        }
        for (int i = 0; i < directoriesSize; i++) {
            root.getSubDirectories().add(new Directory());
            LoadRoot(root.getSubDirectories().get(i),sc);
        }
    }
    public void save(VirtualFileSystem fileSystem) {
        File file = new File("DiskStructure.txt");
        PrintWriter pf = null;
        try {
            pf = new PrintWriter(file);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        pf.println(fileSystem.getMethod());  //
        pf.println(fileSystem.getBlockSize());
        pf.println(fileSystem.getFreeBlocks());
        pf.println(fileSystem.getAllocatedBlocks());
        for (int i = 0; i <fileSystem.getBlockSize(); i++) {
            pf.print(fileSystem.getFreeBlocksIndex()[i] + " ");
        }
        pf.println();
        pf.println(fileSystem.getAllocatedBlocksTable().size());
        for (int i = 0; i <fileSystem.getAllocatedBlocksTable().size() ; i++) {
            pf.println(fileSystem.getAllocatedBlocksTable().get(i).getPath()+" "+fileSystem.getAllocatedBlocksTable().get(i).getStart());
        }

        for (int i = 0; i <fileSystem.getBlockSize(); i++) {
            pf.print(fileSystem.getBlocks()[i].getIndex()+" "+fileSystem.getBlocks()[i].getStatus());

            if(fileSystem.getBlocks()[i].getIndices().size() != 0)
                pf.print(" "+fileSystem.getBlocks()[i].getIndices().size()+" ");
            for (int j = 0; j < fileSystem.getBlocks()[i].getIndices().size(); j++)
            {
                if(j == fileSystem.getBlocks()[i].getIndices().size() - 1)
                    pf.print(fileSystem.getBlocks()[i].getIndices().get(j));
                else
                    pf.print(fileSystem.getBlocks()[i].getIndices().get(j)+" ");
            }
            pf.println();
        }
        SaveRoot(fileSystem.getRoot(),pf);
        pf.close();
    }
    public VirtualFileSystem load() {
        File file = new File("DiskStructure.txt");

        if(file.getTotalSpace() <= 0) return null;

        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String type = sc.nextLine();
        int blockSize = (sc.nextInt());
        VirtualFileSystem fileSystem = new VirtualFileSystem(blockSize,type);

        fileSystem.setFreeBlocks(sc.nextInt());
        fileSystem.setAllocatedBlocks(sc.nextInt());

        for (int i = 0; i < fileSystem.getBlockSize(); i++) {
            fileSystem.getFreeBlocksIndex()[i] = sc.nextInt();
        }
        int allocateSize = sc.nextInt();
        for (int i = 0; i < allocateSize; i++) {
            String path = (sc.next());
            int start = (sc.nextInt());
            Table tb = new Table(path,start);
            fileSystem.getAllocatedBlocksTable().add(tb);
        }

        for (int i = 0; i <fileSystem.getBlockSize() ; i++) {
            String line = sc.nextLine();
            String [] index = line.split(" ");
            fileSystem.getBlocks()[i].setIndex(sc.nextInt());
            fileSystem.getBlocks()[i].setStatus(sc.nextBoolean());

            if (index.length > 2)
            {
                for (int j = 3; j < index.length; j++) {
                    fileSystem.getBlocks()[i].getIndices().add(Integer.parseInt(index[j]));
                }
            }
        }

        LoadRoot(fileSystem.getRoot(),sc);
        return fileSystem;
    }

}
