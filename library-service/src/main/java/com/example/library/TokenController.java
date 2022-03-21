package com.example.library;

import com.example.library.models.TotpRequest;
import com.example.library.models.TotpResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@ApiOperation(value = "/token/", tags = "Token API Service")
@RestController
public class TokenController {
    @Autowired
    TokenService tokenService;

    @ApiOperation(value = "Testing Create Key TOTP")
    @PostMapping(value = "/token/totp/create")
    public TotpResponse createTotp(@RequestBody TotpRequest request) {
        return tokenService.totpCreate(request);
    }

    @ApiOperation(value = "Testing Validate TOTP")
    @PostMapping(value = "/token/totp/validate")
    public TotpResponse validateTotp(@RequestBody TotpRequest request) {
        return tokenService.totpValidate(request);
    }
}
