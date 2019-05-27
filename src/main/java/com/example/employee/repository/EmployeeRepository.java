package com.example.employee.repository;

import com.example.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //以下所有的*都代表变量

    //1.查询名字是*的第一个employee
    @Query(nativeQuery = true, value = "select * from employee where name = ?1 limit 1")
    Employee findOneByEmployeeName(String name);

    //2.找出Employee表中第一个姓名包含`*`字符并且薪资大于*的雇员个人信息
    @Query(nativeQuery = true, value = "select * from employee where name like %?1% and salary > ?2")
    Employee findOneByWhoNameIncludeAndSalaryGreatThan(String name, Integer salary);

    //3.找出一个薪资最高且公司ID是*的雇员以及该雇员的姓名
    @Query(nativeQuery = true, value = "select e.name from " +
            "(select e.name, e.companyId, max(e.salary) from employee e group by e.companyId) e " +
            " where e.companyId = ?1 ")
    String findOneByWhoSalaryIsTopAndCompanyId(Integer companyId);

    //4.实现对Employee的分页查询，每页两个数据
    @Query(nativeQuery = true, value = "select * from employee")
    Page<Employee> list(Pageable pageable);

    //5.查找**的所在的公司的公司名称
    @Query(nativeQuery = true, value = "select c.companyName from company c inner join employee e " +
                                       "on c.id = e.companyId where e.name = ?1")
    String findCompanyNameByEmployeeName(String name);

    //6.将*的名字改成*,输出这次修改影响的行数
    @Modifying
    @Query(nativeQuery = true, value = "update employee set name = ?2 where name = ?1 ")
    void updateEmployeeName(String oldName, String newName);

    @Query(nativeQuery = true, value = "select ROW_COUNT()")
    Integer findAffectedRows();

    //7.删除姓名是*的employee

}
