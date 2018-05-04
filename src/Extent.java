import static java.lang.Math.ceil;

public class Extent extends AllocationMethod {
    private static int extentSize = 2;

    boolean allocate(VirtualFileSystem disk, String path, int size)
    {
        //check for available extents
        int numOfExtends = (int) ceil((double)size /extentSize);
        int counter = 0, sz = 0;
        int []blocks = new int [numOfExtends*extentSize];

        for(int i=0;i<disk.getFreeBlocksIndex().length;i+=extentSize) {
            if (disk.getFreeBlocksIndex()[i] == disk.getFreeBlocksIndex()[i + 1] + 1
                    || disk.getFreeBlocksIndex()[i] + 1 == disk.getFreeBlocksIndex()[i + 1]) {
                blocks[sz++] = disk.getFreeBlocksIndex()[i];
                blocks[sz++] = disk.getFreeBlocksIndex()[i+1];
                counter++;
                if(counter == numOfExtends)
                    i = disk.getFreeBlocksIndex().length;
            }
        }
        if(counter < numOfExtends)
            return false;

        //remove free blocks
        for (int i = 0; i < blocks.length; i++)
            disk.getFreeBlocksIndex()[blocks[i]] = -1;

        disk.setFreeBlocks(disk.getFreeBlocks() - blocks.length);

        //allocate new blocks
        disk.setAllocatedBlocks(disk.getAllocatedBlocks() + blocks.length);

        Table t1 = new Table(path,blocks[0]);
        disk.getAllocatedBlocksTable().add(t1);

        for (int i = 0; i < blocks.length; i+=extentSize)
        {
            disk.getBlocks()[blocks[i]].setStatus(false);
            disk.getBlocks()[blocks[i+1]].setStatus(false);
            if(i != blocks.length - 2)
                disk.getBlocks()[blocks[i+1]].setIndex(blocks[i+2]);
        }

        return true;
    }
    boolean deallocate(VirtualFileSystem disk, String path)
    {
        int counter = 0;
        int cur=-1;
        for (int i = 0; i < disk.getAllocatedBlocksTable().size(); i++) {
            if(disk.getAllocatedBlocksTable().get(i).getPath().equals(path))
            {
                cur = disk.getAllocatedBlocksTable().get(i).getStart();
                disk.getAllocatedBlocksTable().remove(i);
                break;
            }
        }
        if(cur == -1)
             return false;
        while (cur != -1 )
        {
            disk.getBlocks()[cur].setStatus(true);
            disk.getBlocks()[cur+1].setStatus(true);

            disk.getFreeBlocksIndex()[cur] = cur;
            disk.getFreeBlocksIndex()[cur+1] = cur+1;

            int c = cur;
            cur = disk.getBlocks()[cur+1].getIndex();
            disk.getBlocks()[c+1].setIndex(-1);
            counter +=2 ;
        }
        disk.setFreeBlocks(disk.getFreeBlocks() + counter);
        disk.setAllocatedBlocks(disk.getAllocatedBlocks() - counter);

        return true;
    }
}
