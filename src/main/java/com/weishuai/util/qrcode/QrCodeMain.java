package com.weishuai.util.qrcode;


/**
 * @Description : QcCode 入口
 * @Author : Future Buddha
 * @Date: 2021-12-10 23:50
 */
public class QrCodeMain {

    public static void main(String[] args) throws Exception {
        String destPath = "/Users/ws001/Desktop";
        //生成带logo 的二维码
        String text = "https://mp.weixin.qq.com/s?__biz=MzUyMzg0OTk0MA==&mid=2247483775&idx=1&sn=b8796c4270acf76ff5e45f6166ce8043&chksm=fa3718c4cd4091d2ce7c796942835d20c8cc6daa77192b65cef85469c0dd9d603f25122f3b92#rd";
        String imgPath = "/Users/ws001/Desktop/weChat.jpeg";
        QrCodeUtil.encode(text, imgPath, destPath, true);

//        //生成不带logo 的二维码
//        String text = "https://mp.weixin.qq.com/s?__biz=MzUyMzg0OTk0MA==&mid=2247483775&idx=1&sn=b8796c4270acf76ff5e45f6166ce8043&chksm=fa3718c4cd4091d2ce7c796942835d20c8cc6daa77192b65cef85469c0dd9d603f25122f3b92#rd";
//        QrCodeUtil.encode(text, "", destPath, true);
//
//        //指定二维码图片，解析返回数据
//        System.out.println(QrCodeUtil.decode("/Users/ws/Desktop/98675807.jpg"));

    }
}
