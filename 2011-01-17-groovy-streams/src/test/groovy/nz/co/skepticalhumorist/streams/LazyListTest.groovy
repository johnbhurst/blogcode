// $Id$
// See http://cromwellian.blogspot.com/2006/07/infinite-streams-in-groovy-in-my-last.html

package nz.co.skepticalhumorist.streams

import org.junit.Test

class LazyListTest {

  def integers(n) {
    LazyList.cons(n, {integers(n + 1)})
  }

  @Test
  void testNaturalNumbers() {
    def naturalnumbers = integers(1)
    assert naturalnumbers.take(10) == [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

    def evennumbers = naturalnumbers.filter {it % 2 == 0}
    assert evennumbers.take(10) == [2, 4, 6, 8, 10, 12, 14, 16, 18, 20]
  }

  def sieve(list) {
    LazyList.cons(
      list.car,
      {sieve(list.getCdr().filter {x -> x % list.car != 0})}
    )
  }
  def primes = sieve(integers(2))

  @Test
  void testPrimesSieve() {
    assert primes.take(10) == [2, 3, 5, 7, 11, 13, 17, 19, 23, 29]
  }

  def fibo(a,b) {
    LazyList.cons(a, { fibo(b, a+b) })
  }

  @Test
  void testFibonacci() {
    assert fibo(0,1).take(10) == [0, 1, 1, 2, 3, 5, 8, 13, 21, 34]
  }

  @Test
  void testTwinPrimes() {
    def twinprimes = primes.compose({x,y -> [y-x == 2, x, y]}, primes.cdr).filter({it[0]}).map({ [it[1], it[2]] })
    assert twinprimes.take(10) == [
      [3, 5],
      [5, 7],
      [11, 13],
      [17, 19],
      [29, 31],
      [41, 43],
      [59, 61],
      [71, 73],
      [101, 103],
      [107, 109]
    ]
  }

}
