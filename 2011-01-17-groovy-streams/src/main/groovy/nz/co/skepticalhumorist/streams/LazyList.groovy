// $Id$
// See http://cromwellian.blogspot.com/2006/07/infinite-streams-in-groovy-in-my-last.html

package nz.co.skepticalhumorist.streams

class LazyList {
  def car
  private Closure cdr
  static LazyList cons(def val, Closure c) {
    return new LazyList(val, c)
  }
  LazyList(def car, Closure cdr) {
    this.car = car
    this.cdr = cdr
  }
  LazyList getCdr() {
    cdr ? cdr.call() : null
  }
  List take(int n) {
    def r = []
    def l = this
    n.times {
      r.add(l.car)
      l = l.getCdr()
    }
    return r
  }
  def LazyList filter(Closure pred) {
    if (car != null) {
      if (pred(car)) {
        return cons(car, {getCdr().filter(pred) })
      }
      else {
        return getCdr().filter(pred)
      }
    }
    null
  }
  LazyList compose(Closure c, LazyList l) {
    return cons(c(getCar(), l.car), { getCdr().compose(c, l.cdr) })
  }

  LazyList map(Closure c) {
    return cons(c(getCar()), { getCdr().map(c) })
  }

}
