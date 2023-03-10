package com.codecool.TaskTiger.model;


import com.codecool.TaskTiger.model.user.TaskerInfo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.MERGE;

@Entity(name = "Timeslot")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot {

    @SequenceGenerator(name = "timeslot_sequence",
            sequenceName = "timeslot_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "timeslot_sequence")
    @Id
    @Column(name = "timeslot_id", nullable = false)
    private Long id;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "is_reserved", nullable = false)
    private boolean isReserved;

    @ManyToOne(cascade = MERGE)
    @JsonIgnore
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne(cascade = MERGE)
    @JsonBackReference
    @JoinColumn(name = "tasker_user_id", nullable = false)
    private TaskerInfo tasker;
}
