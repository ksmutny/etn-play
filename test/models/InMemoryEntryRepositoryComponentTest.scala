package models

import org.scalatest.{Matchers, FunSuite}

class InMemoryEntryRepositoryComponentTest extends FunSuite with Matchers {

  test("add + list") {
    val component = new InMemoryEntryRepositoryComponent {}
    val repository = component.entryRepository

    repository add "Hello"
    repository add "World"
    repository.list should be (List(Entry("Hello"), Entry("World")))
  }
}
