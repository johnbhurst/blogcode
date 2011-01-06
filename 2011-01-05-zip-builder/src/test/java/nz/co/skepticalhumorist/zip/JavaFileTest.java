// $Id$
// Copyright 2011 Skeptical Humorist
// John Hurst (john.b.hurst@gmail.com)
// 2011-01-07

package nz.co.skepticalhumorist.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class JavaFileTest {

  private void zipSrcTreeWithJdk(String dir, String zipFile) throws IOException {
    ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
    zipDirectory(new File(dir), zos);
    zos.close();
  }

  private void zipDirectory(File dir, ZipOutputStream zos) throws IOException {
    for (File file : dir.listFiles()) {
      if (file.isDirectory()) {
        zipDirectory(file, zos);
      }
      else {
        ZipEntry entry = new ZipEntry(file.getPath());
        entry.setSize(file.length());
        entry.setTime(file.lastModified());
        zos.putNextEntry(entry);
        IOUtils.copy(new FileInputStream(file), zos);
      }
    }
  }

  @Test
  public void testSrcTreeWithJdk() throws IOException {
    zipSrcTreeWithJdk("src", "java-src-jdk.zip");
  }
}
