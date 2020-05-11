package com.shsxt.tmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shsxt.base.BaseService;
import com.shsxt.tmall.dao.CategoryMapper;
import com.shsxt.tmall.dao.ProductImageMapper;
import com.shsxt.tmall.dao.ProductMapper;
import com.shsxt.tmall.query.ProductQuery;
import com.shsxt.tmall.utils.AssertUtil;
import com.shsxt.tmall.utils.QiniuCloudUtil;
import com.shsxt.tmall.utils.ResizeImageUtil;
import com.shsxt.tmall.utils.UploadUtil;
import com.shsxt.tmall.vo.Category;
import com.shsxt.tmall.vo.Product;
import com.shsxt.tmall.vo.ProductImage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class ProductService extends BaseService<Product,Integer> implements com.shsxt.tmall.service.ProductService {
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private ProductImageService productImageService;
    @Override
    public void saveProduct(Product product) {
        /**
         * 1.参数校验
         *      name  非空
         *      subTitle  非空
         *      originalPrice  非空   大于零
         *      stock  库存量   非空
         *      cid  所属分类id  非空 存在
         * 2.设置默认值
         *      createDate
         *      updateDate
         *      primotePrice
         * 3.执行添加操作判断结果
         */
        //1.参数合法性校验
        checkSaveOrUpdateProductParams(product.getName(),product.getSubTitle(),product.getOriginalPrice(),product.getStock(),product.getCid());
        //2.设置参数默认值
        product.setCreateDate(new Date());
        product.setUpdateDate(new Date());
        product.setPromotePrice(product.getOriginalPrice());
        //3.执行添加操作判断结果
        AssertUtil.isTrue(productMapper.insertSelective(product)<1,"新增商品失败!");
    }

    private void checkSaveOrUpdateProductParams(String name, String subTitle, Integer originalPrice, Integer stock, Integer cid) {
        AssertUtil.isTrue(StringUtils.isBlank(name),"请输入商品名称!");
        AssertUtil.isTrue(StringUtils.isBlank(subTitle),"请输入商品标题!");
        AssertUtil.isTrue(null==originalPrice,"请输入商品价格!");
        AssertUtil.isTrue(0==originalPrice,"原始价格不能为零!");
        AssertUtil.isTrue(null==stock,"请输入库存量!");
        //AssertUtil.isTrue(stock==0,"库存量不能为零!");
        AssertUtil.isTrue(null==cid,"请指定商品所属分类!");
        AssertUtil.isTrue(null==categoryMapper.selectByPrimaryKey(cid),"商品所属分类不存在!");
        AssertUtil.isTrue(1==categoryMapper.selectByPrimaryKey(cid).getGrade(),"暂不支持一级分类设置商品!");
    }

    @Override
    public void updateProduct(Product product) {
        //1.参数合法性校验
        checkSaveOrUpdateProductParams(product.getName(),product.getSubTitle(),product.getOriginalPrice(),product.getStock(),product.getCid());
        AssertUtil.isTrue(null==product.getPromotePrice(),"请输入促销价格!");
        //2.设置参数默认值
        product.setUpdateDate(new Date());
        //3.执行更新操作
        AssertUtil.isTrue(productMapper.updateByPrimaryKeySelective(product)<1,"商品信息更新失败!");
    }

    @Override
    public void deleteProduct(Integer pId) {
        /**
         * 1.参数校验
         *      pId 非空 存在
         *      级联删除商品图片表相关数据
         */
    }

    @Override
    public Map<String, Object> queryProductByParams(ProductQuery productQuery) {
        Map<String,Object> map = new HashMap<String ,Object>();
        PageHelper.startPage(productQuery.getPage(),productQuery.getLimit());
        PageInfo<Product> pageInfo = new PageInfo<Product>(productMapper.selectByParams(productQuery));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    public void saveImg(MultipartFile file, HttpServletRequest request, Integer pId,Integer flag){
        AssertUtil.isTrue(null==file,"请选择待上传的图片!");
        //获取原始文件名
        String originalFileName = file.getOriginalFilename();
        AssertUtil.isTrue(!(originalFileName.contains(".")),"缺少后缀名");
        //获取文件后缀
        Integer last = originalFileName.lastIndexOf(".");
        String suffix = originalFileName.substring(last);
        //System.out.println("suffix==="+suffix);
        AssertUtil.isTrue(!(suffix.equals(".jpg")||suffix.equals(".png")),"仅支持png和jpg格式图片!");


        try {
            UploadUtil.upload(file,pId+suffix);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //得到上传文件的保存目录webapps\img\product
        String uploadPath = null;
        if(1==flag){
            //设置文件名
            String newfileName = pId +suffix;
            uploadPath=request.getServletContext().getRealPath("")+"/img/product/main/";
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
                File output = new File(uploadPath+pId+".png");
                try {
                    ImageIO.write(resized, "png", output);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            File temp = new File(uploadPath+pId+".jpg");
            if(null!=temp&&temp.exists()&&temp.isFile()){
                temp.delete();
            }
        }else if(2==flag){
            uploadPath=request.getServletContext().getRealPath("")+"/img/product/detail/";
            ProductImage productImage = new ProductImage();
            productImage.setPid(pId);
            productImage.setType("detail");
            //保存图片信息
            Integer id = productImageService.saveProductImage(productImage);
            //设置文件名
            String newfileName = id +suffix;
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
                BufferedImage resized = ResizeImageUtil.resizebyaspect(image, 600, 800);
                File output = new File(uploadPath+id+".png");
                try {
                    ImageIO.write(resized, "png", output);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            File temp = new File(uploadPath+id+".jpg");
            if(null!=temp&&temp.exists()&&temp.isFile()){
                temp.delete();
            }

        }
    }
}
