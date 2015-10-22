package org.demo.scalatest
import org.scalatest._
import org.scalatest.prop.TableDrivenPropertyChecks._
import domain._

class CreateUser extends FeatureSpec with GivenWhenThen {
  var person : Person = _
  var firstname : String = _
  var lastname : String = _
  var email : String = _
  var password : String = _
  
  def createPerson() {
    person = null
    person = new Person(email, password, firstname, lastname)
  }

  feature("Create user"){
    info("As an administrator")
    info("I want to be able to register users")
    info("So that I can limit access to the application")
    scenario("The personal details of a user can be registered") {
      firstname = "Bert"
      lastname = "Bertels"
      email = "bert.bertels@gmail.com"
      password = "1PasswordForBert"
      given(s"the firstname $firstname, lastname $lastname, email $email and password $password")
      when("I choose to create the person with the given data")
      createPerson()
      
      then("a person object is created with these data")
      assert(firstname == person.getFirstName)
      assert(lastname == person.getLastName)
      assert(email == person.getUserId)
      assert(person.getPassword != null)
    }
    scenario("The firstname of a user is not mandatory") {
      firstname = null
      lastname = "Bertels"
      email = "bert.bertels@gmail.com"
      password = "1PasswordForBert"
      given(s"the lastname $lastname, email $email and password $password but no firstname")
      when("I choose to create the person with the given data")
      createPerson()

      then("a person object is created with these data and no firstname")
      assert(firstname == person.getFirstName)
      assert(lastname == person.getLastName)
      assert(email == person.getUserId)
      assert(person.getPassword != null)
    }
    scenario("the lastname of a user is not mandatory") {
      given("the lastname Bertels, email bert.bertels@gmail.com and password PasswordForBert but no lastname")
      firstname = "Bert"
      lastname = null
      email = "bert.bertels@gmail.com"
      password = "1PasswordForBert"

      when("I choose to create the person with the given data")
      val person = new Person(email, password, firstname, lastname)
      
      then("a person object is created with these data and no lastname")
      assert(firstname == person.getFirstName)
      assert(lastname == person.getLastName)
      assert(email == person.getUserId)
      assert(person.getPassword != null)
    }
    
    /**
     * specification 3
     * scenario 1 
     */
    scenario("The password cannot be stored as plain text") {
      
      given("the password PasswordForBert")
      password = "PasswordForBert"
      
      when("I choose to create a person with this password")
      createPerson()
      
      then("the password is stored as a digest of 40 characters")
      assert(person.getPassword.length() == 40)
      
    }
    
    /**
     * specification 3
     * scenario 2
     */
    scenario("different passwords have different hashed values"){
      given("the password PasswordForBert and another password OtherPasswordForJan")
      password = "PasswordForBert" 
      val another_password = "OtherPasswordForJan"
      
      when("I choose to create a person with the first password and I choose to create a person with the second password")
      createPerson()
      val another_person = new Person(email, another_password, firstname, lastname)
      
      then("the stored password of the first person is different from the stored password of the second user")
      assert(person.getPassword != another_person.getPassword)
    }
    
    /**
     * specification 3
     * scenario 3
     */
    scenario("identical passwords have different hashed values"){
      given("the password PasswordForBert")
      val password = "PasswordForBert"
      
      when("I choose to create a person with this password and I choose to create another person with this password")
      val person = new Person(email, password, firstname, lastname)
      val another_person = new Person(email, password, firstname, lastname)
      
      then("the stored password of the first person is different from the stored password of the second user")
      assert(person.getPassword != another_person.getPassword)
    }
    
    /**
     * specification 4
     * scenario 1
     */
    scenario("the email of a user is mandatory"){
      given("the firstname Bert, lastname Bertels and password PasswordForBert but no email")
      val firstname = "Bert"
      val lastname = "Bertels"
      val password = "PasswordForBert"
      val email = null
      
      when("I choose to create the person with the given data")
      val person = new Person(email, firstname, lastname, password)
      
      then("an error is given and the person is not created")
      intercept[IllegalArgumentException]{
        createPerson()
      }
    }
    
    /**
     * specification 4
     * scenario 2
     */
    scenario("the password of a user is mandatory"){
      given("the firstname Bert, lastname Bertels and email bert.bertels@gmail.com but no password")
      val firstname = "Bert"
      val lastname = "Bertels"
      val email = "bert.bertels@gmail.com"
      val password = null
      
      when("I choose to create the person with the given data")
      createPerson() 
    }
      

  }
}