package com.seven.log.utils.clients;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPClient;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author AlbertJ
 */
public class FtpUtil {
    private String host;
    private int port = 21;
    private String userName;
    private String password;
    //https://www.cnblogs.com/chenfei0801/p/3427310.html  ftpClient中文乱码问题解决
    private String charSet = "GBK";
    private String iso = "iso-8859-1";
    private FTPClient ftpClient = new FTPClient();
    private String homePath;
    private String currentPath;
    private boolean used = false;
    private static String dot = ".";
    private static String doubleDot = "..";
    public static Pattern CHINESE_PATTERN = Pattern.compile("[\u4E00-\u9FA5]");

    public FtpUtil() {
    }

    /**
     * @param host
     */
    public FtpUtil(String host) {
        this.host = host;
    }

    /**
     * @param host
     * @param port
     */
    public FtpUtil(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * @param host
     * @param userName
     * @param password
     */
    public FtpUtil(String host, String userName, String password) {
        this(host, 21, userName, password, "UTF-8");
    }

    /**
     * @param host
     * @param port
     * @param userName
     * @param password
     */
    public FtpUtil(String host, int port, String userName, String password) {
        this(host, port, userName, password, "UTF-8");
    }

    /**
     * @param host
     * @param port
     * @param userName
     * @param password
     * @param charSet
     */
    public FtpUtil(String host, int port, String userName, String password, String charSet) {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
//        this.charSet = charSet;
    }

    public boolean connect() {
        try {
            if (host != null) {
                ftpClient.connect(host, port);
            } else {
                throw new Exception("host is null , please set host!");
            }
            if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param host
     *
     * @return
     */
    public boolean connect(String host) {
        try {
            if (host != null) {
                ftpClient.connect(host, port);
            } else {
                throw new Exception("host is null , please set host!");
            }
            if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                this.host = host;
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param host
     * @param port
     *
     * @return
     */
    public boolean connect(String host, int port) {
        try {
            if (host != null) {
                ftpClient.connect(host, port);
            } else {
                throw new Exception("host is null , please set host!");
            }
            if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                this.host = host;
                this.port = port;
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean login() {
        try {
            if (userName == null || password == null) {
                throw new NullPointerException("userName or password is null");
            } else {
                ftpClient.login(userName, password);
            }
            if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param userName
     * @param password
     *
     * @return
     */
    public boolean login(String userName, String password) {
        try {
            if (userName == null || password == null) {
                throw new NullPointerException("userName or password is null");
            } else {
                ftpClient.login(userName, password);
            }
            if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                this.userName = userName;
                this.password = password;
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public FTPClient initFtpClient() {
        try {
            if (connect()) {
                if (login()) {
                    if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
                        charSet = "UTF-8";
                    }
                    ftpClient.setControlEncoding(charSet);
                    ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                    ftpClient.enterLocalPassiveMode();
                    homePath = ftpClient.printWorkingDirectory();
                    currentPath = ftpClient.printWorkingDirectory();
                    return ftpClient;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param host
     *
     * @return
     */
    private FTPClient initFtpClient(String host) {
        try {
            if (connect(host)) {
                if (login()) {
                    if (charSet == null || charSet.isEmpty()) {
                        ftpClient.setControlEncoding("UTF-8");
                    } else {
                        ftpClient.setControlEncoding(charSet);
                    }
                    ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                    ftpClient.enterLocalPassiveMode();
                    homePath = ftpClient.printWorkingDirectory();
                    currentPath = ftpClient.printWorkingDirectory();
                    return ftpClient;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param host
     * @param port
     *
     * @return
     */
    private FTPClient initFtpClient(String host, int port) {
        try {
            if (connect(host, port)) {
                if (login()) {
                    if (charSet == null || charSet.isEmpty()) {
                        ftpClient.setControlEncoding("UTF-8");
                    } else {
                        ftpClient.setControlEncoding(charSet);
                    }
                    ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                    ftpClient.enterLocalPassiveMode();
                    homePath = ftpClient.printWorkingDirectory();
                    currentPath = ftpClient.printWorkingDirectory();
                    return ftpClient;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param host
     * @param userName
     * @param password
     *
     * @return
     */
    private FTPClient initFtpClient(String host, String userName, String password) {
        try {
            if (connect(host)) {
                if (login(userName, password)) {
                    if (charSet == null || charSet.isEmpty()) {
                        ftpClient.setControlEncoding("UTF-8");
                    } else {
                        ftpClient.setControlEncoding(charSet);
                    }
                    ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                    ftpClient.enterLocalPassiveMode();
                    homePath = ftpClient.printWorkingDirectory();
                    currentPath = ftpClient.printWorkingDirectory();
                    return ftpClient;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param host
     * @param port
     * @param userName
     * @param password
     *
     * @return
     */
    private FTPClient initFtpClient(String host, int port, String userName, String password) {
        try {
            if (connect(host, port)) {
                if (login(userName, password)) {
                    if (charSet == null || charSet.isEmpty()) {
                        ftpClient.setControlEncoding("UTF-8");
                    } else {
                        ftpClient.setControlEncoding(charSet);
                    }
                    ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                    ftpClient.enterLocalPassiveMode();
                    homePath = ftpClient.printWorkingDirectory();
                    currentPath = ftpClient.printWorkingDirectory();
                    return ftpClient;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param host
     * @param port
     * @param userName
     * @param password
     * @param charSet
     *
     * @return
     */
    private FTPClient initFtpClient(String host, int port, String userName, String password, String charSet) {
        try {
            if (connect(host, port)) {
                if (login(userName, password)) {
                    if (charSet == null || charSet.isEmpty()) {
                        ftpClient.setControlEncoding("UTF-8");
                    } else {
                        ftpClient.setControlEncoding(charSet);
                        this.charSet = charSet;
                    }
                    ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                    ftpClient.enterLocalPassiveMode();
                    homePath = ftpClient.printWorkingDirectory();
                    currentPath = ftpClient.printWorkingDirectory();
                    return ftpClient;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public InputStream getInputStreamNoConvert(String filePath) {
        InputStream inputStream = null;
        try {
            inputStream = ftpClient.retrieveFileStream(filePath);
            if (inputStream != null) {
                used = true;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return inputStream;
    }

    public InputStream getInputStream(String filePath) {
        InputStream inputStream = null;
        try {
            if (CHINESE_PATTERN.matcher(filePath).find()) {
                inputStream = ftpClient.retrieveFileStream(new String(filePath.getBytes(charSet), iso));
            } else {
                inputStream = ftpClient.retrieveFileStream(filePath);
            }
            if (inputStream != null) {
                used = true;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return inputStream;
    }

    public InputStream getInputStream(String path, String fileName) {
        InputStream inputStream = null;
        try {
            String filePath = "";
            if (path == null || path.isEmpty()) {
                filePath = fileName;
            } else {
                if (path.endsWith("/") && !fileName.startsWith("/") || !path.endsWith("/") && fileName.startsWith("/")) {
                    filePath = path + fileName;
                } else {
                    filePath = path + "/" + fileName;
                }
            }
            if (CHINESE_PATTERN.matcher(filePath).find()) {
                inputStream = ftpClient.retrieveFileStream(new String(filePath.getBytes(charSet), iso));
            } else {
                inputStream = ftpClient.retrieveFileStream(filePath);
            }
            if (inputStream != null) {
                used = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return inputStream;

    }

    public OutputStream getOutputStreamNoConvert(String fileName) {
        OutputStream outputStream = null;
        try {
            String filePath = fileName;
            if (filePath.startsWith("/")) {
                filePath = filePath.substring(1);
            }
            if (filePath.contains("/")) {
                if (ftpClient.changeWorkingDirectory(filePath.substring(0, filePath.lastIndexOf("/")))) {
                    ftpClient.changeWorkingDirectory(currentPath);
                } else {
                    String tempName = "";
                    for (String temp : filePath.substring(0, filePath.lastIndexOf("/")).split("/")) {
                        if (tempName.isEmpty()) {
                            tempName = temp;
                        } else {
                            tempName += "/" + temp;
                        }
                        ftpClient.makeDirectory(tempName);
                    }
                }
            }
            outputStream = ftpClient.storeFileStream(filePath);
            if (outputStream != null) {
                used = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return outputStream;
    }


    public OutputStream getOutputStream(String fileName) {
        OutputStream outputStream = null;
        try {
            String filePath = fileName;
            if (filePath.startsWith("/")) {
                filePath = filePath.substring(1);
            }
            if (filePath.contains("/")) {
                if (ftpClient.changeWorkingDirectory(filePath.substring(0, filePath.lastIndexOf("/")))) {
                    ftpClient.changeWorkingDirectory(currentPath);
                } else {
                    String tempName = "";
                    for (String temp : filePath.substring(0, filePath.lastIndexOf("/")).split("/")) {
                        if (tempName.isEmpty()) {
                            tempName = temp;
                        } else {
                            tempName += "/" + temp;
                        }
                        ftpClient.makeDirectory(tempName);
                    }
                }
            }
            if (CHINESE_PATTERN.matcher(filePath).find()) {
                outputStream = ftpClient.storeFileStream(new String(filePath.getBytes(charSet), iso));
            } else {
                outputStream = ftpClient.storeFileStream(filePath);
            }
            if (outputStream != null) {
                used = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return outputStream;
    }

    public OutputStream getAppendOutputStream(String fileName) {
        OutputStream outputStream = null;
        try {
            String filePath = fileName;
            if (filePath.startsWith("/")) {
                filePath = filePath.substring(1);
            }
            if (filePath.contains("/")) {
                if (ftpClient.changeWorkingDirectory(filePath.substring(0, filePath.lastIndexOf("/")))) {
                    ftpClient.changeWorkingDirectory(currentPath);
                } else {
                    String tempName = "";
                    for (String temp : filePath.substring(0, filePath.lastIndexOf("/")).split("/")) {
                        if (tempName.isEmpty()) {
                            tempName = temp;
                        } else {
                            tempName += "/" + temp;
                        }
                        ftpClient.makeDirectory(tempName);
                    }
                }
            }
            if (CHINESE_PATTERN.matcher(filePath).find()) {
                outputStream = ftpClient.appendFileStream(new String(filePath.getBytes(charSet), iso));
            } else {
                outputStream = ftpClient.appendFileStream(filePath);
            }
            if (outputStream != null) {
                used = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return outputStream;
    }

    public OutputStream getOutputStream(String path, String fileName) {
        OutputStream outputStream = null;
        try {
            String filePath = "";
            if (path == null || path.isEmpty()) {
                filePath = fileName;
            } else {
                if (path.endsWith("/") && !fileName.startsWith("/") || !path.endsWith("/") && fileName.startsWith("/")) {
                    filePath = path + fileName;
                } else {
                    filePath = path + "/" + fileName;
                }
            }
            if (CHINESE_PATTERN.matcher(filePath).find()) {
                outputStream = ftpClient.storeFileStream(new String(filePath.getBytes(charSet), iso));
            } else {
                outputStream = ftpClient.storeFileStream(filePath);
            }
            if (outputStream != null) {
                used = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return outputStream;
    }

    /**
     * 获取ftp上指定文件名到输出流中
     *
     * @param ftpFileName
     * @param out
     */
    public void retrieveFile(String ftpFileName, OutputStream out) {
        try {
            FTPFile[] fileInfoArray = ftpClient.listFiles(ftpFileName);
            if (fileInfoArray == null || fileInfoArray.length == 0) {
                throw new FileNotFoundException("File '" + ftpFileName + "' was not found on FTP server.");
            }

            FTPFile fileInfo = fileInfoArray[0];
            if (fileInfo.getSize() > Integer.MAX_VALUE) {
                throw new IOException("File '" + ftpFileName + "' is too large.");
            }

            if (!ftpClient.retrieveFile(ftpFileName, out)) {
                throw new IOException("Error loading file '" + ftpFileName + "' from FTP server. Check FTP permissions and path.");
            }
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStream(out);
        }
    }

    /**
     * 将输入流存储到指定的ftp路径下
     *
     * @param ftpFileName
     * @param in
     */
    public void storeFile(String ftpFileName, InputStream in) {
        try {
            if (!ftpClient.storeFile(ftpFileName, in)) {
                throw new IOException("Can't upload file '" + ftpFileName + "' to FTP server. Check FTP permissions and path.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStream(in);
        }
    }

    /**
     * 下载ftp服务器文件到本地
     *
     * @param ftpFileName
     * @param localFile
     *
     * @throws IOException
     */
    public void download(String ftpFileName, File localFile) throws IOException {
        OutputStream out = null;
        try {
            FTPFile[] fileInfoArray = ftpClient.listFiles(ftpFileName);
            if (fileInfoArray == null || fileInfoArray.length == 0) {
                throw new FileNotFoundException("File " + ftpFileName + " was not found on FTP server.");
            }

            FTPFile fileInfo = fileInfoArray[0];
            if (fileInfo.getSize() > Integer.MAX_VALUE) {
                throw new IOException("File " + ftpFileName + " is too large.");
            }

            out = new BufferedOutputStream(new FileOutputStream(localFile));
            if (!ftpClient.retrieveFile(ftpFileName, out)) {
                throw new IOException("Error loading file " + ftpFileName + " from FTP server. Check FTP permissions and path.");
            }
            out.flush();
        } finally {
            closeStream(out);
        }
    }

    /**
     * 下载ftp服务器文件夹 包含子路径文件
     *
     * @param remotePath
     * @param localPath
     *
     * @throws IOException
     */
    public void downloadDir(String remotePath, String localPath) throws IOException {
        localPath = localPath.replace("\\\\", "/");
        File file = new File(localPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        FTPFile[] ftpFiles = ftpClient.listFiles(remotePath);
        for (int i = 0; ftpFiles != null && i < ftpFiles.length; i++) {
            FTPFile ftpFile = ftpFiles[i];
            if (ftpFile.isDirectory() && !dot.equals(ftpFile.getName()) && !doubleDot.equals(ftpFile.getName())) {
                downloadDir(remotePath + "/" + ftpFile.getName(), localPath + "/" + ftpFile.getName());
            } else {
                download(remotePath + "/" + ftpFile.getName(), new File(localPath + "/" + ftpFile.getName()));
            }
        }
    }

    /**
     * 上传文件到ftp服务器
     *
     * @param ftpFileName
     * @param localFile
     *
     * @throws IOException
     */
    public void upload(String ftpFileName, File localFile) {
        InputStream in = null;
        try {
            if (!localFile.exists()) {
                throw new IOException("Can't upload '" + localFile.getAbsolutePath() + "'. This file doesn't exist.");
            }
            in = new BufferedInputStream(new FileInputStream(localFile));
            if (!ftpClient.storeFile(ftpFileName, in)) {
                throw new IOException("Can't upload file '" + ftpFileName + "' to FTP server. Check FTP permissions and path.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStream(in);
        }
    }

    /**
     * 上传目录到ftp服务器
     *
     * @param remotePath
     * @param localPath
     *
     * @throws IOException
     */
    public void uploadDir(String remotePath, String localPath) {
        try {
            localPath = localPath.replace("\\\\", "/");
            File file = new File(localPath);
            if (file.exists()) {
                if (!ftpClient.changeWorkingDirectory(remotePath)) {
                    ftpClient.makeDirectory(remotePath);
                    ftpClient.changeWorkingDirectory(remotePath);
                }
                File[] files = file.listFiles();
                if (null != files) {
                    for (File f : files) {
                        if (f.isDirectory() && !dot.equals(f.getName()) && !doubleDot.equals(f.getName())) {
                            uploadDir(remotePath + "/" + f.getName(), f.getPath());
                        } else if (f.isFile()) {
                            upload(remotePath + "/" + f.getName(), f);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException ex) {
                // do nothing
            }
        }
    }

    /**
     * @param pathName
     */
    public boolean changeWorkingDirectory(String pathName) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        try {
            pathName = pathName.replace("\\\\", "/");
            ftpClient.changeWorkingDirectory(pathName);
            if (ftpClient.getReplyCode() == 550 && "550 Failed to change directory.\r\n".equalsIgnoreCase(ftpClient.getReplyString())) {
                return false;
            }
            currentPath = ftpClient.printWorkingDirectory();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取ftpClient所在路径
     *
     * @return
     */
    public String getHomePath() {
        return homePath;
    }

    /**
     * 获取ftpClient所在路径
     *
     * @return
     */
    public String getCurrentPath() {
        try {
            return ftpClient.printWorkingDirectory();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取路径下所有文件信息包含文件夹
     *
     * @param filePath
     *
     * @return
     */
    public List<String> listAllNames(String filePath) {
        List<String> fileList = new ArrayList<>();
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles(filePath);
            if (ftpFiles != null) {
                for (FTPFile ftpFile : ftpFiles) {
                    fileList.add(ftpFile.getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    /**
     * 获取路径下所有文件夹名称
     *
     * @param filePath
     *
     * @return
     */
    public List<String> listPathNames(String filePath) {
        List<String> fileList = new ArrayList<>();
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles(filePath);
            if (ftpFiles != null) {
                for (int i = 0; i < ftpFiles.length; i++) {
                    FTPFile ftpFile = ftpFiles[i];
                    if (ftpFile.isDirectory()) {
                        fileList.add(ftpFile.getName());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    /**
     * 获取路径下所有文件信息 不包含文件夹
     *
     * @param filePath
     *
     * @return
     */
    public List<String> listFileNames(String filePath) {
        List<String> fileList = new ArrayList<>();
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles(filePath);
            if (ftpFiles != null) {
                for (int i = 0; i < ftpFiles.length; i++) {
                    FTPFile ftpFile = ftpFiles[i];
                    if (ftpFile.isFile()) {
                        fileList.add(ftpFile.getName());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    /**
     * 获取路径下所有文件信息 不包含文件夹
     *
     * @return
     */
    public List<String> listFileNames() {
        List<String> fileList = new ArrayList<>();
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles();
            if (ftpFiles != null) {
                for (int i = 0; i < ftpFiles.length; i++) {
                    FTPFile ftpFile = ftpFiles[i];
                    if (ftpFile.isFile()) {
                        fileList.add(ftpFile.getName());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    /**
     * 获取路径下所有文件信息 不包含文件夹
     *
     * @return
     */
    public List<FTPFile> listFile(String path) {
        List<FTPFile> fileList = new ArrayList<>();
        try {
            FTPFile[] ftpFiles;
            if (CHINESE_PATTERN.matcher(path).find()) {
                ftpFiles = ftpClient.listFiles(new String(path.getBytes(charSet), iso));
            }else {
                ftpFiles = ftpClient.listFiles(path);
            }
            if (ftpFiles != null) {
                for (int i = 0; i < ftpFiles.length; i++) {
                    FTPFile ftpFile = ftpFiles[i];
                    if (ftpFile.isFile()) {
                        fileList.add(ftpFile);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    /**
     * 获取路径下所有文件信息 不包含文件夹
     *
     * @return
     */
    public List<FTPFile> listFile() {
        List<FTPFile> fileList = new ArrayList<>();
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles();
            if (ftpFiles != null) {
                for (int i = 0; i < ftpFiles.length; i++) {
                    FTPFile ftpFile = ftpFiles[i];
                    if (ftpFile.isFile()) {
                        fileList.add(ftpFile);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }


    /**
     * 获取路径下所有文件信息 不包含文件夹
     *
     * @return
     */
    public List<FTPFile> listAll(String path) {
        List<FTPFile> fileList = new ArrayList<>();
        try {
            FTPFile[] ftpFiles;
            if (CHINESE_PATTERN.matcher(path).find()) {
                ftpFiles = ftpClient.listFiles(new String(path.getBytes(charSet), iso));
            }else {
                ftpFiles = ftpClient.listFiles(path);
            }
            if (ftpFiles != null) {
                for (int i = 0; i < ftpFiles.length; i++) {
                    FTPFile ftpFile = ftpFiles[i];
                    fileList.add(ftpFile);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    /**
     * 获取路径下所有文件信息 不包含文件夹
     *
     * @return
     */
    public List<FTPFile> listAll() {
        List<FTPFile> fileList = new ArrayList<>();
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles();
            if (ftpFiles != null) {
                for (int i = 0; i < ftpFiles.length; i++) {
                    FTPFile ftpFile = ftpFiles[i];
                    fileList.add(ftpFile);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    public FTPFile getFileByName(String filePath) {
        FTPFile ftpFile = null;
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles(filePath);
            if (ftpFiles != null) {
                if (ftpFiles[0].isFile()) {
                    ftpFile = ftpFiles[0];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftpFile;
    }

    public boolean makeDirectory(String pathname) throws IOException {
        if (ftpClient.isConnected()) {
            return ftpClient.makeDirectory(pathname);
        } else {
            return false;
        }
    }

    /**
     * @param pathName
     */
    public void makeDirs(String pathName) {
        try {
            pathName = pathName.replace("\\\\", "/");
            String[] pathNameArray = pathName.split("/");
            for (String each : pathNameArray) {
                if (each != null && !each.isEmpty()) {
                    ftpClient.makeDirectory(each);
                    ftpClient.changeWorkingDirectory(each);
                    currentPath = ftpClient.printWorkingDirectory();
                }
            }
            ftpClient.changeWorkingDirectory(homePath);
            currentPath = homePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     */
    public void disconnect() {
        if (null != ftpClient && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException ex) {
                // do nothing
            }
        }
    }

    /**
     * 发送命令到ftp服务器
     *
     * @param args
     *
     * @throws IOException
     */
    public void sendSiteCommand(String args) throws IOException {
        if (!ftpClient.isConnected()) {
            ftpClient.sendSiteCommand(args);
        }
    }

    public boolean changeToParentDirectory() {
        if (!ftpClient.isConnected()) {
            return false;
        }
        try {
            return ftpClient.changeToParentDirectory();
        } catch (IOException e) {
            // do nothing
        }
        return false;
    }

    public boolean completePendingCommand() {
        try {
            if (used) {
                return ftpClient.completePendingCommand();
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
