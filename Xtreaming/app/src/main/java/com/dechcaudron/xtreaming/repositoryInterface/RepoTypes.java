package com.dechcaudron.xtreaming.repositoryInterface;

public enum RepoTypes {
    Koel(0);

    private final int code;

    RepoTypes(int code) {
        this.code = code;
    }

    private int getCode() {
        return code;
    }
}
