package frc.robot;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;

// class used for usbcams
public class Camera implements Runnable {
    // runnable allows the camera to be run in a separate thread without impeding on the processing of the rest of the code

    private volatile boolean exit = false;

    public void run() {
        UsbCamera camera = new UsbCamera("USB Camera 0", 0);
        camera.setVideoMode(PixelFormat.kMJPEG, 320, 240, 15); // (format, width, height, fps)

        CvSink cvSink = CameraServer.getInstance().getVideo(camera); // gets video stream from usb cam
        CvSource cvSource = CameraServer.getInstance().putVideo("Current View", 320, 240); // puts access to video stream on shuffleboard (width, height)

        Mat image = new Mat(); // original stream
        Mat output = new Mat(); // modified stream

        while (!exit) {
            if (cvSink.grabFrame(image) == 0) {
                cvSource.notifyError(cvSink.getError());
                continue;
            }

            cvSink.setSource(camera);

            Imgproc.cvtColor(image, output, Imgproc.COLOR_BGR2GRAY); // changes image to black and white to conserve bandwidth
            cvSource.putFrame(output); // puts the modified output stream on shuffleboard
        }
    }

    // purpose is to stop camera stream if camera is causing any issues
    public void stop(boolean stopButton) {
        if (stopButton) {
            exit = true;
        }
    }
}