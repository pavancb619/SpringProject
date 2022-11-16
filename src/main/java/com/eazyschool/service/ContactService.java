package com.eazyschool.service;

import com.eazyschool.constants.ContactMsgStatus;
import com.eazyschool.entity.Contact;
import com.eazyschool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Contact saveMessageDetails(Contact contact) {
        contact.setStatus(ContactMsgStatus.OPEN);
        return contactRepository.save(contact);
    }
    public List<Contact> findMsgsWithOpenStatus(){
        List<Contact> contactMsgs = contactRepository.findByStatus(ContactMsgStatus.OPEN);
        return contactMsgs;
    }

    public Contact updateMsgStatus(int contactId){
        Optional<Contact> contact = contactRepository.findById(contactId);
        contact.ifPresent(contact1 -> contact1.setStatus(ContactMsgStatus.CLOSE));
        return contactRepository.save(contact.get());

    }
}
