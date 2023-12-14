package com.example.backend_sem2.service.ServiceImpl;

import com.example.backend_sem2.Enum.Status;
import com.example.backend_sem2.dto.OrderRequest;
import com.example.backend_sem2.dto.SeatResponse;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.entity.Order;
import com.example.backend_sem2.entity.OrderDetail;
import com.example.backend_sem2.entity.Slot;
import com.example.backend_sem2.exception.CustomErrorException;
import com.example.backend_sem2.mapper.SeatMapper;
import com.example.backend_sem2.repository.MovieRepo;
import com.example.backend_sem2.repository.SlotRepo;
import com.example.backend_sem2.service.interfaceService.OrderService;
import com.example.backend_sem2.service.interfaceService.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
//    private OrderRepo orderRepo;
    private MovieRepo movieRepo;
    private SeatService seatService;
    private SlotRepo slotRepo;
    private SeatMapper seatMapper;

    @Override
    public ResponseEntity<?> createOrder(OrderRequest orderRequest) {

        Slot slot = slotRepo.findById(orderRequest.getSlotId()).orElse(null);
        if (isEnoughAgeToBook(orderRequest) || isSlotAvailableToBook(slot) || isSeatAvailable(orderRequest)) {
            try {
                List<OrderDetail> orderDetails = orderRequest.getSeatIdList().stream()
                        .map(seatMapper::toEntity).map(
                                seat -> OrderDetail.builder().seat(seat).build()
                        ).collect(Collectors.toList());
                Order order = Order.builder()
                        .orderDetailList(orderDetails)
                        .slot(slot)
                        .build();

                return ResponseEntity.ok(order);
            } catch (Exception e) {
                e.printStackTrace();
//                throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Fail to create order!");
            }
        }
        return null;
    }

    /*  check if all seat which is ordered still available  */
    private boolean isSeatAvailable(OrderRequest orderRequest) {
        List<SeatResponse> seatResponseList = seatService.getAllSeatOfASlotWithStatus(orderRequest.getSlotId());

        Map<Long, SeatResponse> seatResponseMap = seatResponseList.stream()
                .collect(Collectors.toMap(SeatResponse::getSeatId, Function.identity()));

        return orderRequest.getSeatIdList().stream()
                .allMatch(seatId -> seatResponseMap.get(seatId).getStatus().equals(Status.AVAILABLE));
    }

    /*  check if Slot began or not, we can not book a seat 10 minutes after movie started  */
    private boolean isSlotAvailableToBook(Slot slot) {
        return slot.getStartTime().plusMinutes(10)
                .isBefore(ZonedDateTime.now());
    }

    /*  check Customer Age  */
    private boolean isEnoughAgeToBook(OrderRequest orderRequest) {
        Long customerAge = orderRequest.getCustomerAge() == null ? 0L : orderRequest.getCustomerAge();
        Movie bookedMovie = movieRepo.getById(orderRequest.getMovieId());
        Long requiredAge = bookedMovie.getMovieLabel().getMinAge();

        return customerAge >= requiredAge;
    }


}
