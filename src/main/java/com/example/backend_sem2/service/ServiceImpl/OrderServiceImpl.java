package com.example.backend_sem2.service.ServiceImpl;

import com.example.backend_sem2.Enum.Status;
import com.example.backend_sem2.dto.OrderRequest;
import com.example.backend_sem2.dto.SeatResponse;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.entity.Order;
import com.example.backend_sem2.entity.OrderDetail;
import com.example.backend_sem2.entity.Slot;
import com.example.backend_sem2.exception.ApiError;
import com.example.backend_sem2.exception.CustomErrorException;
import com.example.backend_sem2.mapper.SeatMapper;
import com.example.backend_sem2.repository.*;
import com.example.backend_sem2.service.interfaceService.OrderService;
import com.example.backend_sem2.service.interfaceService.SeatService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
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
    private OrderDetailRepo orderDetailRepo;
    private SeatRepo seatRepo;
    private OrderRepo orderRepo;

    @Override
    @Transactional
    @SneakyThrows
    public ResponseEntity<?> createOrder(OrderRequest orderRequest) {

        Slot slot = slotRepo.findById(orderRequest.getSlotId()).orElse(null);
        List<CustomErrorException> createOrderExceptions = new ArrayList<>();
        if(!isEnoughAgeToBook(orderRequest)) createOrderExceptions.add(new CustomErrorException(HttpStatus.BAD_REQUEST, String.format("You need to be older than %s years old to what this movie!", slot.getMovie().getMovieLabel().getMinAge())));
        if(!isSeatAvailable(orderRequest)) createOrderExceptions.add(new CustomErrorException(HttpStatus.BAD_REQUEST, "Some seats you choose have been books, please choose other one!"));
        if(!isSlotAvailableToBook(slot)) createOrderExceptions.add(new CustomErrorException(HttpStatus.BAD_REQUEST, "This slot have been began, you can not book this slot!"));

        if (createOrderExceptions.isEmpty()) {
            try {
                List<OrderDetail> orderDetails = orderRequest.getSeatIdList().stream()
                        .map(id -> seatRepo.findById(id)
                                .orElseThrow(() -> new CustomErrorException(HttpStatus.BAD_REQUEST, "Seat is not exist!"))
                        )
                        .map(
                                seat -> OrderDetail.builder().seat(seat).build()
                        ).collect(Collectors.toList());
                Order order = Order.builder()
                        .customerName(orderRequest.getCustomerName())
                        .customerAddress(orderRequest.getCustomerAddress())
                        .customerAge(orderRequest.getCustomerAge())
                        .orderDetailList(orderDetails)
                        .slot(slot)
                        .build();

                orderRepo.save(order);
                return ResponseEntity.ok(order);
            } catch (Exception e) {
                e.printStackTrace();
//                throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Fail to create order!");
            }
        }

//        CustomErrorException throwoutException = new CustomErrorException(HttpStatus.BAD_REQUEST, "Multiple error",
//                createOrderExceptions.stream().map(CustomErrorException::getMessage).collect(Collectors.toList()));

        return ResponseEntity.badRequest().body(new ApiError(createOrderExceptions));
    }

    /*  check if all seat which is ordered still available  */
    /*  --- Solution 1: Check through "getAllSeatOfASlotWithStatus" --- */
    private boolean isSeatAvailable(OrderRequest orderRequest) {
        List<SeatResponse> seatResponseList = seatService.getAllSeatOfASlotWithStatus(orderRequest.getSlotId());

        Map<Long, SeatResponse> seatResponseMap = seatResponseList.stream()
                .collect(Collectors.toMap(SeatResponse::getSeatId, Function.identity()));

        return orderRequest.getSeatIdList().stream()
                .allMatch(seatId -> seatResponseMap.get(seatId).getStatus().equals(Status.AVAILABLE));
    }

    /*  --- Solution 2: Check through "existsBySeat_IdAnAndOrder_Slot_Id" --- */
    public boolean isSeatAvailable2(OrderRequest orderRequest) {
        return seatService.isAllSeatIsAvailableInSlot(orderRequest.getSeatIdList(), orderRequest.getSlotId());
    }

    /*  check if Slot began or not, we can not book a seat 10 minutes after movie started  */
    private boolean isSlotAvailableToBook(Slot slot) {
        if (slot == null)
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Slot is invalid! Please check again!");
        return ZonedDateTime.now().plusMinutes(10)
                .isBefore(slot.getStartTime());
    }

    /*  check Customer Age  */
    private boolean isEnoughAgeToBook(OrderRequest orderRequest) {
        Long customerAge = orderRequest.getCustomerAge() == null ?  Long.valueOf(0) : orderRequest.getCustomerAge();
        Movie bookedMovie = movieRepo.getById(orderRequest.getMovieId());
        Long requiredAge = bookedMovie.getMovieLabel().getMinAge();

        return customerAge >= requiredAge;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepo.getOrderByIdJoinFetchOrderDetail(id);
    }

    @Override
    public String getEmailByOrderId(Long id) {
        return orderRepo.getCustomerEmailById(id);
    }

    @Override
    public Order getOrderCustomById(Long id) {
        return orderRepo.getOrderCustomById(id);
    }
}
