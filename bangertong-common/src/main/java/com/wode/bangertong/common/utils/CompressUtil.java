package com.wode.bangertong.common.utils;//package com.wode.health.api.util;
//
//import ws.schild.jave.Encoder;
//import ws.schild.jave.MultimediaObject;
//import ws.schild.jave.VideoProcessor;
//import ws.schild.jave.encode.AudioAttributes;
//import ws.schild.jave.encode.EncodingAttributes;
//import ws.schild.jave.encode.VideoAttributes;
//import ws.schild.jave.info.AudioInfo;
//import ws.schild.jave.info.VideoSize;
//
//import java.io.*;
//import java.math.BigDecimal;
//
//
//public class CompressUtil {
//    public static File compressionVideo(File source, String picName) {
//        if (source == null) {
//            return null;
//        }
//        String newPath = source.getAbsolutePath().substring(0, source.getAbsolutePath().lastIndexOf(File.separator)).concat(File.separator).concat(picName);
//        File target = new File(newPath);
//        try {
//            MultimediaObject object = new MultimediaObject(source);
//            AudioInfo audioInfo = object.getInfo().getAudio();
//            // 根据视频大小来判断是否需要进行压缩,
//            int maxSize = 5;
//            double mb = Math.ceil(source.length() / 1048576);
//            int second = (int) object.getInfo().getDuration() / 1000;
//            BigDecimal bd = new BigDecimal(String.format("%.4f", mb / second));
//            System.out.println("开始压缩视频了--> 视频每秒平均 " + bd + " MB ");
//            // 视频 > 5MB, 或者每秒 > 0.5 MB 才做压缩， 不需要的话可以把判断去掉
//            boolean temp = mb > maxSize || bd.compareTo(new BigDecimal(0.5)) > 0;
////            if(temp){
//            long time = System.currentTimeMillis();
//            //TODO 视频属性设置
//            int maxBitRate = 128000;
//            int maxSamplingRate = 44100;
//            int bitRate = 800000;
//            int maxFrameRate = 20;
//            int maxWidth = 1280;
//
//            AudioAttributes audio = new AudioAttributes();
//            // 设置通用编码格式10                   audio.setCodec("aac");
//            // 设置最大值：比特率越高，清晰度/音质越好
//            // 设置音频比特率,单位:b (比特率越高，清晰度/音质越好，当然文件也就越大 128000 = 182kb)
//            if (audioInfo.getBitRate() > maxBitRate) {
//                audio.setBitRate(new Integer(maxBitRate));
//            }
//
//            // 设置重新编码的音频流中使用的声道数（1 =单声道，2 = 双声道（立体声））。如果未设置任何声道值，则编码器将选择默认值 0。
//            audio.setChannels(audioInfo.getChannels());
//            // 采样率越高声音的还原度越好，文件越大
//            // 设置音频采样率，单位：赫兹 hz
//            // 设置编码时候的音量值，未设置为0,如果256，则音量值不会改变
//            // audio.setVolume(256);
//            if (audioInfo.getSamplingRate() > maxSamplingRate) {
//                audio.setSamplingRate(maxSamplingRate);
//            }
//
//            //TODO 视频编码属性配置
//            ws.schild.jave.info.VideoInfo videoInfo = object.getInfo().getVideo();
//            VideoAttributes video = new VideoAttributes();
//            video.setCodec("h264");
//            //设置音频比特率,单位:b (比特率越高，清晰度/音质越好，当然文件也就越大 800000 = 800kb)
//            if (videoInfo.getBitRate() > bitRate) {
//                video.setBitRate(bitRate);
//            }
//
//            // 视频帧率：15 f / s  帧率越低，效果越差
//            // 设置视频帧率（帧率越低，视频会出现断层，越高让人感觉越连续），视频帧率（Frame rate）是用于测量显示帧数的量度。所谓的测量单位为每秒显示帧数(Frames per Second，简：FPS）或“赫兹”（Hz）。
//            if (videoInfo.getFrameRate() > maxFrameRate) {
//                video.setFrameRate(maxFrameRate);
//            }
//
//            // 限制视频宽高
//            int width = videoInfo.getSize().getWidth();
//            int height = videoInfo.getSize().getHeight();
//
//            if(width > height){
//                int tmp = width;
//                width = height;
//                height = tmp;
//            }
//            if (width > maxWidth) {
//                float rat = (float) width / maxWidth;
////                video.setSize(new VideoSize(maxWidth, (int) (height / rat)));
//                video.setSize(new VideoSize(width, height));
//
//            }
//
//            EncodingAttributes attr = new EncodingAttributes();
////                attr.setFormat("mp4");
//            attr.setAudioAttributes(audio);
//            attr.setVideoAttributes(video);
//
//            // 速度最快的压缩方式， 压缩速度 从快到慢： ultrafast, superfast, veryfast, faster, fast, medium,  slow, slower, veryslow and placebo.
////                attr.setPreset(PresetUtil.VERYFAST);
////                attr.setCrf(27);
////                // 设置线程数
////                attr.setEncodingThreads(Runtime.getRuntime().availableProcessors()/2);
//
//            Encoder encoder = new Encoder();
//            encoder.encode(new MultimediaObject(source), target, attr);
//            System.out.println("压缩总耗时：" + (System.currentTimeMillis() - time) / 1000);
//            return target;
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (target.length() > 0) {
//                source.delete();
//            }
//        }
//        return source;
//
//    }
//
//
////    public static void main(String[] ars) {
////        File file = new File("/Users/wode/Documents/1231.mp4");
////        compressionVideo(file, "123.mp4");
////
////    }
//
//}
