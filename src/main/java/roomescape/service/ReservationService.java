package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationCreateValidator;
import roomescape.domain.Theme;
import roomescape.domain.TimeSlot;
import roomescape.domain.dto.ReservationRequest;
import roomescape.domain.dto.ReservationResponse;
import roomescape.exception.ErrorType;
import roomescape.exception.InvalidClientRequestException;
import roomescape.exception.ReservationFailException;
import roomescape.repository.ReservationDao;
import roomescape.repository.ThemeDao;
import roomescape.repository.TimeDao;

import java.util.List;

@Service
public class ReservationService {
    private final TimeDao timeDao;
    private final ReservationDao reservationDao;
    private final ThemeDao themeDao;

    public ReservationService(final TimeDao timeDao, final ReservationDao reservationDao, final ThemeDao themeDao) {
        this.timeDao = timeDao;
        this.reservationDao = reservationDao;
        this.themeDao = themeDao;
    }

    public List<ReservationResponse> findEntireReservationList() {
        return reservationDao.findAll();
    }

    public ReservationResponse create(final ReservationRequest reservationRequest) {
        validateDuplicatedReservation(reservationRequest);
        final TimeSlot timeSlot = getTimeSlot(reservationRequest);
        final Theme theme = getTheme(reservationRequest);
        final ReservationCreateValidator reservationCreateValidator = new ReservationCreateValidator(reservationRequest, timeSlot, theme);
        final Reservation newReservation = reservationCreateValidator.create();
        final Long reservationId = reservationDao.create(newReservation);
        return ReservationResponse.from(reservationId, newReservation);
    }

    private TimeSlot getTimeSlot(final ReservationRequest reservationRequest) {
        return timeDao.findById(reservationRequest.timeId())
                .orElseThrow(() -> new InvalidClientRequestException(ErrorType.NOT_EXIST_TIME, "timeId", reservationRequest.timeId().toString()));
    }

    private Theme getTheme(final ReservationRequest reservationRequest) {
        return themeDao.findById(reservationRequest.themeId())
                .orElseThrow(() -> new InvalidClientRequestException(ErrorType.NOT_EXIST_TIME, "themeId", reservationRequest.themeId().toString()));
    }

    private void validateDuplicatedReservation(final ReservationRequest reservationRequest) {
        if (reservationDao.isExists(reservationRequest.date(), reservationRequest.timeId(), reservationRequest.themeId())) {
            throw new ReservationFailException("이미 예약이 등록되어 있습니다.");
        }
    }

    public void delete(final Long id) {
        reservationDao.delete(id);
    }
}
