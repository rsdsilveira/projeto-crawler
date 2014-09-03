package webcrawler;

import com.dropbox.core.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Locale;

public class PocGoogleDrive {

    // dropbox account: imecrawler@gmail.com
    // dropbox password: pegatudo
    private DbxClient dropboxClient;
    
    public void StartClient()
    {
        final String APP_KEY = "q6tf1m9cosll9vq";
        final String APP_SECRET = "rv70idi7vcyojxr";

        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0",
            Locale.getDefault().toString());

        String accessToken = "6v7TSjvig5MAAAAAAAAAA6Ro-YkTWc7LIfpCkKrYltEt8CsG9B48lAtYmFNdgTFa";

        this.dropboxClient = new DbxClient(config, accessToken);
    }
    
    public void SendFile(String fileName, String fileContents) throws IOException, DbxException
    {
        byte[] byteArray = fileContents.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        
        try {
            
            DbxEntry.File uploadedFile = this.dropboxClient.uploadFile("/" + fileName + ".txt",
                DbxWriteMode.add(), fileContents.length(), inputStream);
            
            System.out.println("Uploaded: " + uploadedFile.toString());
            
        } 
        finally {
            inputStream.close();
        }
    }
    
    public void ListFilesInRootPath() throws DbxException
    {
        DbxEntry.WithChildren listing = this.dropboxClient.getMetadataWithChildren("/");
        System.out.println("Files in the root path:");
        for (DbxEntry child : listing.children) {
            System.out.println("	" + child.name + ": " + child.toString());
        }
    }
    
    public void ShowAccountInfo() throws DbxException
    {
        DbxAccountInfo accountInfo = dropboxClient.getAccountInfo();
        System.out.println("linked account: \t\t" + accountInfo.displayName);
        System.out.println("country: \t\t\t" + accountInfo.country);
        System.out.println("refferal link: \t\t\t" + accountInfo.referralLink);
        System.out.println("user id: \t\t\t" + accountInfo.userId);
    }
    
    public String GetFileContentFromDropbox(String fileName) throws FileNotFoundException, DbxException, IOException
    {
        FileOutputStream outputStream = new FileOutputStream( fileName + ".txt");
        try {
            DbxEntry.File downloadedFile = this.dropboxClient.getFile("/" + fileName + ".txt", null,
                outputStream);
            return downloadedFile.toString();
        } finally {
            outputStream.close();
        }
    }
    
    public void Run() throws IOException, DbxException {
        
        this.StartClient();

        this.ShowAccountInfo();
        
        this.SendFile("teste1231", "conteudo do teste NHAAA");
        
        this.ListFilesInRootPath();
        
        System.out.println(this.GetFileContentFromDropbox("teste1231"));
    }
}
