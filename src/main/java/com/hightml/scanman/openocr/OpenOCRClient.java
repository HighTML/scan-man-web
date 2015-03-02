package com.hightml.scanman.openocr;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Please enter description here.
 * <p>
 * User: marcel
 * Date: 02/03/15
 * Time: 22:55
 * <p>
 * Copyright by HighTML.
 */
@Component
public class OpenOCRClient {




    @Getter
    public class PostData {
        private String img_url;

        private static final String engine = "tesseract";

        public PostData(String url){
            this.img_url = url;
        }
    }


//    public static void main(String[] a) {
//        OpenOCRClient client = new OpenOCRClient();
//        System.out.println(client.getText("http://tleyden-misc.s3.amazonaws.com/blog_images/ocr_test.png"));
//
//    }



    public String getText(String url) {
        PostData data = new PostData(url);
        RestTemplate rt = new RestTemplate();
        return rt.postForObject("http://192.168.1.150:8080/ocr", data, String.class);
    }
}
