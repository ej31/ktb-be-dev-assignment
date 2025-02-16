package com.urung.ktbbedevassignment.global.exception;

public enum ErrorResponseStatus {
    // 400 Bad Request
    MISSING_API_KEY(false, 4001, "API키를 확인해주세요."),
    INVALID_DATE_FORMAT(false, 4002, "날짜 형식이 맞지 않습니다."),
    INVALID_DATE_RANGE(false, 4003, "조회 시작일이 종료일보다 늦습니다."),
    MISSING_REQUIRED_PARAMETER(false, 4004, "입력값을 확인해주세요."),
    // 403 Forbidden
    INVALID_API_KEY(false, 4031, "API키가 유효하지 않습니다."),

    // 404 Not Found
    COMPANY_NOT_FOUND(false, 4041, "존재하지 않는 종목코드입니다."),
    STOCK_DATA_NOT_FOUND(false, 4042, "등록된 데이터가 없습니다."),
    API_NOT_FOUND(false, 4043, "존재하지 않는 API입니다."),

    // 429 Too Many Request


    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(false, 5001, "서버 내부에 오류가 발생하였습니다.")
    ;
    private final boolean isSuccess;
    private final int errorCode;
    private final String message;

    private ErrorResponseStatus(boolean isSuccess, int errorCode, String message){
        this.isSuccess = isSuccess;
        this.errorCode = errorCode;
        this.message = message;
    }

    public boolean isSuccess(){
        return this.isSuccess;
    }

    public int getErrorCode(){
        return this.errorCode;
    }

    public String getMessage(){
        return this.message;
    }


}
