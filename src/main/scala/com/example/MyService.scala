package com.example

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._
import spray.json._
import DefaultJsonProtocol._


// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class MyServiceActor extends Actor with MyService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}

case class Summation(summand1: Int, summand2: Int, sum: Int)
case class Subtraction(minuend: Int, subtrahend: Int, difference: Int)

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit val summationFormat = jsonFormat3(Summation)
  implicit val subtractionFormat = jsonFormat3(Subtraction)
}

import MyJsonProtocol._
import spray.json._

// this trait defines our service behavior independently from the service actor
trait MyService extends HttpService {

  val myRoute =
    path("") {
      get {
        respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
          complete {
            <html>
              <body>
                <h1>Say hello to <i>spray-routing</i> on <i>spray-can</i>!</h1>
              </body>
            </html>
          }
        }
      } 
    } ~		
	path("sum" / IntNumber) { summand1 =>
		parameters("addend".as[Int]) { summand2 =>
			get {
				val summation = new Summation(summand1, summand2, summand1 + summand2)
				respondWithMediaType(MediaTypes.`application/json`) {
					complete(summation.toJson.prettyPrint)
				}
			}
		}
	} ~
	path("difference" / IntNumber) { minuend =>
		parameters("subtrahend".as[Int]) { subtrahend =>
			get {
				val subtraction = new Subtraction(minuend, subtrahend, minuend - subtrahend)
				respondWithMediaType(MediaTypes.`application/json`) {
					complete(subtraction.toJson.prettyPrint)
				}
			}
		}
	}
}