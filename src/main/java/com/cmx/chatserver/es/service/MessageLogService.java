package com.cmx.chatserver.es.service;

/**
 * @author cmx
 * @date 2019/5/22
 */

import com.cmx.chatserver.es.document.MessageLog;
import com.cmx.chatserver.es.repository.MessageRepository;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageLogService {

    @Autowired
    private MessageRepository messageRepository;

    public void addLog(String message){
        MessageLog messageLog = new MessageLog();
        messageLog.setMessageId(UUID.randomUUID().toString());
        messageLog.setMessage(message);
        messageRepository.save(messageLog);

    }

    public void search(){
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("message", "春");
        Page<MessageLog> search = messageRepository.search(queryBuilder, Pageable.unpaged());
        search.forEach(System.out::println);
    }


}
