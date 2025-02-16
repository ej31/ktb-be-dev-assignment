package org.ktb.dev.assignment.core.exception;

import java.io.Serializable;

public interface ErrorCode extends Serializable {
    int getStatus();

    String getCode();

    String getMessage();
}