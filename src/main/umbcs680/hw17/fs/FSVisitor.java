package umbcs680.hw17.fs;

/*  Public interface class for visitors to visit the file system classes.
    Visitors need to have the ability to visit directories, files, and links.
    These base classes will be overwritten by subclasses.
 */

public interface FSVisitor {
    // Visit methods will be overwritten by each visitor
    void visit(Directory directory);
    void visit(File file);
    void visit(Link link);
}