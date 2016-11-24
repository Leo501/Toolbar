package com.leo.sleep.utils;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leo70 on 2016/11/20.
 */

public class FontManagerUtils {

    private static final String TAG = FontManagerUtils.class.getName();

    private static FontManagerUtils instance;

    private AssetManager assetManager;

    private Map<String, Typeface> fonts;

    private FontManagerUtils(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.fonts = new HashMap<>();
    }

    public static FontManagerUtils getInstance(AssetManager assetManager) {
        if (instance == null) {
            instance = new FontManagerUtils(assetManager);
        }
        return instance;
    }

    public Typeface getFont(String asset) {
        if (fonts.containsKey(asset))
            return fonts.get(asset);

        Typeface font = null;

        try {
            font = Typeface.createFromAsset(assetManager, asset);
            fonts.put(asset, font);
        } catch (RuntimeException e) {
            Log.e(TAG, "getFont: Can't create font from asset.", e);
        }

        return font;
    }
}
