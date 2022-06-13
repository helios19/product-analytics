package com.abn.amro.common.service;

import org.springframework.stereotype.Service;

/**
 * Counter service class providing a convenient {@link #getNextSequence(String)} sequence method
 * in an attempt to replicate * the behavior of the {@code GeneratedValue} and {@code SequenceGenerator}
 * annotations supported by most of JPA databases but missing in Mongodb.
 * <p>
 * <p>Note that a separate {@link com.abn.amro.common.utils.ClassUtils#COUNTERS_COLLECTION_NAME}
 * collection is used to hold the document sequences.</p>
 */
@Service
public class CounterServiceImpl implements CounterService {

//    @Autowired
//    private MongoOperations mongo;

    /**
     * Returns the next seauence integer for a given {@code collectionName}.
     *
     * @param collectionName Collection name
     * @return Next sequence integer
     */
    public int getNextSequence(String collectionName) {

//        if (!mongo.collectionExists(COUNTERS_COLLECTION_NAME)) {
//            //db.Counter.insert({ 'name' : 'user_id', sequence : 1}
//            mongo.insert(Counter.builder().id(collectionName).seq(0).build(), COUNTERS_COLLECTION_NAME);
//        }
//
//        Counter counter = mongo.findAndModify(
//                query(where("_id").is(collectionName)),
//                new Update().inc("seq", 1),
//                options().returnNew(true),
//                Counter.class);
//
//        return counter.getSeq();
        return 0;
    }
}