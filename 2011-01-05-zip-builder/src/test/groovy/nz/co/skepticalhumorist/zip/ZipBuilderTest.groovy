// $Id$
// Copyright 2011 ${CLIENT}
// John Hurst (john.b.hurst@gmail.com)
// 2011-01-05

package nz.co.skepticalhumorist.zip

import org.junit.Test

class ZipBuilderTest {
  @Test
  void testOk() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream()
    new ZipBuilder(baos).zip {
      entry("one") {os ->
        os.bytes = "Hello".bytes
      }
    }

    def content = []
    new ZipReader(new ByteArrayInputStream(baos.toByteArray())).withZip {zis, entry ->
      content << "${entry.name}:${entry.size}:${zis.text}"
    }
    assert content == ["one:-1:Hello"]
  }

  @Test
  void testWithDate() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream()
    new ZipBuilder(baos).zip {
      entry("one", time: Date.parse("yyyy-MM-dd HH:mm:ss", "2010-11-17 12:34:56").time) {os ->
        os.bytes = "Hello".bytes
      }
    }

    def content = []
    new ZipReader(new ByteArrayInputStream(baos.toByteArray())).withZip {zis, entry ->
      content << "${entry.name}:${new Date(entry.time).format("yyyy-MM-dd HH:mm:ss")}:${zis.text}"
    }
    assert content == ["one:2010-11-17 12:34:56:Hello"]
  }

  @Test
  void testTree() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream()
    new ZipBuilder(baos).zip {
      entry("tmp/file1.txt") {it << "file1"}
      entry("tmp/dir1/file2.txt") {it << "file2"}
      entry("tmp/dir1/dir11/file3.txt") {it << "file3"}
      entry("tmp/dir1/dir12/file4.txt") {it << "file4"}
    }

    def content = []
    new ZipReader(new ByteArrayInputStream(baos.toByteArray())).withZip {zis, entry ->
      content << "${entry.name}:${zis.text}"
    }
    assert content == [
      "tmp/file1.txt:file1",
      "tmp/dir1/file2.txt:file2",
      "tmp/dir1/dir11/file3.txt:file3",
      "tmp/dir1/dir12/file4.txt:file4"
    ]
  }

}
