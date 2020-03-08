package de.gymondo.samples.restapi.controller;

import de.gymondo.samples.commons.repository.SubscriptionRepository;
import de.gymondo.samples.commons.repository.UserRepository;
import de.gymondo.samples.restapi.dto.SubscriptionV1Dto;
import de.gymondo.samples.restapi.dto.UserV1Dto;
import de.gymondo.samples.restapi.transformation.TransformationsV1;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * User controller.
 *
 * @author Rui Vilao (rui@gymondo.de)
 */
@RestController
@RequestMapping("/api/v1/users")
@ResponseBody
public class UserController {

    private TransformationsV1 transformations = new TransformationsV1();

    // NOTE: In a real approach this would be calling the service layer and not the
    // persistence one directly.
    private UserRepository userRepository = new UserRepository();
    private SubscriptionRepository subscriptionRepository = new SubscriptionRepository();

    /**
     * Lists all the users.
     *
     * @return The list of users.
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<UserV1Dto> list() {
        return transformations.user2Dto(userRepository.findAll());
    }

    /**
     * Returns user by id
     *
     * @param id of user
     * @return The user
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public UserV1Dto getUserById(@RequestParam("id") int id) {
        return transformations.user2Dto(userRepository.findOne(id));
    }

    /**
     * Lists all subscriptions of user with id userId
     * @param userId id of user
     * @return The Subscriptions
     */
    @RequestMapping(value = "/subscriptions", method = RequestMethod.GET)
    public List<SubscriptionV1Dto> getSubscriptionsOfUserId(@RequestParam("userId") int userId) {
        return transformations.subscription2Dto(subscriptionRepository.findByUserId(userId));
    }

    /**
     * Lists all subscriptions which are valid on a specified date
     *
     * @param date on which subscriptions should be valid. If no date is specified, defaults to LocalDate.now()
     * @return The valid subscriptions
     */
    @RequestMapping(value = "/validSubscriptions", method = RequestMethod.GET)
    public List<SubscriptionV1Dto> getSubscriptionsValidOn(@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyyMMdd") Optional<LocalDate> date) {
        return transformations.subscription2Dto(subscriptionRepository.findValidOn(date.orElse(LocalDate.now())));
    }
}
