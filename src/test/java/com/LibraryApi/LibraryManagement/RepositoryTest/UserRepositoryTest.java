//package com.LibraryApi.LibraryManagement.RepositoryTest;
//
//import com.LibraryApi.LibraryManagement.Entity.UserEntity;
//import com.LibraryApi.LibraryManagement.Repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class UserRepositoryTest {
//    @Autowired
//    private UserRepository userRepository;
//
//    private UserEntity userEntity;
//    @Autowired
//    private TestEntityManager testEntityManager;
//
//
//    @BeforeEach
//    public void setUp(){
//        userEntity = new UserEntity("sandeepp","123");
//        testEntityManager.persist(userEntity);
//    }
//
//
//    @Test
//    public void TestExistsByUsername(){
//        boolean exists = userRepository.existsByUsername("sandeep");
//        assertThat(exists).isFalse();
//    }
//
//    @Test
//    public void TestFindByUsername(){
//        UserEntity user = userRepository.findByUsername("sandeepp").orElse(null);
//        assertEquals(user, this.userEntity) ;
//    }
//}
