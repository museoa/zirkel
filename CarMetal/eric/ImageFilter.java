package eric;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class ImageFilter extends FileFilter {

    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String extension = this.getExtension(f);
        if (extension != null) {
            if (extension.equals("tiff") || extension.equals("tif") || extension.equals("svg") || extension.equals("gif") || extension.equals("jpeg") || extension.equals("jpg") || extension.equals("eps") || extension.equals("png")) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public String getDescription() {
        return "Images";
    }

    public String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
}
