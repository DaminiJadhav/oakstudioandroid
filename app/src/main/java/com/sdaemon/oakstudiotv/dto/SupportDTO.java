package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SupportDTO {

    @SerializedName("Questions")
    @Expose
    private List<Questions> questions;

    @SerializedName("Answers")
    @Expose
    private List<Answers> Answers;

    public List<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }

    public List<SupportDTO.Answers> getAnswers() {
        return Answers;
    }

    public void setAnswers(List<SupportDTO.Answers> answers) {
        Answers = answers;
    }

    public class Questions{
        @SerializedName("Qid")
        @Expose
        private Integer Qid;

        @SerializedName("Question")
        @Expose
        private String Question;

        @SerializedName("CreatedDate")
        @Expose
        private String CreatedDate;

        public Integer getQid() {
            return Qid;
        }

        public void setQid(Integer qid) {
            Qid = qid;
        }

        public String getQuestion() {
            return Question;
        }

        public void setQuestion(String question) {
            Question = question;
        }

        public String getCreatedDate() {
            return CreatedDate;
        }

        public void setCreatedDate(String createdDate) {
            CreatedDate = createdDate;
        }
    }

    public class Answers{
        @SerializedName("Ansid")
        @Expose
        private Integer ansid;

        @SerializedName("Qid")
        @Expose
        private Integer Qid;

        @SerializedName("Answer")
        @Expose
        private String Answer;

        public Integer getAnsid() {
            return ansid;
        }

        public void setAnsid(Integer ansid) {
            this.ansid = ansid;
        }

        public Integer getQid() {
            return Qid;
        }

        public void setQid(Integer qid) {
            Qid = qid;
        }

        public String getAnswer() {
            return Answer;
        }

        public void setAnswer(String answer) {
            Answer = answer;
        }
    }

}
