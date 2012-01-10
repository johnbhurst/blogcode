def sum(List l1, List l2) {
  def result = []
  l1.eachWithIndex {v, i ->
    result << v + l2[i]
  }
  return result
}

def a = [1, 2, 3]
def b = [10, 20, 30]
assert sum(a, b) == [11, 22, 33]

Collection zip(Collection cols, Closure closure) {
  def iters = cols*.iterator()
  def result = []
  while (iters.inject(true) {r, iter -> r && iter.hasNext()}) {
    result << closure.call(*iters*.next())
  }
  return result
}

assert zip([a, b]) {x, y -> x + y} == [11, 22, 33]

Collection zip(Collection a, Collection b, Closure closure) {
  return zip([a, b], closure)
}
assert zip(a, b) {x, y -> x + y} == [11, 22, 33]

Collection zip(Collection a, Collection b, Collection c, Closure closure) {
  return zip([a, b, c], closure)
}
def c = [100, 200, 300]
assert zip([a, b, c]) {x, y, z -> x + y + z} == [111, 222, 333]
assert zip(a, b, c) {x, y, z -> x + y + z} == [111, 222, 333]

// Here's a better way, built right in to Groovy:

assert [a, b, c].transpose().collect {x, y, z -> x + y + z} == [111, 222, 333]
assert [a, b, c].transpose()*.sum() == [111, 222, 333]

// Function that started it all:

def max(List a1, List a2) {
  result = []
  a1.eachWithIndex {item, i ->
    result << (item > a2[i] ? item : a2[i])
  }
  result
}

def d = [5, 9, 1, 6, 7]
def e = [7, 3, 1, 8, 9]

assert max(d, e) == [7, 9, 1, 8, 9]

def max2(List a1, List a2) {
  [a1, a2].transpose()*.max()
}

assert max2(d, e) == [7, 9, 1, 8, 9]

// or just

assert [d, e].transpose()*.max() == [7, 9, 1, 8, 9]

// this works just as well with more than two collections:

def f = [9, 7, 2, 8, 1]
assert [d, e, f].transpose()*.max() == [9, 9, 2, 8, 9]

@Grab(group='commons-lang', module='commons-lang', version='2.4')
import static org.apache.commons.lang.StringUtils.rightPad

def pad(Collection l, List lengths) {
  result = []
  l.eachWithIndex {item, i ->
    result << rightPad(item, lengths[i])
  }
  result
}

def strings = ["one", "two", "three", "four", "five", "six"]
def lens = [1, 2, 3, 4, 5, 6]
def padded = ["one", "two", "three", "four", "five ", "six   "]

assert pad(strings, lens) == padded

def pad2(Collection l, List lengths) {
  [l, lengths].transpose().collect {item, len -> rightPad(item, len)}
}

assert pad2(strings, lens) == padded

// or just

assert [strings, lens].transpose().collect {item, len -> rightPad(item, len)} == padded


