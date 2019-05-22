package com.cmx.chatserver.es.repository;

import com.cmx.chatserver.es.document.MessageLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author cmx
 * @date 2019/5/22
 */
@Repository
public interface MessageRepository extends ElasticsearchRepository<MessageLog, String>{



}
