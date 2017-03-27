package encrypt.test.com.encryptiontext.net;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {
    @POST("14vqhcb1")
    Call<String> sendEncryptedString(@Query("text") String encryptedString);
}
