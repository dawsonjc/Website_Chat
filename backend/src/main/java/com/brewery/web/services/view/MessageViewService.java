package com.brewery.web.services.view;

import com.brewery.web.repositories.view.MessageViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageViewService {

    @Autowired
    private MessageViewRepository repo;

}
