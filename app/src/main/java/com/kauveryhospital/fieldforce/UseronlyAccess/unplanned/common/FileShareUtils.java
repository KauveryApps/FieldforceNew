package com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.common;

import android.content.Context;
import android.net.Uri;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * Created by: Ranit Raj Ganguly on 17/04/21
 */
public class FileShareUtils {

    /**
     * Access file from 'Application Directory'
     *
     * @param context - application context
     * @param fileName - name of file inside application directory to be shared
     * @return uri - returns URI of file.
     */
    public static Uri accessFile(Context context, String fileName) {
        File file = new File(context.getExternalFilesDir(null), fileName);

        if (file.exists()) {
            return FileProvider.getUriForFile(context,
                    ".fileprovider", file);
        } else {
            return null;
        }
    }
}
