/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.jpa.compositions;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test showing the usage of a composite repository via {@link UserRepository}.
 *
 * @author Mark Paluch
 */
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class ComposedRepositoryTests {

	@Autowired UserRepository repository;

	/**
	 * Tests inserting a user and asserts it can be loaded again.
	 */
	@Test
	public void testInsert() {

		User user = new User();
		user.setUsername("username");

		user = repository.save(user);

		assertThat(repository.findById(user.getId())).hasValue(user);
	}

	/**
	 * Testing {@link ContactRepository} fragment.
	 */
	@Test
	public void testContactRepository() {

		User walter = new User();
		walter.setUsername("heisenberg");
		walter.setFirstname("Walter");
		walter.setLastname("White");

		User walterJr = new User();
		walterJr.setUsername("flynn");
		walterJr.setFirstname("Walter Jr.");
		walterJr.setLastname("White");

		repository.saveAll(Arrays.asList(walter, walterJr));

		assertThat(repository.findRelatives(walter)).contains(walterJr);
	}

	/**
	 * Testing {@link SoftDeletingRepository} fragment.
	 */
	@Test
	public void testSoftDelete() {

		User walter = new User();
		walter.setUsername("heisenberg");
		walter.setFirstname("Walter");
		walter.setLastname("White");

		repository.delete(walter);

		assertThat(walter.isDeleted()).isTrue();
	}

	/**
	 * Testing {@link SoftDeletingRepository} fragment.
	 */
	@Test
	public void testSoftDeleteByIdNotSupported() {

		User walter = new User();
		walter.setUsername("heisenberg");
		walter.setFirstname("Walter");
		walter.setLastname("White");

		repository.save(walter);

		assertThatThrownBy(() -> repository.deleteById(walter.getId())).isInstanceOf(UnsupportedOperationException.class);
	}
}
