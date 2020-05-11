package com.shsxt.tmall.utils;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import java.io.*;
import java.util.*;
import org.springframework.web.multipart.MultipartFile;
public class UploadUtil {

    //设置好账号的ACCESS_KEY和SECRET_KEY
   private String ACCESS_KEY = "085DIf6kuUy23q6ytI2ngFIx6Vt0SrmFFxPCC2QA";
    //这两个登录七牛 账号里面可以找到
    private String SECRET_KEY = "JsuqCrGxfa4Pdxvcy7Sr_Vo0MgehJP4P3TesFc0f";
    //要上传的空间
    private String bucketname = "zwm";
    //对应要上传到七牛上 你的那个路径（自己建文件夹 注意设置公开）
    // 上传到七牛后保存的文件名
//    String key = "123.html";
    //上传文件的路径
//    String FilePath = "C:\\Users\\沐白\\Desktop\\肺炎疫情武汉加油.html";
    //本地要上传文件路径

/*　　
　　　　普通上传
　　　　multipartFile : form表单传过来的文件,
　　　　key: 文件名
　　*/
    public static String  upload(MultipartFile multipartFile, String key) throws IOException{
        String SECRET_KEY = "JsuqCrGxfa4Pdxvcy7Sr_Vo0MgehJP4P3TesFc0f";
        String ACCESS_KEY = "085DIf6kuUy23q6ytI2ngFIx6Vt0SrmFFxPCC2QA";
        String bucketname = "zwm";
        // 密钥配置
        Auth auth = Auth.create(ACCESS_KEY,SECRET_KEY);
        //创建上传对象
        Configuration configuration = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(configuration);

        String result = "";
        FileInputStream inputStream=(FileInputStream)multipartFile.getInputStream();
        byte[] uploadBytes = new byte[inputStream.available()];
        ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
        try {
            //调用put方法上传
            Response res = uploadManager.put(byteInputStream,key,auth.uploadToken(bucketname),null, null);
            //打印返回的信息
            result = key;
            System.out.println(result+"========================");
        } catch (QiniuException e) {
            e.printStackTrace();
            // 请求失败时打印的异常的信息
            result = "no";
            System.out.println(result+"===========失败=============");
        }
        return  result;

    }

    //流获取输入的搜索关键字同行一行的语句
    public static ArrayList<String> isContainContent(String url, String keyWord) throws  Exception{
        File pathname = new File(url);
        ArrayList<String> jsonObjects = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        boolean result = false;
        //行读取
        LineNumberReader lineReader = null;
        InputStreamReader read = new InputStreamReader(new FileInputStream(pathname), "gbk");
        lineReader = new LineNumberReader(read);
        String readLine = null;
        while((readLine =lineReader.readLine()) != null){

            //判断是否包含
            if(readLine.contains(keyWord)) {
//                result = true;
//                    jsonObject.put("lineWords",readLine);
//                    jsonObject.put("lineNumber", lineReader.getLineNumber());
                jsonObject.put("pathname",pathname);
                jsonObjects.add(readLine);
            }
        }
        //关闭流
        if(lineReader != null){
            try {
                lineReader.close();
            } catch (IOException e) {
                e.printStackTrace();
//                lineReader = null;
            }
        }
//        jsonObject.put("flag", result);
        return jsonObjects;
    }
}