package frc.robot;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;

public class Camera implements Runnable {

    private volatile boolean exit = false;

    public void run() {
        UsbCamera camera = new UsbCamera("USB Camera 0", 0);
        camera.setVideoMode(PixelFormat.kMJPEG, 320, 240, 15);

        CvSink cvSink = CameraServer.getInstance().getVideo(camera);
        CvSource cvSource = CameraServer.getInstance().putVideo("Current View", 320, 240);

        Mat image = new Mat();
        Mat output = new Mat();

        while (!exit) {
            if (cvSink.grabFrame(image) == 0) {
                cvSource.notifyError(cvSink.getError());
                continue;
            }

            cvSink.setSource(camera);

            Imgproc.cvtColor(image, output, Imgproc.COLOR_BGR2GRAY);
            cvSource.putFrame(output);
        }
    }

    public void stop(boolean stopButton) {
        if (stopButton) {
            exit = true;
        }
    }
}