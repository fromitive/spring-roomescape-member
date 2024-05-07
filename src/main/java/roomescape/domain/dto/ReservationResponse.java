package roomescape.domain.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;

public record ReservationResponse(Long id, String name, LocalDate date, TimeSlotResponse time, ThemeResponse theme) {
    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                TimeSlotResponse.from(reservation.getTime()),
                ThemeResponse.from(reservation.getTheme())
        );
    }
}
