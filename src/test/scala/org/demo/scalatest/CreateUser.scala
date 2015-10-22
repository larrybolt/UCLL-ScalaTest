package org.demo.scalatest
import org.scalatest._
import org.scalatest.prop.TableDrivenPropertyChecks._
import domain._
import org.scalatest.prop.TableDrivenPropertyChecks._

class CreateUser extends FeatureSpec with GivenWhenThen {
  var person: Person = _
  var firstname: String = _
  var lastname: String = _
  var email: String = _
  var password: String = _

  def createPerson() {
    person = null
    person = new Person(email, password, firstname, lastname)
  }

  feature("Create user") {
    info("As an administrator")
    info("I want to be able to register users")
    info("So that I can limit access to the application")

    /**
     * case 1
     */
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

    /**
     * case 2
     * scenario 1
     */
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

    /**
     * case 2
     * scenario 2
     */
    scenario("the lastname of a user is not mandatory") {
      firstname = "Bert"
      lastname = null
      email = "bert.bertels@gmail.com"
      password = "1PasswordForBert"

      given(s"the lastname $lastname, email $email and password $password but no lastname")

      when("I choose to create the person with the given data")
      createPerson()

      then("a person object is created with these data and no lastname")
      assert(firstname == person.getFirstName)
      assert(lastname == person.getLastName)
      assert(email == person.getUserId)
      assert(person.getPassword != null)
    }
    
    /**
     * case 3
     * scenario 1
     */
    scenario("The password cannot be stored as plain text") {
      password = "PasswordForBert"

      given(s"the password $password")

      when("I choose to create a person with this password")
      createPerson()

      then("the password is stored as a digest of 40 characters")
      assert(person.getPassword.length() == 40)

    }

    /**
     * case 3
     * scenario 2
     */
    scenario("different passwords have different hashed values") {
      password = "PasswordForBert"
      
      given(s"the password $password and another password OtherPasswordForJan")
      val another_password = "OtherPasswordForJan"

      when("I choose to create a person with the first password and I choose to create a person with the second password")
      createPerson()
      val another_person = new Person(email, another_password, firstname, lastname)

      then("the stored password of the first person is different from the stored password of the second user")
      assert(person.getPassword != another_person.getPassword)
    }

    /**
     * case 3
     * scenario 3
     */
    scenario("identical passwords have different hashed values") {
      password = "PasswordForBert"
      
      given(s"the password $password")

      when("I choose to create a person with this password and I choose to create another person with this password")
      val another_person = new Person(email, password, firstname, lastname)

      then("the stored password of the first person is different from the stored password of the second user")
      assert(person.getPassword != another_person.getPassword)
    }

    /**
     * case 4
     * scenario 1
     */
    scenario("the email of a user is mandatory") {
      firstname = "Bert"
      lastname = "Bertels"
      password = "PasswordForBert"
      email = null
      
      given(s"the firstname $firstname, lastname $lastname and password $password but no email")

      when("I choose to create the person with the given data")
      
      then("an error is given and the person is not created")
      intercept[IllegalArgumentException] {
        createPerson()
      }
    }

    /**
     * case 4
     * scenario 2
     */
    scenario("the password of a user is mandatory") {
      firstname = "Bert"
      lastname = "Bertels"
      email = "bert.bertels@gmail.com"
      password = null
      
      given(s"the firstname $firstname, lastname $lastname and email $email but no password")
      
      when("I choose to create the person with the given data")
      
      then("an error is given and the person is not created")
      intercept[IllegalArgumentException] {
        createPerson()
      }
    }
    
    /**
     * case 5
     */
     scenario("the email of a user should be a valid email address"){
       given("an email address")
       val examples = Table (
           ("email", "motivation"),
           ("bert@gmail.com", "the local part can have one part"),
           ("bert.bertels@gmail.com", "the local and domain part can have two parts seperated by a dot"),
           ("bert.bertels@g.mail.com", "the domain part can have three parts seperated by a dot"),
           ("1-Be+rt.bertels@gmail.com", "the first local part can contain upper- en lowercase characters, digits, hyphens and plus signs"),
           ("bert.1ber-Tels@gmail.com", "the second local part can contain upper- en lowercase characters, digits and hyphens"),
           ("bert.bertels@gMail-1.com", "the first domain part can contain upper- en lowercase characters, digits and hyphens"),
           ("bert.bertels@gmail.cOm", "the third domain part can contain upper- en lowercase characters")
           )
           
        when("I choose to create a person with this email")
        
        then("a person object is created with the given email")
          forAll(examples){ (mail: String, motivation: String) =>
            whenever (mail != null){
              firstname = "Bert"
              lastname = "Bertels"
              email = mail
              password = "1PasswordForBert";
              
              createPerson()
              assert(person != null)
            }
       }
     }
     
     /**
      * case 6
      * scenario 1
      */
     scenario("the local part of an email address can have one part"){
       email = "bert@gmail.com"
       
       given(s"an email address $email")
       
       when("I choose to create a person with this email")
       
       then("a person object is created with the given email")
       createPerson()
       
       assert(person.getUserId == email)
     }
     
     /**
      * case 6
      * scenario 2
      */
     scenario("the local and domain part of an email address can have two parts seperated by a dot"){
       email = "bert.bertels@gmail.com"
       given(s"an email address $email")
       
       when("I choose to create a person with this email")
       then("a person object is created with the given email")
       createPerson()
       assert(person.getUserId == email)
     }
     
     /**
      * case 6
      * scenario 3
      */
     scenario("the domain part of an email address can have three parts separated by a dot") {
       email = "bert.bertels@g.mail.com"
       
       given(s"an email address $email")
       
       when("I choose to create a person with this email")
       
       then("a person object is created with the given email")
       createPerson()
       assert(person.getUserId == email)
     }
     
     /**
      * case 7 
      * TODO
      */
  }
}