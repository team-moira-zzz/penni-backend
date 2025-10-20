package com.moira.pennibackend.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 시스템 관련 에러코드
    INTERNAL_SYSTEM_ERROR("S001", "알 수 없는 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    KAKAO_LOGIN_ERROR("S002", "카카오 로그인 과정에서 알 수 없는 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    KAKAO_LOGIN_TOKEN_ERROR("S003", "카카오 토큰 발급 과정에서 오류가 발생했습니다."),
    KAKAO_LOGIN_USER_INFO_ERROR("S004", "카카오 유저 정보 조회 과정에서 오류가 발생했습니다."),
    NAVER_LOGIN_ERROR("S005", "네이버 로그인 과정에서 알 수 없는 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    NAVER_LOGIN_TOKEN_ERROR("S006", "네이버 토큰 발급 과정에서 오류가 발생했습니다."),
    NAVER_LOGIN_INVALID_STATE("S007", "로그인 요청이 만료되었거나 보안 토큰이 유효하지 않습니다. 다시 시도해주세요."),
    NAVER_LOGIN_USER_INFO_ERROR("S008", "네이버 유저 정보 조회 과정에서 오류가 발생했습니다."),

    // 권한 관련 에러코드
    INVALID_AUTHORIZATION_HEADER("A001", "Authorization 헤더에 토큰 정보가 포함되어 있지 않습니다."),
    EXPIRED_ATK("A002", "Access Token이 만료되었습니다."),
    INVALID_TOKEN1("A003", "토큰 서명이 유효하지 않거나 형식이 올바르지 않습니다."),
    INVALID_TOKEN2("A004", "유효하지 않은 토큰입니다."),

    // 그룹 관련 에러코드
    JOINED_TWO_OR_MORE_GROUP("G001", "알 수 없는 오류가 발생했습니다. 관리자에게 문의해주세요."),
    GROUP_ID_NOT_FOUND("G002", "잘못된 그룹 ID입니다. 접근 권한이 없습니다."),
    INVALID_CATEGORY_TYPE("G003", "올바르지 않은 카테고리 타입입니다."),
    INVALID_METHOD_TYPE("G004", "올바르지 않은 결제수단입니다."),
    INVALID_SORT_CONDITION("G005", "잘못된 정렬조건입니다."),
    ENTRY_NOT_BELONGS_TO_GROUP("G006", "잘못된 접근입니다. 그룹에 포함된 항목이 아닙니다."),
    ALREADY_USING_NICKNAME("G007", "이미 사용 중인 닉네임입니다.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
