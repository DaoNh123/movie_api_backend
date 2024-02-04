package com.example.backend_sem2.dto;

import com.example.backend_sem2.dto.dtoForMovie.MovieResponseOverview;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SlotResponse {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime endTime;
    private MovieResponseOverview movie;
    private String theaterRoom;

//    public static SlotResponseBuilder builder() {
//        return new SlotResponseBuilder();
//    }
//
//    public static class SlotResponseBuilder {
//        private Long id;
//        private ZonedDateTime startTime;
//        private ZonedDateTime endTime;
//        private String theaterRoom;
//
//        SlotResponseBuilder() {
//        }
//
//        public SlotResponseBuilder id(Long id) {
//            this.id = id;
//            return this;
//        }
//
//        @JsonFormat
//        public SlotResponseBuilder startTime(ZonedDateTime startTime) {
//            this.startTime = startTime;
//            return this;
//        }
//
//        public SlotResponseBuilder endTime(ZonedDateTime endTime) {
//            this.endTime = endTime;
//            return this;
//        }
//
//        public SlotResponseBuilder theaterRoom(String theaterRoom) {
//            this.theaterRoom = theaterRoom;
//            return this;
//        }
//
//        public SlotResponse build() {
//            return new SlotResponse(this.id, this.startTime, this.endTime, this.theaterRoom);
//        }
//
//        public String toString() {
//            return "SlotResponse.SlotResponseBuilder(id=" + this.id + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", theaterRoom=" + this.theaterRoom + ")";
//        }
//    }
}
