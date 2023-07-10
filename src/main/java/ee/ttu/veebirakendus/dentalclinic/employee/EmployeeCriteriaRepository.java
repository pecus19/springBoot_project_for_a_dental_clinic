package ee.ttu.veebirakendus.dentalclinic.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeCriteriaRepository {
    private final EntityManager entityManager;

    public List<Employee> searchCount(EmployeeFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> employeeQuery = cb.createQuery(Employee.class);
        Root<Employee> root = employeeQuery.from(Employee.class);
        List<Predicate> predicateList = new ArrayList<>();

        if (filter.getFirstName() != null) {
            predicateList.add(cb.like(root.get("firstName"), filter.getFirstName() + "%"));
        }
        if (filter.getLastName() != null) {
            predicateList.add(cb.like(root.get("lastName"), filter.getLastName() + "%"));
        }
        employeeQuery.select(root).where(cb.and(predicateList.toArray(new Predicate[0])));

        if ("ASC".equals(filter.getOrder())) {
            employeeQuery.orderBy(cb.asc(root.get(filter.getOrderBy())));
        } else {
            employeeQuery.orderBy(cb.desc(root.get(filter.getOrderBy())));
        }
        var entityManagedQuery = entityManager.createQuery(employeeQuery);
        entityManagedQuery.setFirstResult(filter.getFirstResult());
        entityManagedQuery.setMaxResults(10);
        return entityManagedQuery.getResultList();
    }
}