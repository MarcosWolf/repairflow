package com.marcoswolf.repairflow.ui.handler.shared;

public abstract class BaseFormNormalizer<T extends IFormData> implements IFormNormalizer<T> {
    protected String trim(String value) {
        return value == null ? null : value.trim();
    }

    protected String lower(String value) {
        return value == null ? null : value.trim().toLowerCase();
    }
}
