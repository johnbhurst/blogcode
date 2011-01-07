// $Id$
// Copyright 2011 ${CLIENT}
// John Hurst (john.b.hurst@gmail.com)
// 2011-01-05

package nz.co.skepticalhumorist.zip

import groovy.transform.InheritConstructors
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class ZipBuilder {

  @InheritConstructors
  static class NonClosingOutputStream extends FilterOutputStream {
    void close() {
      // do nothing
    }
  }

  ZipOutputStream zos

  ZipBuilder(OutputStream os) {
    zos = new ZipOutputStream(os)
  }

  void zip(Closure closure) {
    closure.delegate = this
    closure.call()
    zos.close()
  }

  void entry(Map props, String name, Closure closure) {
    def entry = new ZipEntry(name)
    props.each {k, v -> entry[k] = v}
    zos.putNextEntry(entry)
    NonClosingOutputStream ncos = new NonClosingOutputStream(zos)
    closure.call(ncos)
  }

  void entry(String name, Closure closure) {
    entry([:], name, closure)
  }

  // ZipEntry properties:
  //setComment(String)
  //setCompressedSize(long)
  //setCrc(long)
  //setExtra(byte[])
  //setMethod(int)
  //setSize(long)
  //setTime(long)

}
