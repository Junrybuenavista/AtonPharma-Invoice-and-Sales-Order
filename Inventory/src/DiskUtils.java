import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DiskUtils {
  public DiskUtils() {  }

  public String getSerialNumber(String drive) {
  String result = "";
    try {
      File file = File.createTempFile("realhowto",".vbs");
      file.deleteOnExit();
      FileWriter fw = new java.io.FileWriter(file);

      String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                  +"Set colDrives = objFSO.Drives\n"
                  +"Set objDrive = colDrives.item(\"" + drive + "\")\n"
                  +"Wscript.Echo objDrive.SerialNumber";  // see note
      fw.write(vbs);
      fw.close();
      Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
      BufferedReader input =
        new BufferedReader
          (new InputStreamReader(p.getInputStream()));
      String line;
      while ((line = input.readLine()) != null) {
         result += line;
      }
      input.close();
    }
    catch(Exception e){
        e.printStackTrace();
    }
    return result.trim();
  }

  public static void main(String[] args){
  	
    DiskUtils dn =new DiskUtils();
    String sn = dn.getSerialNumber("C");
    System.out.println(sn);
    javax.swing.JOptionPane.showConfirmDialog((java.awt.Component)
         null, sn, "Serial Number of C:",
         javax.swing.JOptionPane.DEFAULT_OPTION);
  }
}