package com.br.edu.ufersa.prog_web.mec_tec.order.service;

import com.br.edu.ufersa.prog_web.mec_tec.order.api.dto.*;
import com.br.edu.ufersa.prog_web.mec_tec.order.api.enums.OrderStatus;
import com.br.edu.ufersa.prog_web.mec_tec.order.execption.OrderNotFoundExecption;
import com.br.edu.ufersa.prog_web.mec_tec.order.model.entity.Order;
import com.br.edu.ufersa.prog_web.mec_tec.order.model.entity.Order_Items;
import com.br.edu.ufersa.prog_web.mec_tec.order.model.repository.OrderItemsRepository;
import com.br.edu.ufersa.prog_web.mec_tec.order.model.repository.OrderRepository;
import com.br.edu.ufersa.prog_web.mec_tec.task.api.dto.ReturnTaskDTO;
import com.br.edu.ufersa.prog_web.mec_tec.task.model.entity.Task;
import com.br.edu.ufersa.prog_web.mec_tec.task.service.TaskService;
import org.hibernate.Remove;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final ModelMapper modelMapper;
    private final TaskService taskService;

    public OrderService(OrderRepository orderRepository,
                        OrderItemsRepository orderItemsRepository,
                        ModelMapper modelMapper, TaskService taskService) {
        this.orderRepository = orderRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.modelMapper = modelMapper;
        this.taskService = taskService;
    }

    @Transactional
    public ReturnOrderDTO closeOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundExecption("Order not found"));

        order.setStatus(OrderStatus.CLOSE);

        orderRepository.save(order);

        ReturnOrderDTO dto = modelMapper.map(order, ReturnOrderDTO.class);
        dto.setTasks(
                orderItemsRepository.findByOrderId(order.getId())
                        .stream()
                        .map(Order_Items::getTask)
                        .collect(Collectors.toList())
        );
        return dto;
    }
    @Transactional(readOnly = true)
    public ReturnOrderDTO findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundExecption("Order not found"));

        ReturnOrderDTO dto = modelMapper.map(order, ReturnOrderDTO.class);
        dto.setTasks(
                orderItemsRepository.findByOrderId(order.getId())
                        .stream()
                        .map(Order_Items::getTask)
                        .collect(Collectors.toList())
        );
        return dto;
    }
    @Transactional
    public ReturnOrderDTO create(CreateOrderDTO dto) {
        Order tempOrder = new Order();
        tempOrder.setDescription(dto.getDescription());
        Order order = orderRepository.save(tempOrder);

        dto.getTaskIds().forEach(taskId -> {
            ReturnTaskDTO taskDTO = taskService.findById(UUID.fromString(taskId));
            Task task = modelMapper.map(taskDTO, Task.class);

            CreateOrderItemDTO itemDTO = new CreateOrderItemDTO(order, task, Date.from(order.getCreatedAt()));
            Order_Items item = modelMapper.map(itemDTO, Order_Items.class);
            orderItemsRepository.save(item);
        });

        return modelMapper.map(order, ReturnOrderDTO.class);
    }

    @Transactional
    public Page<ReturnAllOrderDTO> findAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAllPaginate(pageable)
                .map(order -> {
                    ReturnAllOrderDTO dto = new ReturnAllOrderDTO();
                    dto.setOrderId(order.getId());
                    dto.setDescription(order.getDescription());
                    dto.setStatus(order.getStatus());
                    dto.setCreatedAt(Date.from(order.getCreatedAt()));
                    return dto;
                });
    }

    @Transactional
    public ReturnOrderDTO removeTasksToOrder(RemoveTaskOfOrderDTO dto) {
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new OrderNotFoundExecption("Order not found"));

        UUID taskId = UUID.fromString(dto.getTaskId());

        Order_Items item = orderItemsRepository
                .findByOrderIdAndTaskId(order.getId(), taskId)
                .orElseThrow(() -> new OrderNotFoundExecption("Task not found in this order"));

        orderItemsRepository.delete(item);

        ReturnOrderDTO returnDTO = modelMapper.map(order, ReturnOrderDTO.class);
        returnDTO.setTasks(
                orderItemsRepository.findByOrderId(order.getId())
                        .stream()
                        .map(Order_Items::getTask)
                        .collect(Collectors.toList())
        );
        return returnDTO;
    }


    @Transactional
    public void delete(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundExecption("Order not found"));
        orderRepository.delete(order);
    }

}
