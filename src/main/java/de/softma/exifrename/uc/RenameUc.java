package de.softma.exifrename.uc;

import com.drew.imaging.ImageProcessingException;
import de.softma.exifrename.type.RenameOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.StreamSupport;

/**
 * Use case to run the rename action for a complete directory.
 */
public final class RenameUc {

   private static final Logger LOGGER = LoggerFactory.getLogger(RenameUc.class.getSimpleName());

   /**
    * Starts the rename action for the passed directory. The result will be an object which contains a preview of the
    * rename operations. The renaming has to be executed afterwards by the result object.
    *
    * @see RenameProcessor#
    *
    * @param directory
    *           the directory which contains the images to rename
    * @return the {@link RenameOperation}s
    * @throws IOException
    *            if an I/O error occurs
    */
   public Set<RenameOperation> createRenameOperationsForDirectory(Path directory) {
      RenameProcessor renameProcessor = new RenameProcessor();
      if (!Files.isDirectory(directory)) {
         LOGGER.error("{}: not a directory", directory);
         return Collections.emptySet();
      }
      DirectoryStream<Path> newDirectoryStream;
      try {
         newDirectoryStream = Files.newDirectoryStream(directory);
      } catch (IOException e) {
         LOGGER.error("{}: error while accessing directory ({})", directory, e.getMessage());
         return Collections.emptySet();
      }

      StreamSupport.stream(newDirectoryStream.spliterator(), false)
        .sorted(Comparator.naturalOrder())
        .filter(file -> Files.isRegularFile(file))
        .forEach(file -> {
         try {
            renameProcessor.addFile(file);
         } catch (ImageProcessingException | IOException e) {
            LOGGER.error("{}: Error while processing file ({})", file.getFileName(), e.getMessage());
         }
      });

      Set<RenameOperation> createRenameOperations = renameProcessor.createRenameOperations();

      return createRenameOperations;
   }

   public void executeRenameOperations(Set<RenameOperation> renameOperations) {
      for (RenameOperation renameOperation : renameOperations) {
         Path oldFilename = renameOperation.getOldFilename();
         Path newFilenamePath = renameOperation.getNewFilenamePath();
         LOGGER.info("Rename '{}' to '{}'", oldFilename, newFilenamePath);
         try {
            Files.move(oldFilename, newFilenamePath);
         } catch (NoSuchFileException e) {
            LOGGER.error("{}: Error while renaming (file not found)", oldFilename.getFileName());
         } catch (FileAlreadyExistsException e) {
            LOGGER.error("{}: Error while renaming (target file already exists)", oldFilename.getFileName());
         } catch (IOException e) {
            LOGGER.error("{}: Error while renaming ({})", oldFilename.getFileName(), e.getMessage());
         }
      }
   }

}
