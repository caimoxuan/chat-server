package com.cmx.chatserver.es.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author cmx
 * @date 2019/5/22
 */
@Data
@Document(indexName = "message_log")
public class MessageLog {

    @Id
    private String messageId;

    private String message;


}
