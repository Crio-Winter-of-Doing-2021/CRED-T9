package com.crio.cred.service;

import com.crio.cred.repository.CardDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CardDetailsService {
    private final CardDetailsRepository cardDetailsRepository;
    private final ModelMapper modelMapper;
}
