import java.util.ArrayList;

public class Indexed extends AllocationMethod{

    boolean allocate(VirtualFileSystem disk, String path, int size)
    {
        //checkFreeBlocks
        if(disk.getFreeBlocks() < size + 1 )
            return false;

        //remove from free blocks
        boolean flag = false;
        int start = -1, noOfBlocks = size + 1;
        for (int i = 0; i < disk.getBlockSize() ; i++) {
            if(disk.getFreeBlocksIndex()[i] != -1)
            {
                if(!flag) {
                    start = i;
                    flag = true;
                }
                else
                    disk.getBlocks()[start].getIndices().add(i);

                disk.getFreeBlocksIndex()[i] = -1;
                disk.getBlocks()[i].setStatus(false);
                disk.getBlocks()[i].setIndex(i);
                noOfBlocks --;
            }
            if(noOfBlocks == 0)
                break;
        }
        disk.setFreeBlocks(disk.getFreeBlocks() - (size + 1));

        //allocate the new blocks
        disk.setAllocatedBlocks(disk.getAllocatedBlocks() + (size + 1));

        Table table = new Table(path,start);
        disk.getAllocatedBlocksTable().add(table);
        //zabtnaha fo2


        return true;
    }

    boolean deallocate(VirtualFileSystem disk, String path)
    {
        //find start
        int start = 0;
        for (int i = 0; i < disk.getAllocatedBlocksTable().size() ; i++)
            if(disk.getAllocatedBlocksTable().get(i).getPath().equals(path))
                start = disk.getAllocatedBlocksTable().get(i).getStart();

        //add in free blocks
        for (int i = 0; i < disk.getBlocks()[start].getIndices().size(); i++)
        {
            disk.getFreeBlocksIndex()[disk.getBlocks()[start].getIndices().get(i)] = disk.getBlocks()[start].getIndices().get(i);
            disk.getBlocks()[disk.getBlocks()[start].getIndices().get(i)].setStatus(true);
            disk.getBlocks()[disk.getBlocks()[start].getIndices().get(i)].setIndex(disk.getBlocks()[start].getIndices().get(i));
        }

        disk.setFreeBlocks(disk.getFreeBlocks() + (disk.getBlocks()[start].getIndices().size() + 1));
        disk.setAllocatedBlocks(disk.getAllocatedBlocks() - (disk.getBlocks()[start].getIndices().size() + 1));

        disk.getFreeBlocksIndex()[start] = start;
        disk.getBlocks()[start].setStatus(true);
        disk.getBlocks()[start].setIndex(start);
        disk.getBlocks()[start].setIndices(new ArrayList<>());

        return true;
    }

}
