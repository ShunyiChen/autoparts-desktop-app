package com.shunyi.autoparts.ui.common;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;

import java.io.*;

/**
 * @Description: VFS客户端
 *
 * @Author: 陈顺谊
 * @CreateDate: 2020/2/2 12:01
 * @Version: 1.0
 */
public class VFSClient {


    /**
     *
     * @param vfs
     * @return
     * @throws FileSystemException
     */
	public static boolean testConnection(com.shunyi.autoparts.ui.common.vo.VFS vfs) throws FileSystemException, UnsupportedEncodingException {
        FileObject file = resolveFile(vfs, "");
        file.close();
        return file.exists();
	}

    /**
     * 上传单个文件
     *
     * @param sourceFile 源文件
     * @param vfs 虚拟文件系统连接信息
     * @param targetPath 目标路径
     * @throws IOException
     */
	public static void uploadSingleFile(File sourceFile, com.shunyi.autoparts.ui.common.vo.VFS vfs, String targetPath) throws IOException {
        FileObject file = resolveFile(vfs, targetPath);
        if (!file.exists()) {
            file.createFile();
        }
        //File转换bytes
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        FileInputStream fis = new FileInputStream(sourceFile);
        byte[] b = new byte[1024];
        int n;
        while((n = fis.read(b)) != -1) {
            bos.write(b, 0, n);
        }
        fis.close();
        bos.close();
        byte[] buffer = bos.toByteArray();
        //文件拷贝
        IOUtils.write(buffer, file.getContent().getOutputStream());
        //关闭FileObject
        file.close();
	}

    /**
     * 下载单个文件
     *
     * @param vfs
     * @param targetPath
     * @return
     * @throws FileSystemException
     * @throws UnsupportedEncodingException
     */
	public static FileObject downloadSingleFile(com.shunyi.autoparts.ui.common.vo.VFS vfs, String targetPath) throws FileSystemException, UnsupportedEncodingException {
        return resolveFile(vfs, targetPath);
	}

    /**
     * 删除单个文件
     *
     * @param vfs
     * @param targetPath
     * @throws FileSystemException
     * @throws UnsupportedEncodingException
     */
	public static void deleteSingleFile(com.shunyi.autoparts.ui.common.vo.VFS vfs, String targetPath) throws FileSystemException, UnsupportedEncodingException {
        FileObject file = resolveFile(vfs, targetPath);
	    file.delete();
        file.close();
	}

    /**
     * 查找一个文件
     *
     * @param vfs
     * @param targetPath
     * @return
     * @throws FileSystemException
     * @throws UnsupportedEncodingException
     */
	public static FileObject resolveFile(com.shunyi.autoparts.ui.common.vo.VFS vfs, String targetPath) throws FileSystemException, UnsupportedEncodingException {
        FileSystemManager fileSystemManager = VFS.getManager();
        String protocol;
        String fileName = null;
        if (vfs.getProtocol().equals("FTP") || vfs.getProtocol().equals("FTPS")) {
            protocol = vfs.getProtocol().toLowerCase();
            fileName = protocol + "://" + vfs.getUserName() + ":" + vfs.getPassword() + "@" + vfs.getHost() + ":" + vfs.getPort() + vfs.getHome();
        } else if (vfs.getProtocol().equals("File")) {
            protocol = vfs.getProtocol().toLowerCase();
            fileName = protocol + ":///" + vfs.getHost();
        }
        targetPath = new String(targetPath.getBytes("UTF-8"),"ISO-8859-1");
        return fileSystemManager.resolveFile(fileName + targetPath);
    }




//	/**
//	 * 更新存储已用大小
//	 *
//	 * @param siteUniqueId
//	 * @param increment     增量字节数
//	 */
//	public void increaseUsedSize(int siteUniqueId, long increment) {
//		Site site = ui.siteService.findById(siteUniqueId);
//		long newUsedSize = site.getSiteCapacity().getUsedSpace() + increment;
//		int affectedRow = ui.siteService.updateCapacity(siteUniqueId, site.getSiteCapacity().getUpdateTimeMillis(), System.currentTimeMillis(), newUsedSize);
//		// 如果更新时间点不存在，怎递归执行，直到累加成功。
//		if (affectedRow == 0) {
//			increaseUsedSize(siteUniqueId, increment);
//		}
//	}
//
//
//	public static void main(String[] args) throws FileSystemException, UnsupportedEncodingException {
////		// Locate the Jar file
////		FileSystemManager fsManager = VFS.getManager();
//////		FileSystemOptions options = new FileSystemOptions();
//////		FtpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(options, true);
////
//////		String name = "ftp://user1:123456@127.0.0.1:21/";
//////		String name = "ftps://ftps_user:123456@127.0.0.1:21/";
////		String name = "ftp"+"://"+"chens"+":"+"Wonderful2018"+"@"+"10.48.5.75"+":"+"21"+"/";
//////		String name = "file"+":///"+"C:\\Users\\chens\\Documents\\development\\maxtree\\TrackBe4\\tmp\\uploads\\1";
//////		String name = "file"+":///"+"E:\\Maxtree\\trackbe4\\tmp\\uploads";
////
////		FileObject rootFile = fsManager.resolveFile(name );
////
//////		System.out.println(rootFile.exists());
////
////		// List the children of the Jar file
////		FileObject[] children = rootFile.getChildren();
////		System.out.println( "Children of " + rootFile.getName().getURI() );
////		for ( int i = 0; i < children.length; i++ ) {
////			String baseName = children[i].getName().getBaseName();
//////			System.out.println(children[i].getContent().);
////			String ext = Files.getFileExtension(baseName);
//////			System.out.println(baseName+"  "+ext+"  "); //prints txt
////			for (String d: children[i].getContent().getAttributeNames()) {
////				System.out.println(d);
////			}
//////            System.out.println(" " + baseName + "  --  " + new String(baseName.getBytes("iso-8859-1"),"UTF-8"));
////		}
//
//		VFSUtils sys = new VFSUtils();
//		Site site = new Site();
//		site.setSiteType("FTP");
//		site.setUserName("chens");
//		site.setPassword("Wonderful2018");
//		site.setHostAddr("10.48.5.75");
//		site.setPort(21);
//		site.setDefaultRemoteDirectory("/");
//		String targetPath = "1/primary/MaxTree截图20171220113538.png";
//		targetPath = new String(targetPath.getBytes("UTF-8"),"ISO-8859-1");
//		try {
//			sys.receiveUpload(site, targetPath);
//		} catch (FileException e) {
//			e.printStackTrace();
//		}
//	}

}
