package de.gymondo.samples.commons.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.gymondo.samples.commons.entity.Subscription;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Simplified subscription repository.
 * <p>
 * Think of this as a database repository, so all
 * the calls go to the DB.
 *
 * @author Rui Vilao (rui@gymondo.de)
 */
public class SubscriptionRepository {

    /**
     * Gets a list of subscriptions for the user.
     *
     * @param userId The user userId.
     * @return The list of subscriptions.
     */
    public List<Subscription> findByUserId(Integer userId) {
        return Optional.ofNullable(SUBSCRIPTIONS.get(userId)).orElse(ImmutableList.of());
    }

    /**
     * Gets all subscriptions
     *
     * @return Map of User Id -> List of subscriptions
     */
    public Map<Integer, List<Subscription>> findAll() {
        return SUBSCRIPTIONS;
    }

    /**
     * Returns list of subscriptions that are valid on specified date
     *
     * @param date on which subscriptions should be valid
     * @return List of subscriptions
     */
    public List<Subscription> findValidOn(LocalDate date) {
        return SUBSCRIPTIONS.values().stream()
                .flatMap(Collection::stream)
                .filter(x -> x.getExpiration().isAfter(date))
                .collect(Collectors.toList());
    }

    // DATA. User Id -> List of subscriptions.
    private static final Map<Integer, List<Subscription>> SUBSCRIPTIONS = ImmutableMap.<Integer, List<Subscription>>builder()
            .put(1, ImmutableList.of(
                    Subscription.builder().withName("1 month").withExpiration(LocalDate.now().minusMonths(4).plusMonths(1)).withId(1).build(),
                    Subscription.builder().withName("1 month").withExpiration(LocalDate.now().minusMonths(3).plusMonths(1)).withId(2).build(),
                    Subscription.builder().withName("1 month").withExpiration(LocalDate.now().minusMonths(2).plusMonths(1)).withId(3).build(),
                    Subscription.builder().withName("1 month").withExpiration(LocalDate.now().minusMonths(1).plusMonths(1)).withId(4).build(),
                    Subscription.builder().withName("1 month").withExpiration(LocalDate.now().minusMonths(0).plusMonths(1)).withId(5).build()
            ))
            .put(2, ImmutableList.of(
                    Subscription.builder().withName("3 month").withExpiration(LocalDate.now().minusMonths(3).plusMonths(3)).withId(6).build(),
                    Subscription.builder().withName("3 month").withExpiration(LocalDate.now().minusMonths(0).plusMonths(3)).withId(7).build()
            ))
            .put(3, ImmutableList.of(
                    Subscription.builder().withName("6 month").withExpiration(LocalDate.now().minusMonths(6).plusMonths(6)).withId(8).build(),
                    Subscription.builder().withName("6 month").withExpiration(LocalDate.now().minusMonths(0).plusMonths(6)).withId(9).build()
            ))
            .put(4, ImmutableList.of(
                    Subscription.builder().withName("12 month").withExpiration(LocalDate.now().plusMonths(12)).withId(10).build())
            )
            .put(5, ImmutableList.of())
            .build();
}
