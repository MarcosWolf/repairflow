package com.marcoswolf.repairflow.ui.handler.shared;

public interface IFormNormalizer<T extends IFormData> {
    T normalize(T data);
}