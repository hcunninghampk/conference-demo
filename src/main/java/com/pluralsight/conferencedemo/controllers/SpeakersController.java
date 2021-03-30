package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Need to add annot8ns so that Spring knos that these are controllers.
@RestController//means will respond to payloads incoming and outgoing as JSON REST endpnts
@RequestMapping("/api/v1/speakers")
//tells the router what the mapping URL will look like, all reqs to this URL will be sent to this contrler
public class SpeakersController {
    @Autowired//Gives CRUD access to speakers db tbl
    private SpeakerRepository speakerRepository;

    /*This meth is an endpoint, will rtn all the sessions when called.  Have not given a specific request mapping. If
    make a call to '/api/v1/speakers' will rte to this meth. The annotation tells whch HTTP verb to use.  In this case
    'get' to call this endpnt. This called Class mapping. */
    @GetMapping
    public List<Speaker> listSpeakers() {
        /* Spring MVC will pass this list over to Jackson.  Jackson is a serialization lib, wch turns this list into
        JSON, & rtns that back to the calling meth.  */
        return speakerRepository.findAll();
    }

    //Another endpnt
    @GetMapping//Class mapping
    @RequestMapping("{speaker_id}")
//Additional mapping to Class mapping: Adds id to the url in the Class mapping: '/api/v1/sessions'.
    public Speaker getSpeaker(@PathVariable Long speaker_id) {
        /* The id in the @RequestMapping specs a spec speaker and want to rtn that.  The @PathVariable annotation on
        the parameter id pulls the id off of the URL and injects/passes it to this meth auto w/ Spring MVC. Rtns and
        qrys the session for the id and rtns back to caller  */
        return speakerRepository.getOne(speaker_id);
        //Rtns the speaker back to caller in json payload
    }

    @PostMapping//Uses HTTP verb 'post'.  No added req mapping b/c using Class mapping to "/api/v1/sessions" endpoint
    public Speaker createSpeaker(@RequestBody final Speaker speaker) {
        /* @RequestBody takes in all the attr's in a json payload and auto marshals them into a Session obj.  */
        return speakerRepository.saveAndFlush(speaker);//With JPA and Enities, can save to the db but doesn't actually
        // get committed until Flush it.
    }

    @RequestMapping(value = "{speaker_id}", method = RequestMethod.DELETE)
    public void deleteSpeaker(@PathVariable Long speaker_id) {
        /* @RequestBody takes in all the attr's in a json payload and auto marshals them into a Session obj.  */
        //Also need to check for children records (ie: cascades) before deleting.  If the record has children records,
        // JPA won't allow to delete.  Will get a fk constraint violation
        speakerRepository.deleteById(speaker_id);
        //With JPA and Entities, can save to the db but doesn't actually get committed until Flush it.
    }


    //In Rest, have 2 verbs for updating a record: Put and Patch
    //Put updates all of the attrs on the record being updated and Patch allows only some of the atrs 2n updated
    @RequestMapping(value = "{speaker_id}", method = RequestMethod.PUT)
    public Speaker updateSpeaker(@PathVariable Long speaker_id, @RequestBody Speaker speaker) {
        //b/c this is a Put expect all attrs 2b passed in.
        //TODO: Add validation all attrs r passed in. otherwise rtn a bad payload
        Speaker existingSpeaker = speakerRepository.getOne(speaker_id);
        BeanUtils.copyProperties(speaker, existingSpeaker, "speaker_id");
        return speakerRepository.saveAndFlush(existingSpeaker);
    }


}
