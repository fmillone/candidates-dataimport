package io.virtualmind.candidates.dataimport.model

import groovy.transform.ToString

import java.time.LocalDate

@ToString(includePackage = false, includeNames = true, ignoreNulls = true)
class Candidate {

    String profile
    String name
    LocalDate lastContact
    LocalDate interview
    String status
    String notes
    Integer english
    String residence
    Long salary
    String phone
    String email
    String senderType
    String sender
    String follower

}
