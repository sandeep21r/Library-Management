//package com.LibraryApi.LibraryManagement.RepositoryTest;
//
//
//import com.LibraryApi.LibraryManagement.Entity.TenantEntity;
//import com.LibraryApi.LibraryManagement.Entity.UserEntity;
//import com.LibraryApi.LibraryManagement.NotUsed.UserRoleTenantEntity;
//import com.LibraryApi.LibraryManagement.NotUsed.UserRoleRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class UserRoleRepositoryTest {
//    @Autowired
//    private UserRoleRepository userRoleRepository;
//    private TenantEntity tenantEntity;
//    private UserEntity userEntity;
//    @Autowired
//    private TestEntityManager testEntityManager;
//
//    private UserRoleTenantEntity userRoleTenantEntity;
//    @BeforeEach
//    public void setUp(){
//        userEntity = new UserEntity("sandeeprathor","123");
//        testEntityManager.persist(userEntity);
//        tenantEntity = new TenantEntity("testEntity","testLocation","testEntity-testLocation",userEntity);
//        testEntityManager.persist(tenantEntity);
//        userRoleTenantEntity = new UserRoleTenantEntity("admin",userEntity,tenantEntity);
//        testEntityManager.persist(userRoleTenantEntity);
//    }
//
//    @Test
//    public void TestFindByUserEntity_idAndTenantEntity_id(){
//        UserRoleTenantEntity userRoleTenantEntity1 = userRoleRepository.findByUserEntity_idAndTenantEntity_id(userEntity.getId(),tenantEntity.getId());
//        assertEquals(userRoleTenantEntity1,this.userRoleTenantEntity);
//
//    }
//
//    @Test
//    public void TestFindRoleByUserIdAndTenantId(){
//        String  role = userRoleRepository.findRoleByUserIdAndTenantId(userEntity.getId(),tenantEntity.getId());
//        assertEquals(role,"admin");
//    }
//}
