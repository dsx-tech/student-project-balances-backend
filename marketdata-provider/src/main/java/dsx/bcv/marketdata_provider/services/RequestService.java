package dsx.bcv.marketdata_provider.services;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RequestService {

    public String doGetRequest(String url) throws IOException {
        log.trace("doGetRequest called. Url: {}", url);
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.MINUTES)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        log.trace("Response isSuccessful: {}", response.isSuccessful());
        log.trace("Response message: {}", response.message());
        log.trace("Response code: {}", response.code());
        return Objects.requireNonNull(response.body()).string();
    }
}
