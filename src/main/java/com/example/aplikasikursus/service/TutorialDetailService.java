package com.example.aplikasikursus.service;

import com.example.aplikasikursus.domain.Tutorial;
import com.example.aplikasikursus.domain.TutorialDetail;
import com.example.aplikasikursus.repository.TutorialDetailRepository;
import com.example.aplikasikursus.repository.TutorialRepository;
import com.example.aplikasikursus.result.Result;

import java.util.List;

public class TutorialDetailService {
    private TutorialDetailRepository tutorialDetailRepository;
    private TutorialRepository tutorialRepository;

    public TutorialDetailService(TutorialDetailRepository tutorialDetailRepository, TutorialRepository tutorialRepository) {
        this.tutorialDetailRepository = tutorialDetailRepository;
        this.tutorialRepository = tutorialRepository;
    }

    public List<TutorialDetail> findByTutorialId(int tutorialId) {
        return tutorialDetailRepository.findByTutorialId(tutorialId);
    }

    public int countPertemuanByTutorialId(int tutorialId) {
        return tutorialDetailRepository.countPertemuanByTutorialId(tutorialId);
    }
    public Result<TutorialDetail> insertOne(TutorialDetail tutorialDetail) {
        try {
            tutorialDetailRepository.insertOne(tutorialDetail);
            return new Result<>(tutorialDetail, false, "Tutorial detail inserted successfully.");
        } catch (Exception e) {
            return new Result<>(null, true, "Failed to insert tutorial detail: " + e.getMessage());
        }
    }
    public Result<TutorialDetail> insertLastOne(TutorialDetail tutorialDetail, Tutorial tutorial) {
        try {
            tutorialDetailRepository.insertOne(tutorialDetail);
            int tutorialId = tutorialDetail.getTutorialId();
            tutorialDetailRepository.deactivateByTutorialId(tutorialId);
            tutorialRepository.updateById(tutorialId, tutorial);
            return new Result<>(tutorialDetail, false, "Tutorial detail inserted successfully.");
        } catch (Exception e) {
            return new Result<>(null, true, "Failed to insert tutorial detail: " + e.getMessage());
        }
    }
}
