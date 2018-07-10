package com.wjh.demo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 
 * 处理图片url
 */
public class QRCodeUtil {

    /**
     * 生成图像
     * 
     * @throws WriterException
     * @throws IOException
     */
    public static void testEncode() throws WriterException, IOException {
        String filePath = "D://";
        String fileName = "text.png";
        JSONObject json = new JSONObject();
        json.put("url", "https://www.baidu.com");
        json.put("author", "zwm");
        String content = json.toString();// 内容
        int width = 200; // 图像宽度
        int height = 200; // 图像高度
        String format = "png";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        Path path = FileSystems.getDefault().getPath(filePath, fileName);
        File file = new File(filePath + fileName);
        MatrixToImageWriter.writeToFile(bitMatrix, format, file);// 输出图像
        System.out.println("图片文件已经生成.请于D盘查找");
    }

    /**
     * 解析图像
     */
    public static void testDecode() {
        String filePath = "D://text.png";
        BufferedImage image;
        try {
            image = ImageIO.read(new File(filePath));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
            JSONObject content = JSONObject.parseObject(result.getText());
            System.out.println("图片中内容：  ");
            System.out.println("author:" + content.getString("author"));
            System.out.println("url：  " + content.getString("url"));
            System.out.println("图片中格式：  ");
            System.out.println("encode： " + result.getBarcodeFormat());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage createQRCode(final String url) throws WriterException, IOException {
        BufferedImage image = null;
        // 二维码图片输出流
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        HashMap<EncodeHintType, Comparable> hints = new HashMap<EncodeHintType, Comparable>();
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 设置QR二维码的纠错级别（H为最高级别）具体级别信息
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        BitMatrix bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE, 400, 400, hints);
        bitMatrix = updateBit(bitMatrix, 10);
        image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return image;
    }

    /**
     * 自定义白边边框宽度
     *
     * @param matrix
     * @param margin
     * @return
     */
    private static BitMatrix updateBit(final BitMatrix matrix, final int margin) {
        int tempM = margin * 2;
        // 获取二维码图案的属性
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + tempM;
        int resHeight = rec[3] + tempM;
        // 按照自定义边框生成新的BitMatrix
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        // 循环，将二维码图案绘制到新的bitMatrix中
        for (int i = margin; i < resWidth - margin; i++) {
            for (int j = margin; j < resHeight - margin; j++) {
                if (matrix.get(i - margin + rec[0], j - margin + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }

    public static void main(String[] args) throws WriterException, IOException {
//         testEncode();
        testDecode();
    }
}
