package com.zx.Justmeplush;




import android.util.Log;

import java.lang.reflect.Field;
import java.security.KeyManagementException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

//adb push okhttp4.dex /data/local/tmp/
public class OKhttpAOp {

    public static String doIgnore(OkHttpClient.Builder builder){
        try {
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            //initSSLContext(builder);
            builder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("myhttp",message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY));
            return "xx";
          //return  setCertificateNull(builder);
        } catch (Throwable e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private static String setCertificateNull(OkHttpClient.Builder builder) {
        try {
            Class clazz = OkHttpClient.Builder.class;
            Field field = clazz.getDeclaredField("certificatePinner");
            field.setAccessible(true);
            field.set(builder,null);

            Field[] fields  =  clazz.getDeclaredFields();
            StringBuilder builder1 = new StringBuilder("Builder:\n");
            for (Field field1 : fields) {
                field1.setAccessible(true);
                builder1.append(field1.getName())
                        .append(":")
                        .append(field1.get(builder))
                .append(", ");
            }
            return builder1.toString();

        }catch (Throwable throwable){
            throwable.printStackTrace();
            return throwable.getMessage();
        }
    }

    private static void initSSLContext(OkHttpClient.Builder builder) {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            final TrustManager[] trustAllCerts0 = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
                                                       String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
                                                       String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[0];
                        }
                    }
            };

            try {
                sslContext.init(null, trustAllCerts0, new java.security.SecureRandom());
            } catch (KeyManagementException e) {
               e.printStackTrace();
            }

            builder.sslSocketFactory(sslContext.getSocketFactory(), new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


}
