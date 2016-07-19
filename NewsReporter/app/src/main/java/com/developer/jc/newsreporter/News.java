package com.developer.jc.newsreporter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a JSON News Object returned by query
 */
public class News {
        private String status;

        private String copyright;

        private Integer numResults;

        private List<Result> results = new ArrayList<Result>();

        /**
         *
         * @return
         * The status
         */
        public String getStatus() {
            return status;
        }

        /**
         *
         * @param status
         * The status
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         *
         * @return
         * The copyright
         */
        public String getCopyright() {
            return copyright;
        }

        /**
         *
         * @param copyright
         * The copyright
         */
        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        /**
         *
         * @return
         * The numResults
         */
        public Integer getNumResults() {
            return numResults;
        }

        /**
         *
         * @param numResults
         * The num_results
         */
        public void setNumResults(Integer numResults) {
            this.numResults = numResults;
        }

        /**
         *
         * @return
         * The results
         */
        public List<Result> getResults() {
            return results;
        }

        /**
         *
         * @param results
         * The results
         */
        public void setResults(List<Result> results) {
            this.results = results;
        }

    }
