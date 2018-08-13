package com.PaymentGateway;

import org.apache.commons.codec.binary.Base64;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class PayWayApiCheckout {

    /*
    |--------------------------------------------------------------------------
    | ABA PayWay API URL
    |--------------------------------------------------------------------------
    | API URL that is provided by PayWay must be required in your post form
    |
    */
    private static String ABA_PAYWAY_API_URL="https://payway-dev.ababank.com/api/pwvkiriromv/";

    /*
    |--------------------------------------------------------------------------
    | ABA PayWay API KEY
    |--------------------------------------------------------------------------
    | API KEY that is generated and provided by PayWay must be required in your post form
    |
    */
    private static String ABA_PAYWAY_API_KEY="a03301dece230d884c3c9c6c68ad65ab";
    /*
    |--------------------------------------------------------------------------
    | ABA PayWay Merchant ID
    |--------------------------------------------------------------------------
    | Merchant ID that is generated and provided by PayWay must be required in your post form
    |
    */
    private static String ABA_PAYWAY_MERCHANT_ID = "vkirirom";


    /*
     * Returns the getHash
     * For PayWay security, you must follow the way of encryption for hash.
     *
     * @param string $transactionId
     * @param string $amount
     *
     * @return string getHash
     */
    public String getHash(String transactionId, String amount) {

        /* Example in PHP code
        $s = hash_hmac('sha256', 'Message', 'secret', true);
        echo base64_encode($s);
         */
//        $hash = base64_encode(hash_hmac("sha512", ABA_PAYWAY_MERCHANT_ID . transactionId . amount, ABA_PAYWAY_API_KEY, true));
//        return $hash;

        System.out.println("getHash");
        System.out.println("T_ID: "+transactionId);
        System.out.println("Amount: "+ amount);

        try {
            String secret = ABA_PAYWAY_API_KEY;
            String message = ABA_PAYWAY_MERCHANT_ID + transactionId + amount;

            Mac sha256_HMAC = Mac.getInstance("HmacSHA512");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA512");
            sha256_HMAC.init(secret_key);

            String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));
            System.out.println("Hash: "+hash);
            return hash;
        }
        catch (Exception e){
            System.out.println("Error");
            return null;
        }
    }


    /*
     * Returns the getApiUrl
     *
     * @return string getApiUrl
     */
    public static String getApiUrl() {
        System.out.println("getURL");
        return ABA_PAYWAY_API_URL;
    }


    public static void main(String args[]){
        float number = 888;
        String numberAsString = String.format ("%.2f", number);
        System.out.println(numberAsString);
    }
}