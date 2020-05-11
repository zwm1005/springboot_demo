package com.shsxt.tmall.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
public class ResizeImageUtil<output> {

    // 不按比例缩放
    public static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
    // 按比例缩放
    public static BufferedImage resizebyaspect(BufferedImage img, int height, int width) {
        int ori_width = img.getWidth();
        int ori_height = img.getHeight();
        float ratio_w = (float)width/ori_width;
        float ratio_h = (float)height/ori_height;
        int new_width = (ratio_w < ratio_h) ? width : (int)(ratio_h * ori_width);
        int new_height = (ratio_h < ratio_w) ? height : (int)(ratio_w * ori_height);
        System.out.println(new_height);
        System.out.println(new_width);
        Image tmp = img.getScaledInstance(new_width, new_height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}
