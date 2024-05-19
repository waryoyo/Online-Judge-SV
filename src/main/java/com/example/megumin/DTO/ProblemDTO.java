package com.example.megumin.DTO;

import java.util.List;

public class ProblemDTO {
    private long submission_id;
    private SourceCodeDTO source_code;
    private long time_limit;
    private long memory_limit;
    private List<TestCaseDTO> test_cases;

    public SourceCodeDTO getSource_code() {
        return source_code;
    }

    public void setSource_code(SourceCodeDTO source_code) {
        this.source_code = source_code;
    }

    public long getTime_limit() {
        return time_limit;
    }

    public void setTime_limit(long time_limit) {
        this.time_limit = time_limit;
    }

    public long getMemory_limit() {
        return memory_limit;
    }

    public void setMemory_limit(long memory_limit) {
        this.memory_limit = memory_limit;
    }

    public List<TestCaseDTO> getTest_cases() {
        return test_cases;
    }

    public void setTest_cases(List<TestCaseDTO> test_cases) {
        this.test_cases = test_cases;
    }

    public long getSubmission_id() {
        return submission_id;
    }

    public void setSubmission_id(long submission_id) {
        this.submission_id = submission_id;
    }
}
