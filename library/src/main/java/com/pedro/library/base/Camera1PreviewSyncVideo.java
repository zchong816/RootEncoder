package com.pedro.library.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.content.Context;
import android.os.Build;
import android.view.SurfaceView;
import android.view.TextureView;

import com.pedro.encoder.video.FormatVideoEncoder;
import com.pedro.library.view.OpenGlView;

/**
 * @author zhangchong
 * @date 2024/4/20 11:54
 */
public abstract class Camera1PreviewSyncVideo extends Camera1Base {

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
}
