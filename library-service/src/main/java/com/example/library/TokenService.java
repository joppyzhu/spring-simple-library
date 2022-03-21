package com.example.library;

import com.amdelamar.jotp.OTP;
import com.amdelamar.jotp.type.Type;
import com.example.library.models.TotpRequest;
import com.example.library.models.TotpResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TokenService {
    public TotpResponse totpCreate(TotpRequest request) {
        List<String> messages = new ArrayList<>();
        try {
            String code = OTP.create(request.getSecretKey(), OTP.timeInHex(System.currentTimeMillis()), 6, Type.TOTP);
            messages.add("code " + code);
        } catch (Exception e) {
            messages.add("Error");
        }
        return new TotpResponse(messages);
    }

    public TotpResponse totpValidate(TotpRequest request) {
        List<String> messages = new ArrayList<>();
        try {
            if(OTP.verify(request.getSecretKey(), OTP.timeInHex(System.currentTimeMillis()), request.getCode(), 6, Type.TOTP)) {
                messages.add("Success");
            } else {
                messages.add("Error");
            }
        } catch (Exception e) {

        }
        return new TotpResponse(messages);
    }
}
