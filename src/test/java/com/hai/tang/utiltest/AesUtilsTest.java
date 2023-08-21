package com.hai.tang.utiltest;

import com.hai.tang.util.AesUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Aes加密解密测试类")
public class AesUtilsTest {

    @DisplayName("测试Aes加密解密")
    @Test
    public void aes() {
        //原始字符串
        String str = "Hello!";

        System.out.println("原始字符串:" + str);
        System.out.println();

        String encryption = null;
        String decrypt = null;
        try {
            encryption = AesUtils.encrypt(str, AesUtils.AES_KEY, AesUtils.AES_VECTOR);//普通加密
            decrypt = AesUtils.decrypt(encryption, AesUtils.AES_KEY, AesUtils.AES_VECTOR);//普通解密
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertThat(str).isEqualTo(decrypt);
        System.out.println("加密后:" + encryption);
        System.out.println("解密后:" + decrypt);
        System.out.println();

        String encryption1 = AesUtils.encryptAd(str, AesUtils.AES_KEY, AesUtils.AES_VECTOR);//增强加密
        String decrypt1 = AesUtils.decryptAd(encryption1, AesUtils.AES_KEY, AesUtils.AES_VECTOR);//增强解密
        assertThat(str).isEqualTo(decrypt1);
        System.out.println("增强加密后:" + encryption1);
        System.out.println("增强解密后:" + decrypt1);
    }
}
