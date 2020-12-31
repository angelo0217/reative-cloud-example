package com.stream.client.controller;

import java.util.List;

public interface EventListener<T> {
    /** 數據塊已準備就緒，*/
    void onDataChunk(String chunk);

    /** process完成 */
    void processComplete();
}

