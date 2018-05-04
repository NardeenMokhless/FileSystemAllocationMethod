import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;

public class Main {

    public static void main(String[] args) {

        Xerox xerox = new Xerox();
        VirtualFileSystem virtualFileSystem;

        if(xerox.load() == null)  ////
        {
            System.out.println("Which method of allocation/de-allocation ?  //enter its number\n1)Extent\n2)Indexed");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();     String type = "";
            if(choice == 1)
                type = "Extent";
            else
                type = "Indexed";
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter disk space: ");
            Integer diskSize = sc.nextInt();
            virtualFileSystem = new VirtualFileSystem(diskSize,type);
        }
        else
            virtualFileSystem = xerox.load();

        //User can enter commands
        String command = "";

        Scanner input = new Scanner(System.in);

        System.out.print(">> ");
        command = input.nextLine();

        while (!command.toLowerCase().equals("exit"))
        {
            String []Commands = command.split(" ");
            Commands[0] = Commands[0].toLowerCase();

            if(Commands[0].equals("createfile") && Commands.length == 3)
            {
                if(virtualFileSystem.CreateFile(Commands[1]+" "+ Commands[2]))
                    System.out.println("File is successfully created.");
                else
                    System.out.println("File is failed to be created.");
            }
            else if(Commands[0].equals("createfolder")&& Commands.length == 2)
            {
                if(virtualFileSystem.CreateFolder(Commands[1]))
                    System.out.println("Folder is successfully created.");
                else
                    System.out.println("Folder is failed to be created.");
            }
            else if(Commands[0].equals("deletefile")&& Commands.length == 2)
            {
                if(virtualFileSystem.DeleteFile(Commands[1]))
                    System.out.println("File is successfully deleted.");
                else
                    System.out.println("File is failed to be deleted.");
            }
            else if(Commands[0].equals("deletefolder")&& Commands.length == 2)
            {
                if(virtualFileSystem.DeleteFolder(Commands[1]))
                    System.out.println("Folder is successfully deleted.");
                else
                    System.out.println("Folder is failed to be deleted.");
            }
            else if(Commands[0].equals("displaydiskstatus")&& Commands.length == 1)
            {
                virtualFileSystem.DisplayDiskStatus();
            }
            else if(Commands[0].equals("displaydiskstructure")&& Commands.length == 1)
            {
                virtualFileSystem.getRoot().printDirectory(virtualFileSystem.getRoot(),"");
            }
            else
            {
                System.out.println("This command is not found !!");
            }

            System.out.print(">> ");
            command = input.nextLine();

        }
        //save it in file
        xerox.save(virtualFileSystem);
    }
}
