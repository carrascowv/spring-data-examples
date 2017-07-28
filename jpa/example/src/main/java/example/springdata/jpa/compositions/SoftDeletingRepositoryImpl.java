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

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

/**
 * {@link SoftDeletingRepository} fragment implementation.
 *
 * @author Mark Paluch
 */
@RequiredArgsConstructor
public class SoftDeletingRepositoryImpl<T extends SoftDeletable, ID> implements SoftDeletingRepository<T, ID> {

	final @NonNull EntityManager entityManager;

	/*
	 * (non-Javadoc)
	 * @see example.springdata.jpa.compositions.SoftDeletingRepository#deleteById(java.lang.Object)
	 */
	@Override
	public void deleteById(ID id) {
		throw new UnsupportedOperationException("Cannot soft-delete without having an entity");
	}

	/*
	 * (non-Javadoc)
	 * @see example.springdata.jpa.compositions.SoftDeletingRepository#delete(java.lang.Object)
	 */
	@Override
	public void delete(T entity) {

		entity.delete();
		entityManager.persist(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see example.springdata.jpa.compositions.SoftDeletingRepository#deleteAll(java.lang.Iterable)
	 */
	@Override
	public void deleteAll(Iterable<? extends T> entities) {
		entities.forEach(this::delete);
	}

	/*
	 * (non-Javadoc)
	 * @see example.springdata.jpa.compositions.SoftDeletingRepository#deleteAll()
	 */
	@Override
	public void deleteAll() {
		throw new UnsupportedOperationException("Cannot soft-delete without having an entity");
	}
}
