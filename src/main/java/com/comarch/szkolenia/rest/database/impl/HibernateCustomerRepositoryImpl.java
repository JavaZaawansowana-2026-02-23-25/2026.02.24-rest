package com.comarch.szkolenia.rest.database.impl;

import com.comarch.szkolenia.rest.database.CustomerRepository;
import com.comarch.szkolenia.rest.model.Customer;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HibernateCustomerRepositoryImpl implements CustomerRepository {

    private final SessionFactory sessionFactory;

    @Override
    public List<Customer> getAllCustomers() {
        Session session = this.sessionFactory.openSession();
        String hql = "FROM com.comarch.szkolenia.rest.model.Customer";
        Query<Customer> query = session.createQuery(hql, Customer.class);
        List<Customer> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public Optional<Customer> getCustomerById(int id) {
        Session session = this.sessionFactory.openSession();
        String hql = "FROM com.comarch.szkolenia.rest.model.Customer WHERE id = :id";
        Query<Customer> query = session.createQuery(hql, Customer.class);
        query.setParameter("id", id);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Customer> updateCustomer(int id, Customer customer) {
        customer.setId(id);
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            Customer actual = session.merge(customer);
            session.getTransaction().commit();
            return Optional.of(actual);
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

        return Optional.empty();
    }

    @Override
    public void deleteCustomer(int id) {
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.remove(new Customer(id));
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public Customer addCustomer(Customer customer) {
        customer.setId(null);
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            Customer actual = session.merge(customer);
            session.getTransaction().commit();
            return actual;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

        return null;
    }

    @Override
    public void deleteCustomerByDomain(String domain) {
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            String hql = "DELETE FROM com.comarch.szkolenia.rest.model.Customer WHERE email LIKE :pattern";
            var query = session.createMutationQuery(hql);
            query.setParameter("pattern", "%" + domain);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
