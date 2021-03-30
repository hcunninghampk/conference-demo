package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Need to add annot8ns so that Spring knos that these are controllers.
@RestController//means will respond to payloads incoming and outgoing as JSON REST endpnts
@RequestMapping("/api/v1/sessions")
//tells the router what the mapping URL will look like, all reqs to this URL will be sent to this contrler
public class SessionsController {
    /*This class controls the API Endpoints which are REST based.*/

    @Autowired//Gives CRUD access to sessions db tbl
    private SessionRepository sessionRepository;

    //Need all these API endpnts for full CRUD contrlr: List, Get, Create, Update, & Delete

    //--------------------------------List, Get API endpnts for CRUD-----------------------------------

    /*This meth is an endpoint, will rtn all the sessions when called.  Have not given a specific request mapping. If
    make a call to '/api/v1/sessions' will rte to this meth. The annotation tells whch HTTP verb to use.  In this case
    'get' to call this endpnt. This called Class mapping.  Class request maping is mapped to '/api/v1/sessions' */
    @GetMapping
    public List<Session> listSessions() {
        /* Spring MVC will pass this list over to Jackson.  Jackson is a serialization lib, wch turns this list into
        JSON, & rtns that back to the calling meth.  */
        return sessionRepository.findAll();
    }

    //Another endpnt
    @GetMapping//Class mapping
    @RequestMapping("{session_id}")
//Additional mapping to Class mapping: Adds id to the url in the Class mapping: '/api/v1/sessions'.
    public Session getSession(@PathVariable Long session_id) {
        /* The id in the @RequestMapping specs a spec session and want to rtn that.  The @PathVariable annotation on
        the parameter id pulls the id off of the URL and injects/passes it to this meth auto w/ Spring MVC. Rtns and
        qrys the session for the id and rtns back to caller  */
        return sessionRepository.getOne(session_id);
        //Rtns the session back to caller in json payload
    }

    //--------------------------------Create, Update, & Delete API endpnts for CRUD-----------------------------------

    @PostMapping//Uses HTTP verb 'post'.  No added req mapping b/c using Class mapping to "/api/v1/sessions" endpoint
    public Session createSession(@RequestBody final Session session) {
        /* @RequestBody takes in all the attr's in a json payload and auto marshals them into a Session obj.  */
        return sessionRepository.saveAndFlush(session);//With JPA and Enities, can save to the db but doesn't actually
        // get committed until Flush it.
    }

    @RequestMapping(value = "{session_id}", method = RequestMethod.DELETE)
    public void deleteSession(@PathVariable Long session_id) {
        /* @RequestBody takes in all the attr's in a json payload and auto marshals them into a Session obj.  */
        //Also need to check for children records (ie: cascades) before deleting.  If the record has children records,
        // JPA won't allow to delete.  Will get a fk constraint violation
        sessionRepository.deleteById(session_id);
        //With JPA and Entities, can save to the db but doesn't actually get committed until Flush it.
    }


    //In Rest, have 2 verbs for updating a record: Put and Patch
    //Put updates all of the attrs on the record being updated and Patch allows only some of the atrs 2n updated
    @RequestMapping(value = "{session_id}", method = RequestMethod.PUT)
    public Session updateSession(@PathVariable Long session_id, @RequestBody Session session) {
        //b/c this is a Put expect all attrs 2b passed in.
        //TODO: Add validation all attrs r passed in. otherwise rtn a bad payload
        Session existingSession = sessionRepository.getOne(session_id);
        BeanUtils.copyProperties(session, existingSession, "session_id");
        return sessionRepository.saveAndFlush(existingSession);
    }



    /* Rest contrlrs in Spring MVC will always rtn type a 200s for a response status for all the calls.  Even w/ the
    @PostMapping. Spring will not infer anything from @PostMapping.  When post or create something in a db will get a 201
    back typically in the HTTP 'world', but not with Spring Rest contrlrs -- Spring will just give you a 200 response status.*/
    //@PostMapping
    //@ResponseStatus(HttpStatus.CREATED)//this annotation will override the typical spring 200 response status, but
    // we're not going to use this in this class b/c not going into REST.  Can add on own and define/annotate all the
    // correct mapping responses for calls to get, put, create, etc
    /*public Session createSession(@RequestBody final Session session){
        return sessionRepository.saveAndFlush(session);
    }*/

}
