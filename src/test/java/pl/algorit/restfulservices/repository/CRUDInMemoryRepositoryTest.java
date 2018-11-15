package pl.algorit.restfulservices.repository;

import com.google.common.collect.ImmutableSet;
import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@AllArgsConstructor
@Test(groups = {"unit"})
public abstract class CRUDInMemoryRepositoryTest<ENTITY extends CRUDEntity, REPOSITORY extends CRUDInMemoryRepository<ENTITY>, CREATOR extends ObjectCreator<ENTITY>> {
    private CRUDInMemoryRepository<ENTITY> objectUnderTest;

    public void __constructor_usesObjectCreator_toInitializeInMemoryDataAndIncreaseObjectsCounter() {
        val oneObject = getTestObjectOneWithId();
        objectUnderTest = repositoryWithObjects(ImmutableSet.of(oneObject));

        @Cleanup
        val softly = new AutoCloseableSoftAssertions();
        softly.assertThat(objectUnderTest.getObjects())
                .containsExactlyInAnyOrder(oneObject)
                .doesNotHaveDuplicates();
        softly.assertThat(objectUnderTest.getLatestId().get()).isEqualTo(1);
    }

    public void __constructor_usingObjectCreator_doesNotAllowToCreateObjectsWithoutId() {
        ENTITY oneObject = getTestObjectWithoutId();
        val objectCreators = ImmutableSet.of(getCreator(Collections.singleton(oneObject)));
        assertThatThrownBy(() -> objectUnderTest = repositoryWithCreators(objectCreators))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Creator must creates objects with id");
    }

    public void __constructor_usingObjectCreator_doesNotAllowToCreateObjectsWithTheSameIds() {
        val oneObject = getTestObjectOneWithId();
        val oneObjectPrim = getTestObjectOneWithId();
        val objectCreators = ImmutableSet.of(
                getCreator(Collections.singleton(oneObject)),
                getCreator(Collections.singleton(oneObjectPrim)));

        assertThatThrownBy(() -> objectUnderTest = repositoryWithCreators(objectCreators))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Creators conflict - creators created object with the same id");
    }

    public void create_newObject_createsNewObjectWithId() {
        objectUnderTest = emptyRepository();
        val newObject = getTestObjectWithoutId();

        val result = objectUnderTest.create(newObject);

        @Cleanup
        val softly = new AutoCloseableSoftAssertions();
        softly.assertThat(result).isEqualTo(newObject);
        softly.assertThat(result.getId()).isEqualTo(1);
    }

    public void getAll_emptyRepository_returnsEmptyCollection() {
        objectUnderTest = emptyRepository();

        val result = objectUnderTest.getAll();

        Assertions.assertThat(result).isEmpty();
    }

    public void getAll_oneObjectInRepository_returnsSingleElementCollection() {
        val oneObject = getTestObjectOneWithId();
        objectUnderTest = repositoryWithObjects(ImmutableSet.of(oneObject));

        val result = objectUnderTest.getAll();

        Assertions.assertThat(result)
                .containsExactlyInAnyOrder(oneObject)
                .doesNotHaveDuplicates();
    }

    public void getById_getId1fromTwoObjects_returnsObjectWithId1AndDoesNotChangeObjects() {
        val oneObject = getTestObjectOneWithId();
        val twoObject = getTestObjectTwoWithId();
        objectUnderTest = repositoryWithObjects(ImmutableSet.of(oneObject, twoObject));

        val result = objectUnderTest.getById(1).orElseThrow(RuntimeException::new);

        @Cleanup
        val softly = new AutoCloseableSoftAssertions();
        softly.assertThat(result).isEqualTo(oneObject);
        softly.assertThat(objectUnderTest.getObjects()).hasSize(2);
        softly.assertThat(objectUnderTest.getLatestId().get()).isEqualTo(2);
    }

    public void getById_objectNotExistingWithinObjects_returnsEmptyOptional() {
        val oneObject = getTestObjectOneWithId();
        val twoObject = getTestObjectTwoWithId();
        objectUnderTest = repositoryWithObjects(ImmutableSet.of(oneObject, twoObject));

        val result = objectUnderTest.getById(3);
        assertThat(result).isEmpty();
    }

    public void update_objectWithId2_updatesObjectsCollection() {
        val oneObject = getTestObjectOneWithId();
        val twoObject = getTestObjectTwoWithId();
        objectUnderTest = repositoryWithObjects(ImmutableSet.of(oneObject, twoObject));

        val twoObjectPrim = getTestObjectTwoPrimWithId();
        objectUnderTest.update(twoObjectPrim);

        @Cleanup
        val softly = new AutoCloseableSoftAssertions();
        softly.assertThat(objectUnderTest.getObjects())
                .containsExactlyInAnyOrderElementsOf(Arrays.asList(oneObject, twoObjectPrim))
                .doesNotHaveDuplicates();
        softly.assertThat(objectUnderTest.getLatestId().get()).isEqualTo(2);
    }

    public void update_objectNotYetExistingWithinObjects_throwsException() {
        val oneObject = getTestObjectOneWithId();
        val twoObject = getTestObjectTwoWithId();
        objectUnderTest = repositoryWithObjects(ImmutableSet.of(oneObject, twoObject));

        val threePrim = getTestObjectThreeWithId();

        assertThatThrownBy(() -> objectUnderTest.update(threePrim))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("object with such id does not exists");
    }

    public void update_objectWithoutId_throwsException() {
        val oneObject = getTestObjectOneWithId();
        val twoObject = getTestObjectTwoWithId();
        objectUnderTest = repositoryWithObjects(ImmutableSet.of(oneObject, twoObject));

        val twoObjectPrim = getTestObjectWithoutId();

        assertThatThrownBy(() -> objectUnderTest.update(twoObjectPrim))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("can't update object without id");
    }

    public void delete_id1_removesObjectWithId1AndUpdatesObjects() {
        val oneObject = getTestObjectOneWithId();
        val twoObject = getTestObjectTwoWithId();
        objectUnderTest = repositoryWithObjects(ImmutableSet.of(oneObject, twoObject));

        objectUnderTest.deleteById(1);

        @Cleanup
        val softly = new AutoCloseableSoftAssertions();
        softly.assertThat(objectUnderTest.getObjects()).containsExactly(twoObject);
        softly.assertThat(objectUnderTest.getLatestId().get()).isEqualTo(2);
    }

    public void delete_objectNotExistingWithinObjects_throwsException() {
        val oneObject = getTestObjectOneWithId();
        val twoObject = getTestObjectTwoWithId();
        objectUnderTest = repositoryWithObjects(ImmutableSet.of(oneObject, twoObject));

        assertThatThrownBy(() -> objectUnderTest.deleteById(3))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("object with such id does not exists");
    }

    protected abstract REPOSITORY emptyRepository();

    protected abstract REPOSITORY repositoryWithObjects(Set<ENTITY> objects);

    protected abstract REPOSITORY repositoryWithCreators(Set<CREATOR> objectCreators);

    protected abstract CREATOR getCreator(Set<ENTITY> objects);

    protected abstract ENTITY getTestObjectWithoutId();

    protected abstract ENTITY getTestObjectOneWithId();

    protected abstract ENTITY getTestObjectTwoWithId();

    protected abstract ENTITY getTestObjectTwoPrimWithId();

    protected abstract ENTITY getTestObjectThreeWithId();
}
