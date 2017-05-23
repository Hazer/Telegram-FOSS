/*
 * This is the source code of Telegram for Android v. 3.x.x.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Nikolai Kudashov, 2013-2017.
 */

package org.telegram.messenger;

import org.telegram.tgnet.TLRPC;

import java.util.Locale;

public class VideoEditedInfo {
    public long startTime;
    public long endTime;
    public int rotationValue;
    public int originalWidth;
    public int originalHeight;
    public int resultWidth;
    public int resultHeight;
    public int bitrate;
    public TLRPC.InputEncryptedFile encryptedFile;
    public TLRPC.InputFile file;
    public byte[] iv;
    public byte[] key;
    public boolean muted;
    public boolean roundVideo;
    public String originalPath;
    public long estimatedSize;
    public long estimatedDuration;

    public String getString() {
        return String.format(Locale.US, "-1_%d_%d_%d_%d_%d_%d_%d_%d_%s", startTime, endTime, rotationValue, originalWidth, originalHeight, bitrate, resultWidth, resultHeight, originalPath);
    }

    public boolean needConvert()
    {
        return (!this.roundVideo) || ((this.roundVideo) && ((this.startTime > 0L) || ((this.endTime != -1L) && (this.endTime != this.estimatedDuration))));
    }

    public boolean parseString(String string) {
        if (string.length() < 6) {
            return false;
        }
        try {
            String args[] = string.split("_");
            if (args.length >= 10) {
                startTime = Long.parseLong(args[1]);
                endTime = Long.parseLong(args[2]);
                rotationValue = Integer.parseInt(args[3]);
                originalWidth = Integer.parseInt(args[4]);
                originalHeight = Integer.parseInt(args[5]);
                bitrate = Integer.parseInt(args[6]);
                resultWidth = Integer.parseInt(args[7]);
                resultHeight = Integer.parseInt(args[8]);
                for (int a = 9; a < args.length; a++) {
                    if (originalPath == null) {
                        originalPath = args[a];
                    } else {
                        originalPath += "_" + args[a];
                    }
                }
            }
            return true;
        } catch (Exception e) {
            FileLog.e(e);
        }
        return false;
    }
}
