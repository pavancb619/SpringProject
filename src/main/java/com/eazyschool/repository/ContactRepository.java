package com.eazyschool.repository;

import com.eazyschool.constants.ContactMsgStatus;
import com.eazyschool.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    List<Contact> findByStatus(ContactMsgStatus status);
}
