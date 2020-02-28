package com.ectrip.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
    private static byte[] codes = new byte[256];
    private static char[] alphabet;

    static {
        int i;
        for(i = 0; i < 256; ++i) {
            codes[i] = -1;
        }

        for(i = 65; i <= 90; ++i) {
            codes[i] = (byte)(i - 65);
        }

        for(i = 97; i <= 122; ++i) {
            codes[i] = (byte)(26 + i - 97);
        }

        for(i = 48; i <= 57; ++i) {
            codes[i] = (byte)(52 + i - 48);
        }

        codes[43] = 62;
        codes[47] = 63;
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
    }

    public Encrypt() {
    }

    public static void main(String[] args) {
        Encrypt my = new Encrypt();
        System.out.println("12345678" + my.passwordEncrypt("12345678"));
        System.out.println(my.isPass("123456", "D8E423A9D5EB97DA9E2D58CD57B92808"));
    }

    private boolean checkPassword(String str_pwds, String str_passwd) {
        try {
            String myinfo = str_pwds + "ectrip";
            MessageDigest alga = MessageDigest.getInstance("MD5");
            alga.update(myinfo.getBytes());
            byte[] digesta = alga.digest();
            String ls_str = this.byte2hex(digesta);
            return ls_str.equals(str_passwd);
        } catch (NoSuchAlgorithmException var7) {
            System.out.println("没有这个加密算法请检查JDK版本");
            return false;
        }
    }

    private String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for(int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }

            if (n < b.length - 1) {
                hs = hs;
            }
        }

        return hs.toUpperCase();
    }

    private String addPwd(String str_pwds) {
        try {
            String myinfo = str_pwds + "ectrip";
            MessageDigest alga = MessageDigest.getInstance("MD5");
            alga.update(myinfo.getBytes());
            byte[] digesta = alga.digest();
            String ls_str = this.byte2hex(digesta);
            return ls_str;
        } catch (NoSuchAlgorithmException var6) {
            System.out.println("没有这个加密算法请检查JDK版本");
            return null;
        }
    }

    public String passwordEncrypt(String str_pwds) {
        return this.addPwd(str_pwds);
    }

    public boolean isPass(String str_pwds, String str_passwd) {
        return this.checkPassword(str_pwds, str_passwd);
    }

    public static byte[] base64Encode(byte[] byteData) {
        if (byteData == null) {
            return null;
        } else {
            byte[] byteDest = new byte[(byteData.length + 2) / 3 * 4];
            int iSrcIdx = 0;

            int iDestIdx;
            for(iDestIdx = 0; iSrcIdx < byteData.length - 2; iSrcIdx += 3) {
                byteDest[iDestIdx++] = (byte)(byteData[iSrcIdx] >>> 2 & 63);
                byteDest[iDestIdx++] = (byte)(byteData[iSrcIdx + 1] >>> 4 & 15 | byteData[iSrcIdx] << 4 & 63);
                byteDest[iDestIdx++] = (byte)(byteData[iSrcIdx + 2] >>> 6 & 3 | byteData[iSrcIdx + 1] << 2 & 63);
                byteDest[iDestIdx++] = (byte)(byteData[iSrcIdx + 2] & 63);
            }

            if (iSrcIdx < byteData.length) {
                byteDest[iDestIdx++] = (byte)(byteData[iSrcIdx] >>> 2 & 63);
                if (iSrcIdx < byteData.length - 1) {
                    byteDest[iDestIdx++] = (byte)(byteData[iSrcIdx + 1] >>> 4 & 15 | byteData[iSrcIdx] << 4 & 63);
                    byteDest[iDestIdx++] = (byte)(byteData[iSrcIdx + 1] << 2 & 63);
                } else {
                    byteDest[iDestIdx++] = (byte)(byteData[iSrcIdx] << 4 & 63);
                }
            }

            for(iSrcIdx = 0; iSrcIdx < iDestIdx; ++iSrcIdx) {
                if (byteDest[iSrcIdx] < 26) {
                    byteDest[iSrcIdx] = (byte)(byteDest[iSrcIdx] + 65);
                } else if (byteDest[iSrcIdx] < 52) {
                    byteDest[iSrcIdx] = (byte)(byteDest[iSrcIdx] + 97 - 26);
                } else if (byteDest[iSrcIdx] < 62) {
                    byteDest[iSrcIdx] = (byte)(byteDest[iSrcIdx] + 48 - 52);
                } else if (byteDest[iSrcIdx] < 63) {
                    byteDest[iSrcIdx] = 43;
                } else {
                    byteDest[iSrcIdx] = 47;
                }
            }

            while(iSrcIdx < byteDest.length) {
                byteDest[iSrcIdx] = 61;
                ++iSrcIdx;
            }

            return byteDest;
        }
    }

    public String MD5Encrypt(String str_pwds) {
        return this.md5(str_pwds);
    }

    private String md5(String str_pwds) {
        try {
            MessageDigest alga = MessageDigest.getInstance("MD5");
            alga.update(str_pwds.getBytes());
            byte[] digesta = alga.digest();
            String ls_str = this.byte2hexold(digesta);
            return ls_str;
        } catch (NoSuchAlgorithmException var6) {
            System.out.println("没有这个加密算法请检查JDK版本");
            return null;
        }
    }

    public static String base64Encode(String strInput) {
        return strInput == null ? null : base64Encode(strInput, "GB2312");
    }

    public static final String base64Encode(String strInput, String charSet) {
        if (strInput == null) {
            return null;
        } else {
            String strOutput = null;
            byte[] byteData = new byte[strInput.length()];

            try {
                byteData = strInput.getBytes(charSet);
                strOutput = new String(base64Encode(byteData), charSet);
                return strOutput;
            } catch (UnsupportedEncodingException var5) {
                return null;
            }
        }
    }

    private String byte2hexold(byte[] b) {
        String hs = "";
        String stmp = "";

        for(int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }

            if (n < b.length - 1) {
                hs = hs;
            }
        }

        return hs;
    }

    public static String Base64Decode(String inputString) {
        try {
            byte[] ls_value = (byte[])null;
            ls_value = decode(inputString.toCharArray());
            String s2 = new String(ls_value, "GB2312");
            return s2;
        } catch (Exception var3) {
            return "";
        }
    }

    public static byte[] decode(char[] data) {
        int tempLen = data.length;

        int len;
        for(len = 0; len < data.length; ++len) {
            if (data[len] > 255 || codes[data[len]] < 0) {
                --tempLen;
            }
        }

        len = tempLen / 4 * 3;
        if (tempLen % 4 == 3) {
            len += 2;
        }

        if (tempLen % 4 == 2) {
            ++len;
        }

        byte[] out = new byte[len];
        int shift = 0;
        int accum = 0;
        int index = 0;

        for(int ix = 0; ix < data.length; ++ix) {
            int value = data[ix] > 255 ? -1 : codes[data[ix]];
            if (value >= 0) {
                accum <<= 6;
                shift += 6;
                accum |= value;
                if (shift >= 8) {
                    shift -= 8;
                    out[index++] = (byte)(accum >> shift & 255);
                }
            }
        }

        if (index != out.length) {
            throw new Error("Miscalculated data length (wrote " + index + " instead of " + out.length + ")");
        } else {
            return out;
        }
    }
}
