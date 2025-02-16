package ktb.back.assignment.global.validator;

import ktb.back.assignment.global.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class RequestValidator {
    private static final String VALID_API_KEY = "c18aa07f-f005-4c2f-b6db-dff8294e6b5e";

    public void validateApiKey(String apiKey){
        if(apiKey == null || apiKey.isBlank()){
            throw new ApiException("API Key가 없습니다.", HttpStatus.BAD_REQUEST);
        }else if(!apiKey.equals(VALID_API_KEY)){
            throw new ApiException("잘못된 API Key입니다.",HttpStatus.FORBIDDEN);
        }
    }

    public void validateRequestParams(String companyCode,String startDate, String endDate){
        if(companyCode == null || companyCode.isBlank()){
            throw new ApiException("companyCode 파라미터가 필요합니다.", HttpStatus.BAD_REQUEST);
        } else if (startDate == null || startDate.isBlank()) {
            throw new ApiException("startDate 파라미터가 필요합니다.", HttpStatus.BAD_REQUEST);
        } else if (endDate == null || endDate.isBlank()) {
            throw new ApiException("endDate 파라미터가 필요합니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
