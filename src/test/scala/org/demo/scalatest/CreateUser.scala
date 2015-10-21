package org.demo.scalatest
import org.scalatest._
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
    info("I want to be dfdfable to register users")
    info("So that I can limit access to the application")
    scenario("The personal details of a user can be registered") {
      given("the firstname Bert, lastname Bertels, email bert.bertels@gmail.com and password 1PasswordForBert")
      val firstname = "Bert"
      val lastname = "Bertels"
      val email = "bert.bertels@gmail.com"
      val password = "1PasswordForBert"
      
      when("I choose to create the perosn with the given data")
      val person = new Person(email, password, firstname, lastname)
      
      then("a person object is created with these data")
      assert(firstname == person.getFirstName)
      assert(lastname == person.getLastName)
      assert(email == person.getUserId)
      assert(person.getPassword != null)
    }
    scenario("Scenario: the firstname of a user is not mandatory") {
      given("the lastname Bertels, email bert.bertels@gmail.com and password PasswordForBert but no firstname")
      val firstname = null;
      val lastname = "Bertels"
      val email = "bert.bertels@gmail.com"
      val password = "1PasswordForBert"

      when("I choose to create the person with the given data")
      val person = new Person(email, password, firstname, lastname)
      
      then("Then a person object is created with these data and no firstname")
      assert(firstname == person.getFirstName)
      assert(lastname == person.getLastName)
      assert(email == person.getUserId)
      assert(person.getPassword != null)
    }
    scenario("Scenario: the lastname of a user is not mandatory") {
      given("the lastname Bertels, email bert.bertels@gmail.com and password PasswordForBert but no lastname")
      val firstname = "Bert"
      val lastname = null
      val email = "bert.bertels@gmail.com"
      val password = "1PasswordForBert"

      when("I choose to create the person with the given data")
      val person = new Person(email, password, firstname, lastname)
      
      then("Then a person object is created with these data and no lastname")
      assert(firstname == person.getFirstName)
      assert(lastname == person.getLastName)
      assert(email == person.getUserId)
      assert(person.getPassword != null)
    }
  }
}