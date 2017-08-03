package com.framework.core.constants;

public class StatusCodeConstants {
    public enum StatusCode {

        ON_SALE(0, "网销"), UN_SALE(1, "非网销");

        private int status;
        private String comment;

        private StatusCode() {
        }

        private StatusCode(int status, String comment) {
            this.status = status;
            this.comment = comment;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

    }
}
