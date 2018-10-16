/*
 * Copyright (c) 2018 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

package com.github.nalukit.nalu.simpleapplication.client.data.service;

import com.github.nalukit.nalu.simpleapplication.client.data.model.dto.Address;
import com.github.nalukit.nalu.simpleapplication.client.data.model.dto.Person;
import com.github.nalukit.nalu.simpleapplication.client.data.model.dto.PersonSearch;
import com.github.nalukit.nalu.simpleapplication.client.data.model.exception.PersonNotFoundException;

import java.util.*;

/**
 * Due to the fact, that there is currently no J2Cl based server framework and to proof Nalu is working,
 * There is no need to do real server calls. So, we will simulate the server calls, to concentrate on
 * Nalu
 * <p>
 * It is up to you to decide which way you use to talk to the server!
 * <p>
 * For the example it does matter, if we retrieve the data form a server mock or a client mock!
 */
public class PersonService {

  private static PersonService instance;

  private Map<Long, Person> persons;

  private PersonService() {
    persons = new HashMap<>();
    initList();
  }

  public static PersonService get() {
    if (instance == null) {
      instance = new PersonService();
    }
    return instance;
  }

  private void initList() {
    Address address01 = new Address(1,
                                    "Evergreen Terrace",
                                    "7 42",
                                    "Springfield");
    persons.put(1L,
                new Person(1,
                           "Simpsons",
                           "Homer",
                           address01));
    persons.put(2L,
                new Person(2,
                           "Simpsons",
                           "Marge",
                           address01));
    persons.put(3L,
                new Person(3,
                           "Simpsons",
                           "Bart",
                           address01));
    persons.put(4L,
                new Person(4,
                           "Simpsons",
                           "Maggie",
                           address01));
    persons.put(5L,
                new Person(5,
                           "Simpsons",
                           "Lisa",
                           address01));
    Address address02 = new Address(2,
                                    "Blumenweg Nr. 13",
                                    "",
                                    "Entenhausen");
    persons.put(6L,
                new Person(6,
                           "Duck",
                           "Donald",
                           address02));
    persons.put(7L,
                new Person(7,
                           "Duck",
                           "Trick",
                           address02));
    persons.put(8L,
                new Person(8,
                           "Duck",
                           "Tick",
                           address02));
    persons.put(9L,
                new Person(9,
                           "Duck",
                           "Tack",
                           address02));
    Address address03 = new Address(2,
                                    "Am Goldberg Nr. 1",
                                    "",
                                    "Entenhausen");
    persons.put(10L,
                new Person(10,
                           "Duck",
                           "Dagobert",
                           address03));
  }

  public Person get(long id)
      throws PersonNotFoundException {
    if (persons.containsKey(id)) {
      return persons.get(id);
    } else {
      throw new PersonNotFoundException("no data found for ID >>" + Long.toString(id) + "<<");
    }
  }

  public List<Person> getAll() {
    List<Person> list = new ArrayList<>();
    for (Long aLong : persons.keySet()) {
      list.add(persons.get(aLong));
    }
    return list;
  }

  public List<Person> get(PersonSearch search) {
    List<Person> list = new ArrayList<>();
    if ((search.getName() != null &&
             search.getName()
                   .length() != 0) ||
        (search.getCity() != null &&
             search.getCity()
                   .length() != 0)) {
      for (Long aLong : persons.keySet()) {
        Person person = persons.get(aLong);
        if (search.getName() != null &&
            search.getName()
                  .length() != 0 &&
            search.getCity() != null &&
            search.getCity()
                  .length() != 0) {
          if (person.getName()
                    .toLowerCase()
                    .contains(search.getName()
                                    .toLowerCase()) &&
              person.getAddress()
                    .getCity()
                    .toLowerCase()
                    .contains(search.getCity()
                                    .toLowerCase())) {
            list.add(person);
          }
        } else if (search.getName() != null &&
            search.getName()
                  .length() != 0) {
          if (person.getName()
                    .toLowerCase()
                    .contains(search.getName()
                                    .toLowerCase())) {
            list.add(person);
          }
        } else if (search.getCity() != null &&
            search.getCity()
                  .length() != 0) {
          if (person.getAddress()
                    .getCity()
                    .toLowerCase()
                    .contains(search.getCity()
                                    .toLowerCase())) {
            list.add(person);
          }
        }
      }
    }
    return list;
  }

  public void insert(Person person) {
    Iterator<Person> iter = persons.values()
                                   .iterator();
    long maxKey = 0;
    while (iter.hasNext()) {
      Person element = iter.next();
      if (maxKey < element.getId()) {
        maxKey = element.getId();
      }
    }
    maxKey++;
    person.setId(maxKey);
    persons.put(maxKey,
                person);
  }

  public void update(Person person) {
    Person value = persons.get(person.getId());
    if (value != null) {
      persons.remove(person.getId());
      persons.put(person.getId(),
                  person);
    }
  }
}
