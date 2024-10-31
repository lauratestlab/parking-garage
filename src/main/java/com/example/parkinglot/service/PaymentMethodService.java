package com.example.parkinglot.service;

import com.example.parkinglot.dto.PaymentMethodDTO;
import com.example.parkinglot.entity.PaymentMethod;
import com.example.parkinglot.entity.User;
import com.example.parkinglot.exception.OperationForbiddenForCurrentUser;
import com.example.parkinglot.exception.UserNotFoundException;
import com.example.parkinglot.mapper.PaymentMethodMapper;
import com.example.parkinglot.repo.PaymentMethodRepository;
import com.example.parkinglot.repo.UserRepository;
import com.example.parkinglot.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PaymentMethodService {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentMethodService.class);

    private final PaymentMethodRepository paymentMethodRepository;

    private final PaymentMethodMapper paymentMethodMapper;
    private final UserRepository userRepository;

    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository, PaymentMethodMapper paymentMethodMapper, UserRepository userRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentMethodMapper = paymentMethodMapper;
        this.userRepository = userRepository;
    }

    public PaymentMethodDTO save(PaymentMethodDTO paymentMethodDTO, String login) {
        LOG.debug("Request to save PaymentMethod : {}", paymentMethodDTO);
        User user = userRepository.findOneByLogin(login).orElseThrow(() -> new UsernameNotFoundException(login));
        PaymentMethod paymentMethod = paymentMethodMapper.toEntity(paymentMethodDTO);
        paymentMethod.setUser(user);
        paymentMethod = paymentMethodRepository.save(paymentMethod);
        return paymentMethodMapper.toDto(paymentMethod);
    }

    public PaymentMethodDTO update(PaymentMethodDTO paymentMethodDTO) {
        LOG.debug("Request to update PaymentMethod : {}", paymentMethodDTO);
        PaymentMethod paymentMethod = paymentMethodMapper.toEntity(paymentMethodDTO);
        String login = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new SessionAuthenticationException("Authentication failure"));
        User user = userRepository.findOneByLogin(login)
                .orElseThrow(()->new UserNotFoundException(login));
        paymentMethod.setUser(user);
        paymentMethod = paymentMethodRepository.save(paymentMethod);
        return paymentMethodMapper.toDto(paymentMethod);
    }

    @Transactional(readOnly = true)
    public Page<PaymentMethodDTO> findAll(Pageable pageable, String login) {
        LOG.debug("Request to get all PaymentMethods");
        return paymentMethodRepository.findByUserIsCurrentUser(pageable, login).map(paymentMethodMapper::toDto);
    }

    public Page<PaymentMethodDTO> findAllWithEagerRelationships(Pageable pageable, String login) {
        return paymentMethodRepository.findAllWithEagerRelationships(pageable, login).map(paymentMethodMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<PaymentMethodDTO> findOne(Long id) {
        LOG.debug("Request to get PaymentMethod : {}", id);
        String login = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new SessionAuthenticationException("Authentication failure"));
        return paymentMethodRepository.findOne(id, login).map(paymentMethodMapper::toDto);
    }

    public void delete(Long id) {
        LOG.debug("Request to delete PaymentMethod : {}", id);

        String login = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new SessionAuthenticationException("Authentication failure"));

        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findOne(id, login);
        paymentMethod.ifPresent(paymentMethodRepository::delete);
        paymentMethod.orElseThrow(OperationForbiddenForCurrentUser::new);
    }
}
