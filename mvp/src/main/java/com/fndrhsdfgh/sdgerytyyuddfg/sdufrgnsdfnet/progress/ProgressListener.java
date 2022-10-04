package com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfnet.progress;

/**
 * Created by wanglei on 2017/9/10.
 */

public interface ProgressListener {
    void onProgress(long soFarBytes, long totalBytes);

    void onError(Throwable throwable);
}
