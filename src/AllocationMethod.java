public abstract class AllocationMethod {

    abstract boolean  allocate(VirtualFileSystem disk, String path, int size);
    abstract boolean deallocate(VirtualFileSystem disk, String path);
}
