//package com.example.admin.com.example.product_management.respoonsitory;
//
//import com.example.product_management.model.Roles;
//import com.example.product_management.responsitory.RoleResponsitory;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
////@SpringBootTest
//@DataJpaTest
//@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE )
//public class RoleResponsitoryTest {
//
//    @Autowired
//    private RoleResponsitory repo;
//
//    @Test
//    public void testCreateFirstRole(){
//        Roles roleAdmin =new Roles("Admin", "Manage everything");
//        Roles savedRole = repo.save(roleAdmin);
//
//        Assertions.assertThat(savedRole.getId()).isGreaterThan(0);
////    }
//}
////