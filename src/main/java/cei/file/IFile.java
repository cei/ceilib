package cei.file;

public interface IFile {
	File bySeq(File file);
	int garbage(File file);
	int recycle(File file);
	boolean remove(File file);
}
