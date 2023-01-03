package de.softma;

import de.softma.exifrename.uc.RenameUc;
import de.softma.exifrename.type.RenameOperation;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please give path to work on");
            System.out.println("e.g. App.java C:\\images");
            System.exit(255);
        }

        RenameUc renameUc = new RenameUc();
        URL resource = null;

        Path path = Paths.get(args[0]);
        Set<RenameOperation> renameOperations = renameUc.createRenameOperationsForDirectory(path);
        renameUc.executeRenameOperations(renameOperations);


    }
}
