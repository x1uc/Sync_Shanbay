package top.x1uc;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.x1uc.Entity.YouDaoData;
import top.x1uc.Entity.YouDaoWordItem;
import top.x1uc.Mapper.WordMapper;
import top.x1uc.ShanBay.ShanBayCollection;
import top.x1uc.YouDao.CollectionWordList;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class Schedule {


    private final ShanBayCollection shanBayCollection;
    private final CollectionWordList collectionWordList;
    private static Integer count = 0;
    private final WordMapper wordMapper;

    @Scheduled(fixedRate = 3600000)
    public void TransCollection() {
        log.warn("这是第{}次同步", count++);
        YouDaoData collectionWords = collectionWordList.getCollectionWordList();
        Set<String> allWords = new HashSet<>();
        allWords = wordMapper.getAll();
        YouDaoData uniqueWords = new YouDaoData();
        System.out.println("从有道同步" + collectionWords.getItemList().size() + "个单词");
        System.out.println("去重中...");
        for (YouDaoWordItem youDaoWordItem : collectionWords.getItemList()) {
            if (allWords.contains(youDaoWordItem.getWord()))
                continue;
            uniqueWords.getItemList().add(youDaoWordItem);
            uniqueWords.setTotal(uniqueWords.getTotal() + 1);
            wordMapper.insert(youDaoWordItem.getWord());
        }
        System.out.println("去重后，实际需要需要同步" + uniqueWords.getTotal());
        for (YouDaoWordItem youDaoWordItem : uniqueWords.getItemList()) {
            System.out.println(youDaoWordItem.getWord());
        }
        if (uniqueWords.getItemList().size() == 0) {
            System.out.println("同步完成");
        }
        shanBayCollection.addWordToShanBay(uniqueWords);
    }
}
