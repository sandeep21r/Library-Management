//package com.LibraryApi.LibraryManagement.RepositoryTest;
//
//import com.LibraryApi.LibraryManagement.Entity.TenantEntity;
//import com.LibraryApi.LibraryManagement.Entity.UserEntity;
//import com.LibraryApi.LibraryManagement.Repository.TenantRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import javax.swing.plaf.PanelUI;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class TenantRepositoryTest {
//
//    @Autowired
//    private TenantRepository tenantRepository;
//    private TenantEntity tenantEntity;
//    private UserEntity userEntity;
//    @Autowired
//    private TestEntityManager testEntityManager;
//
//
//    @BeforeEach
//    public void setUp(){
//        userEntity = new UserEntity("sandeeprathor","123");
//        testEntityManager.persist(userEntity);
//        tenantEntity = new TenantEntity("testEntity","testLocation","testEntity-testLocation",userEntity);
//
//        testEntityManager.persist(tenantEntity);
//    }
//    @Test
//    public  void TestExistsByRegistrationCode(){
//        boolean exits = tenantRepository.existsByRegistrationCode("testEntity-testLocation");
//        assertThat(exits).isTrue();
//    }
//    @Test
//    public void TestFindByRegistrationCode(){
//        TenantEntity tenant=tenantRepository.findByRegistrationCode("testEntity-testLocation").orElse(null);
//        assertEquals(tenant,this.tenantEntity);
//    }
//
//}
