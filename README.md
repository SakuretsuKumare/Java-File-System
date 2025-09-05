# Java-File-System



A file system written in Java. The FileSystem singleton class represents the file system as a whole which can have multiple drives or tree structures like (C, D, E drives in Windows). I used an abstract class to represent any file element such as a file or directory which should have base properties that all elements should have. It also utilizes multiple interfaces for things such as visitor methods or any command classes. It also has an interface for sorting files, directories, and links. Examples of sorting include sorting by alpabetical, time an object was created, file type (directory -> file -> link), and even sort by size of the element. There is also tree traversal with file crawlers that can count the amount of elements within a certain tree, return all of the files in the tree, and even find a specific file you are looking for.



\*\*Built and compiled using Apache Ant\*\*



Ran using `ant -f FileSystem.xml` on a shell





