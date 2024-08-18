package com.example.backend_sem2.service.serviceImpl;

import com.example.backend_sem2.dto.OrderRequest;
import com.example.backend_sem2.dto.OrderRequestWithLoginAccount;
import com.example.backend_sem2.dto.orderResponseInfoOverview.OrderResponseOverview;
import com.example.backend_sem2.dto.orderResponseInfo_InDetail.OrderResponse;
import com.example.backend_sem2.dto.SeatResponse;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.entity.Order;
import com.example.backend_sem2.entity.Slot;
import com.example.backend_sem2.entity.User;
import com.example.backend_sem2.enums.ActionTypeEnum;
import com.example.backend_sem2.enums.SeatStatusEnum;
import com.example.backend_sem2.exception.CustomErrorException;
import com.example.backend_sem2.mapper.OrderMapper;
import com.example.backend_sem2.mapper.SeatMapper;
import com.example.backend_sem2.model.RabbitMQMessage;
import com.example.backend_sem2.rabbitMQProducer.MessageProducer;
import com.example.backend_sem2.repository.*;
import com.example.backend_sem2.service.interfaceService.OrderService;
import com.example.backend_sem2.service.interfaceService.SeatService;
import com.example.backend_sem2.utils.AuthorityUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
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
    private MovieRepo movieRepo;
    private SeatService seatService;
    private SlotRepo slotRepo;
    private SeatMapper seatMapper;
    private OrderDetailRepo orderDetailRepo;
    private SeatRepo seatRepo;
    private OrderRepo orderRepo;
    private OrderMapper orderMapper;
    private MessageProducer messageProducer;
    private AuthorityUtility authorityUtility;
    private UserRepo userRepo;

    @Override
    @Transactional
    @SneakyThrows
    public OrderResponse createOrder(OrderRequest orderRequest) {

        Long customerAge = orderRequest.getCustomerAge() == null ? Long.valueOf(0) : orderRequest.getCustomerAge();
        List<Long> seatIdList = orderRequest.getSeatIdList();
        Long slotId = orderRequest.getSlotId();

        validateOrder(customerAge, slotId, seatIdList);
        Order order = orderMapper.toEntity(orderRequest);

        return handleCreateOrderProcessAfterValidate(order);
    }


    @Override
    public OrderResponse createOrderWithLoginAccount(HttpServletRequest request, OrderRequestWithLoginAccount orderRequestWithLoginAccount) {
        String username = authorityUtility.extractUsernameFromRequest(request);
        User loginUser = userRepo.getUserByUsername(username);

        validateOrder(loginUser.getAge().longValue(), orderRequestWithLoginAccount.getSlotId(), orderRequestWithLoginAccount.getSeatIdList());

        Order order = orderMapper.toEntity(orderRequestWithLoginAccount, loginUser);

        return handleCreateOrderProcessAfterValidate(order);
    }

    private boolean isAllSeatsExist(List<Long> seatIdList) {
        return seatIdList.stream().allMatch(seatRepo::existsById);
    }

    /*  check if all seat which is ordered still available  */
    /*  --- Solution 1: Check through "getAllSeatOfASlotWithStatus" --- */
    private boolean isSeatAvailableAndBelongToSlot(Long slotId, List<Long> seatIdList) {
        List<SeatResponse> seatResponseList = seatService.getAllSeatOfASlotWithStatus(slotId);

        Map<Long, SeatResponse> seatResponseMap = seatResponseList.stream()
                .collect(Collectors.toMap(SeatResponse::getSeatId, Function.identity()));

        return seatIdList.stream()
                .map(seatId -> {
                    SeatResponse seatResponse = seatResponseMap.get(seatId);
                    if (seatResponse == null)
                        throw new CustomErrorException(HttpStatus.BAD_REQUEST, "There are seats which do not belong to this slot or isn't exist");
                    return seatResponse;
                })
                .allMatch(seatResponse -> seatResponse.getStatus().equals(SeatStatusEnum.AVAILABLE));
    }

    /*  check if Slot began or not, we can not book a seat 10 minutes after movie started  */
    private boolean isSlotAvailableToBook(Slot slot) {
        if (slot == null)
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Slot is invalid! Please check again!");
        return ZonedDateTime.now().plusMinutes(10)
                .isBefore(slot.getStartTime());
    }

    /*  check Customer Age  */
    private boolean isEnoughAgeToBook(Long customerAge2, Long slotId) {
//        Long customerAge = orderRequest.getCustomerAge() == null ? Long.valueOf(0) : orderRequest.getCustomerAge();

        Movie bookedMovie = movieRepo.findMovieBySlotId(slotId).orElseThrow(() -> new CustomErrorException(HttpStatus.BAD_REQUEST, "This Movie is not exist!"));
        Long requiredAge = bookedMovie.getMovieLabel().getMinAge();

        return customerAge2 >= requiredAge;
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

    @NotNull
    private OrderResponse handleCreateOrderProcessAfterValidate(Order order) {
        try {
            System.out.println("Order ***");
            orderRepo.save(order);
            OrderResponse orderResponse = orderMapper.toDto(order);

            if(orderResponse.getCustomerEmail() != null){
                messageProducer.sendMessage(RabbitMQMessage.builder()
                        .actionType(ActionTypeEnum.ORDER_CREATED)
                        .data(orderResponse)
                        .destinationEmail(orderResponse.getCustomerEmail())
                        .build());
            }else {
                System.out.println("Customer Email is null ==> Do not send email to inform about \"Order Created\"");
            }
            return orderResponse;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Fail to create order!");
        }
    }

    private void validateOrder(Long customerAge, Long slotId, List<Long> seatIdList) {
        Slot slot = slotRepo.findById(slotId).orElseThrow(() -> new CustomErrorException(HttpStatus.BAD_REQUEST, "This slot is not exist!"));
        if (!isEnoughAgeToBook(customerAge, slotId))
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, String.format("You need to be older than %s years old to what this movie!", slot.getMovie().getMovieLabel().getMinAge()));
        /*  rarely wrong because in the main flow, customer always choose from exist seat   */
        if (!isAllSeatsExist(seatIdList))
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Seat is not exist!");
        if (!isSeatAvailableAndBelongToSlot(slotId, seatIdList))
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Some seats you choose have been books, please choose other one!");
    }

    @Override
    public List<OrderResponseOverview> listingOrderByUser(HttpServletRequest request) {
        String username = authorityUtility.extractUsernameFromRequest(request);

        List<Order> ordersByUser_Username = orderRepo.getOrderByUser_Username(username);

        return ordersByUser_Username.stream().map(orderMapper::toDtoOverview).toList();
    }
}
