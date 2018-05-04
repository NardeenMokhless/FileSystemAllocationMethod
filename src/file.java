public class file
{
    private String fileName;
    private String filePath;
    private int[] allocatedBlocks;
    private boolean deleted;
    private int fileSize;

    public file(){
        this.filePath = "";
        this.fileName = "";
        this.deleted = false;
        this.fileSize = 0;
        allocatedBlocks = null;
    }
    public file(String filePath, int fileSize) {
        this.filePath = filePath;
        this.deleted = false;
        this.fileSize = fileSize;
        allocatedBlocks = new int[fileSize];

        String [] arr = filePath.split("/");
        fileName = arr[arr.length-1];
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int[] getAllocatedBlocks() {
        return allocatedBlocks;
    }

    public void setAllocatedBlocks(int[] allocatedBlocks) {
        this.allocatedBlocks = allocatedBlocks;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }


}
