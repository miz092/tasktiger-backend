package com.codecool.TaskTiger;

import com.codecool.TaskTiger.model.*;
import com.codecool.TaskTiger.model.user.TaskerInfo;
import com.codecool.TaskTiger.model.user.User;
import com.codecool.TaskTiger.repository.ReservationRepository;
import com.codecool.TaskTiger.repository.TimeSlotRepository;
import com.codecool.TaskTiger.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;


@SpringBootApplication
public class TaskTigerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskTigerApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner2(UserRepository userRepository, DataGenerator dataGenerator,
                                         TimeSlotRepository timeSlotRepository, ReservationRepository reservationRepository) {
        List<WorkType> feriTypes = List.of(WorkType.CLEANING, WorkType.GARDENING);
        List<WorkType> mariTypes = List.of(WorkType.DOG_WALKING, WorkType.GARDENING);
        List<WorkType> tecaTypes = List.of(WorkType.FURNITURE_ASSEMBLY, WorkType.MINOR_HOME_REPAIRS);
        List<WorkType> gyuriTypes = List.of(WorkType.CLEANING, WorkType.DOG_WALKING);
        List<WorkType> siyarTypes = List.of(WorkType.MINOR_HOME_REPAIRS, WorkType.HELP_MOVING, WorkType.CLEANING);

        return (args) -> {
//

            User user = DataGenerator.generateRandomUser("mari123", "Mária", "Kovács");
            saveUser(userRepository, timeSlotRepository, feriTypes, user);

            // Second user
            User user2 = DataGenerator.generateRandomUser("julia89", "Júlia", "Nagy");
            saveUser(userRepository, timeSlotRepository, mariTypes, user2);
// Third user
            User user3 = DataGenerator.generateRandomUser("david00", "Dávid", "Tóth");
            saveUser(userRepository, timeSlotRepository, feriTypes, user3);

// Fourth user
            User user4 = DataGenerator.generateRandomUser("zsuzsa32", "Zsuzsanna", "Balogh");
            User savedUser4 = userRepository.save(user4);
            TaskerInfo taskerInfo4 = TaskerInfo.builder()
                    .user(savedUser4)
                    .skills(tecaTypes).hourlyWage(12.0)
                    .build();
            setTaskerInfo(userRepository, timeSlotRepository, savedUser4, taskerInfo4);
// Fifth user
            User user5 = DataGenerator.generateRandomUser("krisztian77", "Krisztián", "Varga");
            User savedUser5 = userRepository.save(user5);
            TaskerInfo taskerInfo5 = TaskerInfo.builder()
                    .user(savedUser5)
                    .skills(gyuriTypes).hourlyWage(17.0)
                    .build();
            setTaskerInfo(userRepository, timeSlotRepository, savedUser5, taskerInfo5);


            User mari = DataGenerator.generateRandomUser("dénes5567", "Dénes", "Fülöp");
            User mariSaved = userRepository.save(mari);
            TaskerInfo mariTaskerInfo = TaskerInfo.builder()
                    .user(mariSaved)
                    .skills(mariTypes).hourlyWage(22.0)
                    .build();
            setTaskerInfo(userRepository, timeSlotRepository, mariSaved, mariTaskerInfo);
            User teca = DataGenerator.generateRandomUser("teca123", "Teca", "Kiss");
            User tecaSaved = userRepository.save(teca);
            TaskerInfo tecaTaskerInfo = TaskerInfo.builder()
                    .user(tecaSaved)
                    .skills(tecaTypes).hourlyWage(11.0)
                    .build();
            setTaskerInfo(userRepository, timeSlotRepository, tecaSaved, tecaTaskerInfo);
            User gyuri = DataGenerator.generateRandomUser("gyuri123", "Gyuri", "Kovács");
            User gyuriSaved = userRepository.save(gyuri);
            TaskerInfo gyuriTaskerInfo = TaskerInfo.builder()
                    .user(gyuriSaved)
                    .skills(gyuriTypes).hourlyWage(15.0)
                    .build();
            setTaskerInfo(userRepository, timeSlotRepository, gyuriSaved, gyuriTaskerInfo);
            User siyar = DataGenerator.generateRandomUser("siyar123", "Siyar", "Ahmad");
            User siyarSaved = userRepository.save(siyar);
            TaskerInfo siyarTaskerInfo = TaskerInfo.builder()
                    .user(siyarSaved)
                    .skills(siyarTypes).hourlyWage(20.0)
                    .build();
            setTaskerInfo(userRepository, timeSlotRepository, siyarSaved, siyarTaskerInfo);
            User zsolt = DataGenerator.generateRandomUser("zsolti", "Zsolt", "Béka");
            zsolt.setTasker(false);
            User savedZsolt = userRepository.save(zsolt);
            Address address = DataGenerator.generateRandomAddress();
            Reservation reservation = DataGenerator.generateRandomReservation(savedZsolt, siyar, address,
                    WorkType.HELP_MOVING);
            reservationRepository.save(reservation);
        };


    }

    private void setTaskerInfo(UserRepository userRepository, TimeSlotRepository timeSlotRepository, User savedUser4, TaskerInfo taskerInfo4) {
        savedUser4.setTaskerInfo(taskerInfo4);
        userRepository.save(savedUser4);
        User taskerUser3 = userRepository.getUserById(savedUser4.getId());
        for (int i = 0; i < 30; i++) {
            TimeSlot timeSlot = DataGenerator.generateRandomTimeSlot(taskerUser3);
            timeSlot.setTasker(taskerUser3.getTaskerInfo());
            timeSlotRepository.save(timeSlot);
        }
    }

    private void saveUser(UserRepository userRepository, TimeSlotRepository timeSlotRepository, List<WorkType> feriTypes, User user) {
        User savedUser = userRepository.save(user);

        TaskerInfo taskerInfo = TaskerInfo.builder()
                .user(savedUser).skills(feriTypes).hourlyWage(10.0)
                .build();
        setTaskerInfo(userRepository, timeSlotRepository, savedUser, taskerInfo);
    }

};


