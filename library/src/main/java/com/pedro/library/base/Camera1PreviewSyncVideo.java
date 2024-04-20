package com.pedro.library.base;
import androidx.annotation.RequiresApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.util.Pair;
import android.view.SurfaceView;
import android.view.TextureView;
import com.pedro.encoder.input.video.CameraHelper;
import com.pedro.encoder.video.FormatVideoEncoder;
import com.pedro.library.view.GlStreamInterface;
import com.pedro.library.view.OpenGlView;

/**
 * @author zhangchong
 * @date 2024/4/20 11:54
 */
public abstract class Camera1PreviewSyncVideo extends Camera1Base {

    private static final String TAG = "Camera1PreviewSyncVideo";

    public Camera1PreviewSyncVideo(SurfaceView surfaceView) {
        super(surfaceView);
    }

    public Camera1PreviewSyncVideo(TextureView textureView) {
        super(textureView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public Camera1PreviewSyncVideo(OpenGlView openGlView) {
        super(openGlView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public Camera1PreviewSyncVideo(Context context) {
        super(context);
    }


    @Override
    public boolean prepareVideo(int width, int height, int fps, int bitrate, int iFrameInterval,
                                int rotation, int profile, int level) {
        if ((onPreview && (fps != videoEncoder.getFps() || rotation != videoEncoder.getRotation()))) {
            stopPreview();
        }
        FormatVideoEncoder formatVideoEncoder =
                glInterface == null ? FormatVideoEncoder.YUV420Dynamical : FormatVideoEncoder.SURFACE;
        return videoEncoder.prepareVideoEncoder(previewWidth
                , previewHeight
                , videoEncoder.getFps()
                , bitrate
                , videoEncoder.getRotation()
                , iFrameInterval
                , formatVideoEncoder
                , profile
                , level);
    }


    public void startPreview(CameraHelper.Facing cameraFacing, int width, int height, int fps, int rotation) {
        if (!isStreaming() && !onPreview) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 && (glInterface instanceof GlStreamInterface)) {
                // if you are using background mode startPreview only work to indicate
                // that you want start with front or back camera
                cameraManager.setCameraFacing(cameraFacing);
                return;
            }
            Pair<Integer, Integer> wh = cameraManager.start(cameraFacing, width, height, videoEncoder.getFps());
            previewWidth = wh.first;
            previewHeight = wh.second;
            videoEncoder.setFps(fps);
            videoEncoder.setRotation(rotation);
            prepareGlView(wh.first, wh.second, rotation);
            cameraManager.setRotation(rotation);
            onPreview = true;
        } else {
            Log.e(TAG, "Streaming or preview started, ignored");
        }
    }
}
