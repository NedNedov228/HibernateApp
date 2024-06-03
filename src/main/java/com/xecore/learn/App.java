package com.xecore.learn;

import com.xecore.learn.model.Item;
import com.xecore.learn.model.Person;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class App
{
    public static void main( String[] args )
    {

        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

//////// Save
//
//            session.save(person);
//            Person person = new Person("Test",30);
//////// Update
//
//            Person p= session.get(Person.class, 2);
//            p.setName("NewName");
//
//////// Delete
//
//            Person p2= session.get(Person.class, 1);
//            session.delete(p2);
//
//////// HQL
//
//            session.createQuery("FROM Person where name like 'T%'").getResultList().forEach(System.out::println);
//            session.createQuery("delete from Person").executeUpdate();


//            Get Ordered Items

            Person person = session.get(Person.class, 3);
            System.out.println(person.toString());

            List<Item> items = person.getItems();

            items.forEach(System.out::println);

            Item item = session.get(Item.class, 3);
            System.out.println(item.toString());

            Person person2 = item.getOwner();
            System.out.println(person2.toString());

//            Create new order for person id=2

            Item newItem = new Item("New Test Item", person);
            person.getItems().add(newItem); // is not important but is a good practise
            session.save(newItem);

//            Create new Person and an order for him

            Person person3 = new Person("Test Person",36);

            Item newItem2 = new Item("New Test Item2", person3);
            person3.setItems(new ArrayList<Item>(Collections.singletonList(newItem2)));
            session.save(person3);
            session.save(newItem2);

            session.getTransaction().commit();
        }
        finally
        {
            sessionFactory.close();
        }
    }
}
