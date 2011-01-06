// $Id$
// Copyright 2011 ${CLIENT}
// John Hurst (john.b.hurst@gmail.com)
// 2011-01-05

package nz.co.skepticalhumorist.zip

import groovy.io.FileType
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import org.junit.Before
import org.junit.Test

class ZipBuilderFileTest {

  String dir = "tmp"

  @Before
  void setupTree() {
    new File("$dir/dir1/dir11").mkdirs()
    new File("$dir/dir1/dir12").mkdirs()
    new File("$dir/file1.txt").text = "file1"
    new File("$dir/dir1/file2.txt").text = "file2"
    new File("$dir/dir1/dir11/file3.txt").text = "file3"
    new File("$dir/dir1/dir12/file4.txt").text = "file4"
  }

  void zipFileTreeWithBuilder(String dir, String zipFile) {
    new ZipBuilder(new FileOutputStream(zipFile)).zip {
      new File(dir).traverse(type: FileType.FILES) {File file ->
        entry(file.path, size: file.length(), time: file.lastModified()) {it << file.bytes}
      }
    }
  }

  void zipFileTreeWithJdk(String dir, String zipFile) {
    new ZipOutputStream(new FileOutputStream(zipFile)).withStream {zos ->
      new File(dir).traverse(type: FileType.FILES) {File file ->
        def entry = new ZipEntry(file.path)
        entry.size = file.length()
        entry.time = file.lastModified()
        zos.putNextEntry(entry)
        zos << file.bytes
      }
    }
  }

  @Test
  void testFileTreeWithBuilder() {
    zipFileTreeWithBuilder(dir, "builder.zip")
  }

  @Test
  void testFileTreeWithGdk() {
    zipFileTreeWithJdk(dir, "jdk.zip")
  }

  @Test
  void testSrcTreeWithBuilder() {
    zipFileTreeWithBuilder("src", "src-builder.zip")
  }

  @Test
  void testSrcTreeWithJdk() {
    zipFileTreeWithJdk("src", "src-jdk.zip")
  }
}
