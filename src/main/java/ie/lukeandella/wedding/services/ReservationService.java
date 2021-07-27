package ie.lukeandella.wedding.services;


import ie.lukeandella.wedding.models.Gift;
import ie.lukeandella.wedding.models.Reservation;
import ie.lukeandella.wedding.models.User;
import ie.lukeandella.wedding.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

}
