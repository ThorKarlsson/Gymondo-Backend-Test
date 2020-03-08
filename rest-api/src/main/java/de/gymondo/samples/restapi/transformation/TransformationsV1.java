package de.gymondo.samples.restapi.transformation;

import com.google.common.collect.ImmutableList;
import de.gymondo.samples.commons.entity.Subscription;
import de.gymondo.samples.commons.entity.User;
import de.gymondo.samples.commons.repository.SubscriptionRepository;
import de.gymondo.samples.restapi.dto.SubscriptionV1Dto;
import de.gymondo.samples.restapi.dto.UserV1Dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Transformation utility service.
 *
 * @author Rui Vilao (rui@gymondo.de)
 */
public class TransformationsV1 {

    private final SubscriptionRepository subscriptionRepository = new SubscriptionRepository();

    public List<UserV1Dto> user2Dto(List<User> users) {
        Map<Integer, List<Subscription>> subscriptions = subscriptionRepository.findAll();
        return users.stream().map(x ->
                UserV1Dto.builder()
                        .withAge(x.getAge())
                        .withId(x.getId())
                        .withName(x.getName())
                        .withGender(x.getGender())
                        .withSubscriptions(subscription2Dto(subscriptions.get(x.getId())))
                        .build())
                .collect(Collectors.toList());
    }

    public UserV1Dto user2Dto(User user) {
        if (user == null) {
            return null;
        }

        return user2Dto(ImmutableList.of(user)).get(0);
    }

    public List<SubscriptionV1Dto> subscription2Dto(List<Subscription> subscriptions) {
        return subscriptions.stream().map(x ->
                SubscriptionV1Dto.builder()
                        .withId(x.getId())
                        .withName(x.getName())
                        .withExpiration(x.getExpiration())
                        .build())
                .collect(Collectors.toList());
    }
}
