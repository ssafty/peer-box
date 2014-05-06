import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

// Splits files, format independent and merges them back 
public class FileSplitAndMerge {
	
	public static void mergeFiles(String filename, LinkedList<String> paths) throws IOException{
		File out = new File(filename);
		File read;
		FileOutputStream fos;
		FileInputStream fis;
		byte[] fileBytes;
		int bytesRead = 0;
	    fos = new FileOutputStream(out,true);             
	    for (String path: paths) {
	    	read = new File(path);
	        fis = new FileInputStream(read);
	        fileBytes = new byte[(int) read.length()];
	        bytesRead = fis.read(fileBytes, 0, (int) read.length());
	        fos.write(fileBytes);
	        fos.flush();
	        fileBytes = null;
	        fis.close();
	        fis = null;
	    }
	    fos.close();
	    fos = null;
	}
	
	// Returns list of paths to split files
    public static LinkedList<String> splitFile(File f) throws IOException {
    	LinkedList<String> paths = new LinkedList<String>();
        BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(f));
        FileOutputStream out;
        String name = f.getName();
        int partCounter = 1;
        // sizeOfFiles = 512 KB, can be customized to our needs
        int sizeOfFiles = 512 * 1024;	
        byte[] buffer = new byte[sizeOfFiles];
        int tmp = 0;
        String path;
        while ((tmp = bis.read(buffer)) > 0) {
        	
        	// Saves file as originalFileName.extension.partNo in the same directory as original file
        	path = f.getParent()+"/"+name+"."+String.format("%1d", partCounter++);
            File newFile=new File(path);
            newFile.createNewFile();
            out = new FileOutputStream(newFile);
            out.write(buffer,0,tmp);
            out.close();
         
            paths.add(path);
        }
        
        return paths;
    }

    public static void main(String[] args) throws IOException {
        LinkedList<String> paths = splitFile(
        		new File("/Users/macbokpro/Music/iERA/Self Discovery.mp3"));
        mergeFiles("/Users/macbokpro/Music/iERA/Self Discovery2.mp3", paths);
    }

}
