package com.manuh.droidcon2018;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ApolloClient {
    private static final String BASE_URL = "https://api.graph.cool/simple/v1/cjmroit1737n40192oe6p80n5";
    private static com.apollographql.apollo.ApolloClient apolloClient;

    public static com.apollographql.apollo.ApolloClient getApolloClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        apolloClient = com.apollographql.apollo.ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();

        return apolloClient;

    }

}
