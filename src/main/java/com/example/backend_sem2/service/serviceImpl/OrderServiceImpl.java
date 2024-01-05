package com.example.backend_sem2.service.serviceImpl;

import com.example.backend_sem2.enums.SeatStatusEnum;
import com.example.backend_sem2.dto.OrderRequest;
import com.example.backend_sem2.dto.SeatResponse;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.entity.Order;
import com.example.backend_sem2.entity.OrderDetail;
import com.example.backend_sem2.entity.Slot;
import com.example.backend_sem2.exception.CustomErrorException;
import com.example.backend_sem2.mapper.OrderMapper;
import com.example.backend_sem2.mapper.SeatMapper;
import com.example.backend_sem2.repository.*;
import com.example.backend_sem2.service.interfaceService.OrderService;
import com.example.backend_sem2.service.interfaceService.SeatService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
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
    private OrderDetailRepo orderDetailRepo;
    private SeatRepo seatRepo;
    private OrderRepo orderRepo;
    private OrderMapper orderMapper;

    @Override
    @Transactional
    @SneakyThrows
    public Object createOrder(OrderRequest orderRequest) {

        Slot slot = slotRepo.findById(orderRequest.getSlotId()).orElseThrow(()-> new CustomErrorException(HttpStatus.BAD_REQUEST, "This slot is not exist!"));

        if (!isEnoughAgeToBook(orderRequest))
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, String.format("You need to be older than %s years old to what this movie!", slot.getMovie().getMovieLabel().getMinAge()));

        /*  rarely wrong because in the main flow, customer always choose from exist seat   */
        if (!isAllSeatsExist(orderRequest))
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Seat is not exist!");
        if (!isSeatAvailableAndBelongToSlot(orderRequest))
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Some seats you choose have been books, please choose other one!");

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
                    .customerEmail(orderRequest.getCustomerEmail())
                    .orderDetailList(orderDetails)
                    .slot(slot)
                    .build();

            orderRepo.save(order);

            return orderMapper.toDto(order);
        } catch (Exception e) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Fail to create order!");
        }
    }

    private boolean isAllSeatsExist(OrderRequest orderRequest) {
        return orderRequest.getSeatIdList().stream().allMatch(seatRepo::existsById);
    }

    /*  check if all seat which is ordered still available  */
    /*  --- Solution 1: Check through "getAllSeatOfASlotWithStatus" --- */
    private boolean isSeatAvailableAndBelongToSlot(OrderRequest orderRequest) {
        List<SeatResponse> seatResponseList = seatService.getAllSeatOfASlotWithStatus(orderRequest.getSlotId());

        Map<Long, SeatResponse> seatResponseMap = seatResponseList.stream()
                .collect(Collectors.toMap(SeatResponse::getSeatId, Function.identity()));

        return orderRequest.getSeatIdList().stream()
                .map(seatId -> {
                    SeatResponse seatResponse = seatResponseMap.get(seatId);
                    if(seatResponse == null) throw  new CustomErrorException(HttpStatus.BAD_REQUEST, "There are seats which do not belong to this slot or isn't exist");
                    return seatResponse;
                })
                .allMatch(seatResponse -> seatResponse.getStatus().equals(SeatStatusEnum.AVAILABLE));
    }

    /*  --- Solution 2: Check through "existsBySeat_IdAnAndOrder_Slot_Id" --- */
    /*  - Remove because this method can't check if this Seat belong to "Slot" or not*/
//    public boolean isSeatAvailable(OrderRequest orderRequest) {
//        return seatService.isAllSeatIsAvailableInSlot(orderRequest.getSeatIdList(), orderRequest.getSlotId());
//    }

    /*  check if Slot began or not, we can not book a seat 10 minutes after movie started  */
    private boolean isSlotAvailableToBook(Slot slot) {
        if (slot == null)
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Slot is invalid! Please check again!");
        return ZonedDateTime.now().plusMinutes(10)
                .isBefore(slot.getStartTime());
    }

    /*  check Customer Age  */
    private boolean isEnoughAgeToBook(OrderRequest orderRequest) {
        Long customerAge = orderRequest.getCustomerAge() == null ? Long.valueOf(0) : orderRequest.getCustomerAge();
        Movie bookedMovie = movieRepo.findMovieBySlotId(orderRequest.getSlotId()).orElseThrow(()-> new CustomErrorException(HttpStatus.BAD_REQUEST, "This Movie is not exist!"));
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
