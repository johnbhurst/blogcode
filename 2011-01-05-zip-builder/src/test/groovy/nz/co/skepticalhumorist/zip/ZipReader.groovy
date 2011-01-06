// $Id$
// Copyright 2011 ${CLIENT}
// John Hurst (john.b.hurst@gmail.com)
// 2011-01-05

package nz.co.skepticalhumorist.zip

import java.util.zip.ZipInputStream
import java.util.zip.ZipEntry

class ZipReader {

  class NonClosingInputStream extends FilterInputStream {
    NonClosingInputStream(InputStream target) {
      super(target)
    }

    void close() {
      // do nothing
    }
  }

  ZipInputStream zis

  ZipReader(InputStream is) {
    this.zis = new ZipInputStream(is)
  }

  void withZip(Closure closure) {
    ZipEntry entry = zis.nextEntry
    while (entry) {
      NonClosingInputStream is = new NonClosingInputStream(zis)
      closure.call(is, entry)
      entry = zis.nextEntry
    }
    zis.close()
  }
}
