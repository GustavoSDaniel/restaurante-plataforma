package com.gustavosdaniel.restaurantReview.storage;

import com.gustavosdaniel.restaurantReview.common.BaseException;

public class StorageException extends BaseException {

    public StorageException() {
    }

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageException(Throwable cause) {
        super(cause);
    }
}
