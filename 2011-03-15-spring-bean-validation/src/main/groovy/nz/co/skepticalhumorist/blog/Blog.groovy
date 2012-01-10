// $Id$
// Copyright 2011 John Hurst
// John Hurst (john.b.hurst@gmail.com)
// 2011-03-15

package nz.co.skepticalhumorist.blog

import javax.validation.constraints.Future
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size
import org.springframework.format.annotation.DateTimeFormat

class Blog {

  int id
  @NotNull
  @Size(min = 2, max = 20)
  String firstName
  @NotNull
  @Size(min = 2, max = 20)
  String lastName
  @Size(min = 2, max = 20)
  String middleName
  @NotNull
  @Past
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  Date dateOfBirth
  @Future
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  Date dateOfRetirement

  @Min(value = 0)
  @Max(value = 9)
  BigDecimal heightMeters

  @Min(value = 0)
  @Max(value = 300)
  BigDecimal weightKilograms

  @Pattern(regex = "[A-Za-z0-9\\.\\-]+@[A-Za-z0-9\\.\\-]+")
  String email
}
