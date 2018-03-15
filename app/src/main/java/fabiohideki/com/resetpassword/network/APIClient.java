package fabiohideki.com.resetpassword.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hidek on 12/03/2018.
 */

public class APIClient {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://us-central1-last-minute-app-71f82.cloudfunctions.net";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }



}
