package com.shsxt.tmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shsxt.base.BaseService;
import com.shsxt.tmall.dao.CategoryMapper;
import com.shsxt.tmall.query.CategoryQuery;
import com.shsxt.tmall.utils.AssertUtil;
import com.shsxt.tmall.utils.ResizeImageUtil;
import com.shsxt.tmall.vo.Category;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class CategoryService extends BaseService<Category,Integer> implements com.shsxt.tmall.service.CategoryService {
    @Resource
    private CategoryMapper categoryMapper;
    @Override
    public void saveCategory(Category category) {
        /**
         * 1.参数校验
         *      name  非空  唯一
         *      grade  非空  1 或 2
         *          grade=1  一级分类
         *          grade=2  必须指定父级分类parentId   且父级分类id存在
         *
         * 2.设置默认值
         *      isValid
         *      createDate
         *      updateDate
         * 3.执行添加操作 判断结果
         */
        System.out.println("grade-->"+category.getGrade());
  /*      Category temp = categoryMapper.selectByPrimaryKey(parentId);
        if(null!=temp){
            category.setParentId(temp.getId());
        };*/
        System.out.println("parentId-->"+category.getParentId());
        //1.参数校验
        checkSaveOrUpdateParams(category.getName(),category.getGrade(),category.getParentId());
        Category temp = categoryMapper.queryByName(category.getName());
        AssertUtil.isTrue(null != temp,"该分类名称已存在!");
        //2.设置默认值
        category.setCreateDate(new Date());
        category.setIsValid(1);
        category.setUpdateDate(new Date());
        AssertUtil.isTrue(categoryMapper.insertSelective(category)<1,"新增分类失败!");
    }

    //校验参数合法性
    private void checkSaveOrUpdateParams(String name, Integer grade, Integer parentId) {
        AssertUtil.isTrue(StringUtils.isBlank(name),"请输入分类名称!");
        AssertUtil.isTrue(null==grade,"请指定分类级别!");
        AssertUtil.isTrue(!(grade==1||grade==2),"分类级别不合法!");
        if(grade==1){
            AssertUtil.isTrue(!(null==parentId),"暂不支持一级分类设置父级分类!");
        }else if(grade==2){
            AssertUtil.isTrue(null==parentId,"请指定父级分类!");
            Category temp = categoryMapper.selectByPrimaryKey(parentId);
            AssertUtil.isTrue(null==temp,"父级分类不存在!");
        }
    }

    @Override
    public void updateCategory(Category category) {
        /**
         * 1.参数校验
         *      name  非空  唯一
         *      grade  非空  1 或 2
         *          grade=1  一级分类
         *          grade=2  必须指定父级分类parentId   且父级分类id存在
         *
         * 2.设置默认值
         *      updateDate
         * 3.执行更新操作 判断结果
         */
        //1.参数校验
        checkSaveOrUpdateParams(category.getName(),category.getGrade(),category.getParentId());
        Category temp = categoryMapper.queryByName(category.getName());
        Integer cId = category.getId();
        if(null != temp){
            AssertUtil.isTrue(cId != temp.getId(),"该分类名称已存在!");
        }
        temp = categoryMapper.selectByPrimaryKey(cId);
        AssertUtil.isTrue(category.getGrade() != temp.getGrade(),"暂不支持分类级别变动!");
        //2.设置默认值
        category.setUpdateDate(new Date());
        AssertUtil.isTrue(categoryMapper.updateByPrimaryKeySelective(category)<1,"分类更新失败!");
    }

    @Override
    public void deleteCategory(Integer id) {
        /**
         * 当该分类下存在子分类时  不能删除
         */
        AssertUtil.isTrue(null == id,"请指定要删除的分类!");
        Integer  total = categoryMapper.countByParentId(id);
        AssertUtil.isTrue(null != total||total != 0,"暂不支持删除带有子类的分类!");
        Category temp = categoryMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(null == temp,"待删除记录不存在!");
        temp.setIsValid(0);
        AssertUtil.isTrue(categoryMapper.updateByPrimaryKeySelective(temp)<1,"分类删除失败!");
    }

    @Override
    public Map<String, Object> queryCategoryByParams(CategoryQuery categoryQuery) {
        if(StringUtils.isNotBlank(categoryQuery.getpName())){
            Category temp = categoryMapper.queryByName(categoryQuery.getpName());
            if(null!=temp){
                categoryQuery.setpId(temp.getId());
            }
        }
        Map<String,Object> map = new HashMap<String ,Object>();
        PageHelper.startPage(categoryQuery.getPage(),categoryQuery.getLimit());
        PageInfo<Category> pageInfo = new PageInfo<Category>(categoryMapper.selectByParams(categoryQuery));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    public void saveImg(MultipartFile file, HttpServletRequest request, Integer cId){
        AssertUtil.isTrue(null==file,"请选择待上传的图片!");
        //获取原始文件名
        String originalFileName = file.getOriginalFilename();
        AssertUtil.isTrue(!(originalFileName.contains(".")),"缺少后缀名");
        //获取文件后缀
        Integer last = originalFileName.lastIndexOf(".");
        String suffix = originalFileName.substring(last);
        //System.out.println("suffix==="+suffix);
        AssertUtil.isTrue(!(suffix.equals(".jpg")||suffix.equals(".png")),"仅支持png和jpg格式图片!");
        //设置文件名
        String newfileName = cId +suffix;
        //得到上传文件的保存目录webapps\img\category
        String uploadPath=request.getServletContext().getRealPath("")+"/img/category/";
        //获取项目名
        //String projectName=request.getServletContext().getContextPath().substring(1);
        //将上传文件的保存目录换成 D:\apache-tomcat-8.0.53\webapps/uploadfile\
        String newdestPath= uploadPath+newfileName;
        //判断上传文件的目录是否存在
        File newfile=new File(newdestPath);
        if(!newfile.exists() && !newfile.isDirectory()){
            System.out.println(newfile+"目录不存在，需要创建");
            //创建目录
            newfile.mkdirs();
        }
        try {
            file.transferTo(newfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(".jpg".equals(suffix)){
            File input = new File(newdestPath);
            BufferedImage image = null;
            try {
                image = ImageIO.read(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedImage resized = ResizeImageUtil.resizebyaspect(image, 300, 600);
            File output = new File(uploadPath+cId+".png");
            try {
                ImageIO.write(resized, "png", output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File temp = new File(uploadPath+cId+".jpg");
        if(null!=temp&&temp.exists()&&temp.isFile()){
            temp.delete();
        }
    }
}
