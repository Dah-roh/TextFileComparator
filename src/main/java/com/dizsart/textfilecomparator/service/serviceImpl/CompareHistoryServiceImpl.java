package com.dizsart.textfilecomparator.service.serviceImpl;

import com.dizsart.textfilecomparator.model.CompareHistory;
import com.dizsart.textfilecomparator.model.UploadFile;
import com.dizsart.textfilecomparator.repository.CompareHistoryRepository;
import com.dizsart.textfilecomparator.service.CompareHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.*;

@Service
public class CompareHistoryServiceImpl implements CompareHistoryService {

    private CompareHistoryRepository compareHistoryRepository;

    @Autowired
    public CompareHistoryServiceImpl(CompareHistoryRepository compareHistoryRepository) {
        this.compareHistoryRepository = compareHistoryRepository;
    }


    public static Set<String> getSetForTextFile (String file) {
    Set<String> set;
    set = new HashSet<>(Arrays.asList(file.split("[$&+,:;=?@#|'<>.^*()%!-]")));
    return set;
    }

    public Map<String,String> compareTextFiles(MultipartFile[] filePaths) throws IOException {
    List<Set<String>> sets = new ArrayList<>();
    Map<String, String> results = new HashMap<>();
    for (MultipartFile file: filePaths){
        String newFile = new String(file.getBytes(), StandardCharsets.UTF_8);
        sets.add(getSetForTextFile(newFile));
    }
        return getMapResults(sets, results);
    }

    @Transactional
    public Map<String,String> rerun(List<UploadFile> filePaths){
        List<Set<String>> sets = new ArrayList<>();
        Map<String, String> results = new HashMap<>();
        filePaths.forEach(file->{
            sets.add(getSetForTextFile(new String(file.getContent(), StandardCharsets.UTF_8)));
        });
        return getMapResults(sets, results);
    }
    @Cacheable("compareHistories")
    @Override
    public Optional<CompareHistory> findAllHistories(User principal) {
        return Optional.empty();
    }

    @Override
    @CachePut("compareHistories")
    public void save(CompareHistory compareHistory) {
        compareHistoryRepository.save(compareHistory);
    }

    private Map<String, String> getMapResults(List<Set<String>> sets, Map<String, String> results) {
        double totalFileSizes= (double) (sets.get(0).size()+sets.get(1).size())/2;
        sets.get(0).retainAll(sets.get(1));
        String similarities = String.join("* ", sets.get(0));
        double similaritySize = sets.get(0).size()==1?0.0:sets.get(0).size();
        double percentageOfSimilarities = similaritySize/totalFileSizes*100;
        results.put("Percentage", String.valueOf(percentageOfSimilarities));
        if(similarities.isEmpty()){
            similarities="No similarities";
        }
        results.put("Similarities", similarities);
        return results;
    }
}
