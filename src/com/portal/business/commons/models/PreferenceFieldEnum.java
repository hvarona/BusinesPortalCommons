package com.portal.business.commons.models;

public enum PreferenceFieldEnum {

    TIMEOUT_INACTIVE_SESSION(1L, PreferenceTypeValuesEnum.INTEGER),
    MAX_AMOUNT_DAILY_PER_STORE(2L, PreferenceTypeValuesEnum.FLOAT),
    MAX_AMOUNT_MONTHLY_PER_STORE(3l, PreferenceTypeValuesEnum.FLOAT),
    MAX_AMOUNT_YEARLY_PER_STORE(4L, PreferenceTypeValuesEnum.FLOAT),
    MAX_WRONG_LOGIN_INTENT_NUMBER(5l, PreferenceTypeValuesEnum.INTEGER),
    DISABLED_TRANSACTION(6L, PreferenceTypeValuesEnum.INTEGER),
    DEFAULT_SMS_PROVIDER(7L, PreferenceTypeValuesEnum.INTEGER),
    CYCLES(8L, PreferenceTypeValuesEnum.INTEGER),
    PERIOD(9L, PreferenceTypeValuesEnum.INTEGER),
    MAX_AMOUNT_PER_REMITTANCE(10L, PreferenceTypeValuesEnum.FLOAT),
    MAX_AMOUNT_DAILY_PER_REMITTENT(11L, PreferenceTypeValuesEnum.FLOAT),
    MAX_AMOUNT_MONTHLY_PER_REMITTENT(12L, PreferenceTypeValuesEnum.FLOAT),
    MAX_AMOUNT_YEARLY_PER_REMITTENT(13L, PreferenceTypeValuesEnum.FLOAT),
    MAX_AMOUNT_DAILY_PER_RECEIVER(14L, PreferenceTypeValuesEnum.FLOAT),
    MAX_AMOUNT_MONTHLY_PER_RECEIVER(15L, PreferenceTypeValuesEnum.FLOAT),
    MAX_AMOUNT_YEARLY_PER_RECEIVER(16L, PreferenceTypeValuesEnum.FLOAT),
    STIPULATED_PERIOD(17L, PreferenceTypeValuesEnum.FLOAT);
    
    private Long id;
    private PreferenceTypeValuesEnum type;

    private PreferenceFieldEnum(Long id, PreferenceTypeValuesEnum preferenceTypeValues) {
        this.id = id;
        this.type = preferenceTypeValues;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setType(PreferenceTypeValuesEnum type) {
        this.type = type;
    }

    public PreferenceTypeValuesEnum getType() {

        return type;

    }
}
