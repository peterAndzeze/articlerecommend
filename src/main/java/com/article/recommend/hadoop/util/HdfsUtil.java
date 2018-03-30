package com.article.recommend.hadoop.util;

import com.article.recommend.constant.RecommendConstant;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.util.Optional;

/**
 * hdfs 工具类
 */
public class HdfsUtil {
    private static  final Logger logger= LoggerFactory.getLogger(HdfsUtil.class);
    /**
     * 创建文件夹
     * @param dirName 文件夹名称
     * @param path  路径
     * @return/
     */
    public static boolean mkdir(String dirName,String path){
        if(!StringUtils.isNotBlank(dirName)){//不存在
            return false;
        }
        Path newPath= new Path(RecommendConstant.BASEPATH+dirName);
        FileSystem fs=null;
        try {
            fs=HadoopUtil.createFileSystem(null);
            if(!fs.exists(newPath)){
                fs.mkdirs(newPath);
                return true;
            }
            logger.info("文件夹已存在{}",dirName);
           return false;
        } catch (IOException e) {
            logger.error("创建文件夹{}{}:{}",dirName,"失败",e.getMessage());
            e.printStackTrace();
            return false;
        }finally {
           closeFileSystem(fs);
        }

    }

    /**
     * 删除文件夹
     * @param dir 要删除的文件夹
     * @param path 文件夹路径
     * @return 删除结果
     */
    public static boolean deleteDir(String dir,String path){
        if(!StringUtils.isNotBlank(dir)){
            return false;
        }
        Path newPath=new Path(path+File.separatorChar+dir);
        FileSystem fileSystem=null;
        try {
            fileSystem =HadoopUtil.createFileSystem(null);
            if(fileSystem.exists(newPath)){//存在文件夹
                fileSystem.delete(newPath,true);
                logger.info("成功删除文件夹{}"+dir);
                return true;
            }
            logger.info("不存在文件夹{}",newPath.getName());
            return false;
        }catch (Exception e){
           logger.error("删除文件夹{}异常:{}",newPath.getName(),e.getMessage());
            e.printStackTrace();
            return false;
        }finally {
            closeFileSystem(fileSystem);
        }
    }

    /**
     * 创建hdfs文件
     * @param fileName 文件名称
     * @param hdfsPath 文件路径
     * @param content 文件内容
     * @return 创建结果
     */
    public static  boolean createHdfsFile(String fileName,String hdfsPath,String content){
        if(!StringUtils.isNotBlank(fileName)){//文件为空
            return false;
        }
        String newFile=hdfsPath+File.separatorChar+fileName;
        FileSystem fileSystem=null;
        FSDataOutputStream fsDataOutputStream=null;
        try{
            fileSystem=HadoopUtil.createFileSystem(null);
            fsDataOutputStream=fileSystem.create(new Path(newFile));
            byte [] buff=content.getBytes("utf-8");
            fsDataOutputStream.write(buff,0,buff.length);
            logger.info("成功新建文件{}",newFile);
            return true;
        }catch (Exception e){
           logger.error("创建文件{}异常:{}",newFile,e.getMessage());
            e.printStackTrace();
            return false;
        }finally {
            closeFileSystem(fileSystem);
            closeFileStream(fsDataOutputStream,null);
        }
    }

    /**
     * 追加内容到hdfs文件
     * @param hdfsFile
     * @param content
     * @return
     */
    public static  boolean appendContenToHdfsFile(String hdfsFile,String content){
        if (StringUtils.isBlank(hdfsFile)) {
            return false;
        }
        if(StringUtils.isEmpty(content)){
            return true;
        }
        Configuration conf = HadoopUtil.createHadoopConf();
        // solve the problem when appending at single datanode hadoop env
        conf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
        conf.set("dfs.client.block.write.replace-datanode-on-failure.enable", "true");
        FileSystem fs =null;
        try{
            fs=HadoopUtil.createFileSystem(conf);
            Path path = new Path(hdfsFile);
            if (fs.exists(path)) {
                    InputStream in = new ByteArrayInputStream(content.getBytes());
                    OutputStream out = fs.append(new Path(hdfsFile));
                    IOUtils.copyBytes(in, out, 4096, true);
                    out.close();
                    in.close();
                    fs.close();
                logger.info("成功追加内容到{}",hdfsFile);
                return true;
            }
            return false;
        }catch (Exception e){
            logger.error("{}追加内容异常:{}",hdfsFile,e.getMessage());
            e.printStackTrace();
            return false;
        }finally {
            closeFileSystem(fs);
        }

    }

    /**
     * 追加文件到文件
     * @param filePaht 本地文件
     * @param hdfsFile hdfs 文件
     * @return
     */
    public static  boolean appendFileToHdfsFile(String filePaht,String hdfsFile){
        if (StringUtils.isBlank(hdfsFile)) {
            return false;
        }
        if(StringUtils.isEmpty(filePaht)){
            return true;
        }
        Configuration conf = HadoopUtil.createHadoopConf();
        // solve the problem when appending at single datanode hadoop env
        conf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
        conf.set("dfs.client.block.write.replace-datanode-on-failure.enable", "true");
        FileSystem fileSystem=null;
        try {
            fileSystem=HadoopUtil.createFileSystem(conf);
            InputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(filePaht));
            OutputStream bufferedOutputStream=fileSystem.append(new Path(hdfsFile));
            IOUtils.copyBytes(bufferedInputStream,bufferedOutputStream,4096,true);
            bufferedInputStream.close();
            bufferedOutputStream.close();
            logger.error("{}追加到{}成功",filePaht,hdfsFile);

            return true;
        }catch (Exception e){
            logger.error("{}追加到{}异常:{}",filePaht,hdfsFile,e.getMessage());
            e.printStackTrace();
            return false;
        }finally {
            closeFileSystem(fileSystem);
        }
    }

    /**
     * 读取文件内容
     * @param hdfsFile hdfs 文件全路径
     * @return
     */
    public static String readHdfsFile(String hdfsFile){
        if(!StringUtils.isNotBlank(hdfsFile)){
            return null;
        }
        FileSystem fileSystem=null;
        FSDataInputStream fsDataInputStream=null;
        try{
            fileSystem=HadoopUtil.createFileSystem(null);
            Path newPath=new Path(hdfsFile);
            if(fileSystem.exists(newPath)){
                fsDataInputStream=fileSystem.open(newPath);
                FileStatus fileStatus=fileSystem.getFileStatus(newPath);
                byte [] buff=new byte[Integer.parseInt(String.valueOf(fileStatus.getLen()))];
                fsDataInputStream.readFully(0,buff);
                logger.info("读取{}内容成功",hdfsFile);
                return new String(buff,"UTF-8");
            }
            logger.info("{}不存在",hdfsFile);
            return null;
        }catch (Exception e){
            logger.error("读取文件{}失败:{}",hdfsFile,e.getMessage());
            e.printStackTrace();
            return  null;
        }finally {
            closeFileStream(null,fsDataInputStream);
            closeFileSystem(fileSystem);
        }
    }

    /**
     * HDFS 到 HDFS 的合并
     * hdfs提供了一种FileUtil.copyMerge（）的方法， 注意下面的 false 这个，如果改为true，就会删除这个目录
     * @param folder 需要合并的目录
     * @param file  要合并成的文件，完整路径名称
     */
    public void copyMerge(String folder, String file) {
        Configuration conf =HadoopUtil.createHadoopConf();
        Path src = new Path(folder);
        Path dst = new Path(file);
        try {
            FileUtil.copyMerge(src.getFileSystem(conf), src,
                    dst.getFileSystem(conf), dst, false, conf, null);
            logger.info("合并{}成功",folder);
        } catch (IOException e) {
            logger.error("合并文件{}失败:{}",folder,e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 删除hdfs文件
     * @param hdfsFile
     * @return
     */
    public static  boolean delHdfs(String hdfsFile) {
        if(!StringUtils.isNotBlank(hdfsFile)){
            return  false;
        }
        FileSystem fileSystem = null;
        try{
            fileSystem=HadoopUtil.createFileSystem(null);
            Path dst = new Path(hdfsFile);
            logger.info("删除{}成功",hdfsFile);
            return fileSystem.delete(dst,false);
        }catch (Exception e){
            logger.error("删除{}异常:{}",hdfsFile,e.getMessage());
            e.printStackTrace();
            return false;
        }finally {
            closeFileSystem(fileSystem);
        }

    }


    /**
     * 关闭文件流
     * @param fsDataOutputStream
     * @param fsDataInputStream
     */
    protected  static  void closeFileStream(FSDataOutputStream fsDataOutputStream,FSDataInputStream fsDataInputStream){
        try{
            if(null !=fsDataOutputStream){
                fsDataOutputStream.close();
            }
            if(null !=fsDataInputStream){
                fsDataInputStream.close();
            }
        }catch (IOException e){
            logger.error("关闭文件输入输出流异常:{}",e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * 上传文件到hdfs
     * @param fileName 文件名称(绝对路径加文件名)
     * @param hdfsFilePath 文件路径
     * @return 上传结果
     */
    public static boolean uploadHdfsFile(String fileName,String hdfsFilePath){
        if(null== fileName || "".equals(fileName)){
            return false;
        }
        Path localFilePath=new Path(fileName);
        Path hdfsFilePaht=new Path(hdfsFilePath);
        FileSystem fileSystem=null;
        try {
            fileSystem =HadoopUtil.createFileSystem(null);
            fileSystem.copyFromLocalFile(localFilePath,hdfsFilePaht);
            return true;
        }catch (Exception e){
           logger.error("copy失败："+e.getMessage());
            e.printStackTrace();
            return  false;
        }finally {
            closeFileSystem(fileSystem);
        }
    }


    /**
     * 关闭文件系统实例
     * @param fileSystem
     */
    protected   static  void closeFileSystem(FileSystem fileSystem){
        if(null!=fileSystem || "".equals(fileSystem)){
            try {
                fileSystem.close();
            } catch (IOException e) {
                logger.error("关闭文件系统异常:{}",e.getMessage());
                logger.error("关闭文件系统异常:"+e.getMessage());
                e.printStackTrace();
            }
        }
    }


}
