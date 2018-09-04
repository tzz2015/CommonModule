package com.zyf.fwms.commonlibrary.http;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.zyf.fwms.commonlibrary.model.mine.AccountInfo;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.LogUtil;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * 刘宇飞 创建 on 2017/5/15.
 * 描述：
 */

public class HttpUtils {
    private Gson gson;
    private static volatile HttpUtils instance;
    private static volatile Object httpTask;
    private static HashMap<String, Object> customHttpMaps = new HashMap<>();

    /**
     * 单例
     *
     * @return
     */
    public static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null) {
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }


    private Retrofit.Builder getBuilder(String apiUrl) {

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getOkClient());
        builder.baseUrl(apiUrl);//设置远程地址
        builder.addConverterFactory(new NullOnEmptyConverterFactory());
        builder.addConverterFactory(GsonConverterFactory.create(getGson()));
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        return builder;
    }

    private OkHttpClient getOkClient() {
        //使用OkHttp拦截器可以指定需要的header给每一个Http请求
        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())//日志
//            .addInterceptor(new NotEdcodeLoggingInterceptor())//不加密
                .addNetworkInterceptor(new RequestHeaderInterceptor())//请求头
                .build();
        return client;
    }

    public Gson getGson() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.setLenient();
            builder.setFieldNamingStrategy(new AnnotateNaming());
            builder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory());
            builder.serializeNulls();
            gson = builder.create();
        }
        return gson;
    }

    public class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringNullAdapter();
        }
    }

    public class StringNullAdapter extends TypeAdapter<String> {
        @Override
        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }

        @Override
        public void write(JsonWriter writer, String value) throws IOException {
            if (value == null) {
                writer.value("");
                return;
            }
            writer.value(value);
        }
    }

    private static class AnnotateNaming implements FieldNamingStrategy {
        @Override
        public String translateName(Field field) {
            ParamNames a = field.getAnnotation(ParamNames.class);
            return a != null ? a.value() : FieldNamingPolicy.IDENTITY.translateName(field);
        }
    }

    /**
     * 一般请求
     *
     * @param a
     * @param <T>
     * @return
     */
    public <T> T createRequest(Class<T> a) {
        if (httpTask == null) {
            synchronized (HttpUtils.class) {
                if (httpTask == null) {
                    httpTask = getBuilder(Api.HOST_URL).build().create(a);
                }
            }
        }
        return (T) httpTask;
    }


    /**
     * 一般请求
     *
     * @param a
     * @param <T>
     * @return
     */
    public <T> T createRequest(Class<T> a, String baseUrl) {
        if (!customHttpMaps.containsKey(baseUrl)) {
            customHttpMaps.put(baseUrl, getBuilder(baseUrl).build().create(a));
        }
        return (T) customHttpMaps.get(baseUrl);
    }

    /**
     * 清空重建 防止切换账号 使用共同的请求
     */
    public void clearHttp() {
        instance = null;
        httpTask = null;
        customHttpMaps.clear();
        gson = null;
    }

    public class RequestHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
           String authorization="";
            String token = AccountInfo.getInstance().getToken();
            if(CommonUtils.isNotEmpty(token)) {
                authorization = "Bearer " + token;
                LogUtil.getInstance().e(authorization);
            }
            Request newRequest = null;
                newRequest = chain.request().newBuilder()
                        .addHeader("Authorization",authorization)
                        .build();


            return chain.proceed(newRequest);
        }
    }




}
