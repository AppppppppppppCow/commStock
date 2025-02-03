package com.commuStock.commuStock.core.response;

import com.commuStock.commuStock.core.enums.ErrorCode;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse <T>{
    private int status;  // 예: "success" 또는 "error"
    private String message; // 사용자에게 전달할 메시지
    private T data;         // 실제 응답 데이터 (제네릭 타입)


    public ApiResponse(T data) {
        this.status = ErrorCode.SUCCESS.getCode();
        this.message = ErrorCode.SUCCESS.getMessage();
        this.data = data;
    }

//    public ApiResponse(int status, String message, T data) {
//        this.status = status;
//        this.message = message;
//        this.data = data;
//    }


}
