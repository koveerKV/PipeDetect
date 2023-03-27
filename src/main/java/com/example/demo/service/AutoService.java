package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;

/**
 * 运行逻辑如下，调用该服务的autoRun（）方法，启动管道读写线程
 * 管道写线程WriterThread启动python，并且持续读取python数据，通过管道传递给管道读线程
 * 管道读线程获取管道写线程的数据，传入CURDService.upload()，该方法会执行DetectService.detect()
 * 在detect()方法中，
 *
 * @author koveer
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class AutoService {

    final CURDService curdService;

    //有两个类使用了这个变量，@Value不能注入静态变量，本来应该用全局变量的，偷个懒，
    static final int VAR = 5;

    @Value("${pythonPath}")
    String pythonPath;

    //该方法用于启动自动读取功能。
    public void autoRun() {
        try {
            //初始化文件夹
            curdService.init("E:\\Demo\\Debug\\IMGtmp");
            PipedWriter writer = new PipedWriter();
            PipedReader reader = new PipedReader();
            writer.connect(reader);// 这里注意一定要连接，才能通信

            new Thread(new WriterThread(writer)).start();
            Thread.sleep(1000);
            new Thread(new ReaderThread(reader)).start();


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //启动该线程，会不断读取python输出，并且不断录入数据库
    class ReaderThread implements Runnable {
        private final PipedReader reader;

        public ReaderThread(PipedReader reader) {
            this.reader = reader;
        }

        @Override
        public void run() {
            StringBuilder sb = new StringBuilder();
            int receive = 0;
            String[] sp = {};
            String[] temp = new String[VAR];
            try {
                while ((receive = reader.read()) != -1) {
                    sb.append((char) receive);
                    //String[]数组，数组的0-VAR都是image行
                    sp = sb.toString().split("@");
                    if (sp.length == VAR + 1) {
                        System.out.println("--------------------");
                        System.out.println(Arrays.toString(sp));
                        System.arraycopy(sp, 0, temp, 0, VAR);
                        curdService.upload(temp);
                        sb = new StringBuilder();
                    }
//                    System.out.println(sb);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } finally {
                try {
                    reader.close();
                    System.out.println("reader停止");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    //启动该线程，会启动python程序，不断输出值
    class WriterThread implements Runnable {

        private final PipedWriter writer;

        public WriterThread(PipedWriter writer) {
            this.writer = writer;
        }

        @Override
        public void run() {
            int receive = 0;

            try {
                Process proc;
                String[] cmd = new String[]{pythonPath + "\\venv\\Scripts\\detect.bat"};
                proc = Runtime.getRuntime().exec(cmd, null, new File(pythonPath + "\\venv\\Scripts"));

                //读取进程输出
                BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "gbk"));
                //***

                //读取进程报错
//                BufferedReader error = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
                //***

                String line = null;
                //将进程输出信息写到管道流


                while ((line = in.readLine()) != null) {
                    line = line.replaceAll("划痕缺陷", "A");
                    line = line.replaceAll("压坑缺陷", "B");
                    line = line.replaceAll("腐蚀缺陷", "C");
                    line = line.replaceAll("裂纹缺陷", "D");
//                    line = line.replaceAll("bmp", "jpg");
                    if (line.startsWith("image")) {
                        writer.write(line);
                    } else if (line.startsWith("Results")) {
                        writer.write(line.charAt(0));
                    }
                }
                in.close();
                //***
                proc.waitFor();
                //将进程报错信息输出到控制台
//                System.out.println("****************error*****************");
//                while ((line = error.readLine()) != null) {
//                    System.out.println(line);
//                }
//                System.out.println("****************error*****************");
//                error.close();
                //***

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    writer.close();
                    System.out.println("write停止");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //***
}
