package kerberos.general

/**
 * Created by kasonchan on 2/10/15.
 */
case class DAB(d: Int, a: Int, b: Int)

trait General {
  /**
   * Returns the greatest common divisor of a and b *
   * @param a Integer
   * @param b Integer
   * @return gcd(a, b) Integer
   */
  def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

  /**
   * Returns d = gcd(a, b) and integers x, y satisfying ax + by = d *
   * @param a Integer
   * @param b Integer
   * @return (d: Int, x: Int, y: Int)
   */
  def extendedGcd(a: Int, b: Int): DAB = {
    if (b == 0)
      DAB(a, 1, 0)

    else {
      val r: DAB = extendedGcd(b, a % b)
      val d = r.d;
      val x = r.b;
      val y = r.a - (a / b) * r.b

      DAB(d, x, y)
    }
  }

  /**
   * Returns a list of prime numbers *
   * @param n Integer
   */
  def generatePrimes(n: Int) = {
    val list = (1 to n).toList

    val list2 = list.filterNot(l => (l != 2) && (l % 2 == 0))

    val primes = multiplesFilter(2, n, list2)

    primes
  }

  /**
   * Returns a list of prime numbers*
   * @param x filtered prime number: Integer
   * @param n Integer
   * @param list List[Integer]
   * @return prime numbers: List[Integer]
   */
  def multiplesFilter(x: Int, n: Int, list: List[Int]): List[Int] = {
    if (x < Math.sqrt(n).ceil)
      multiplesFilter(x + 1, n,
        list.filterNot(l => (l != list(x)) && (l % list(x) == 0)))
    else list
  }

  /**
   * Converts a string m to a list of BigInt *
   * @param m String
   * @return List[BigInt]
   */
  def stringToBigInt(m: String): List[BigInt] = {
    m.map(m => BigInt(m)).toList
  }

  /**
   * Converts a string m to a list of Int *
   * @param m String
   * @return List[Int]
   */
  def stringToInt(m: String): List[Int] = {
    m.map(m => m.toInt).toList
  }

  /**
   * Converts a list of Int to String *
   * @param l List[Int]
   * @return String
   */
  def intToString(l: List[Int]): String = {
    (l.map(m => m.toChar)).mkString
  }

  /**
   * Converts a list of Double to String *
   * @param l List[Double]
   * @return String
   */
  def doubleToString(l: List[Double]): String = {
    (l.map(m => m.toInt.toChar)).mkString
  }

  /**
   * Converts a list of BigInt to String *
   * @param l List[BigInt]
   * @return String
   */
  def bigIntToString(l: List[BigInt]): String = {
    (l.map(m => m.toChar)).mkString
  }

  /**
   * Returns a set of integers congruence mod m where m is positive integer and
   * n is the range of -n to n *
   * @param m modulus: Integer
   * @param n range: Integer
   * @return Seq[Seq[Int]/]
   */
  def residueClass(m: Int, n: Int): Seq[Seq[Int]] = {
    val rs = residues(m)

    rs.map(r => congruence(m, r, 21))
  }

  /**
   * Returns the congruence the residue r is the same for a set of integers
   * ranging from -n to n
   * a is congruent to b % n if m / (a - b) = 0 *
   * @param m modulus: Integer
   * @param r residue: Integer
   * @param n range: Integer
   * @return Seq[Int]
   */
  def congruence(m: Int, r: Int, n: Int): Seq[Int] = {
    for {
      a <- -n to n
      if (mod(a, m) == r)
    } yield a
  }

  /**
   * Returns the multiplicative inverse of positive integer m *
   * @param m Integer
   * @return Seq[Int]
   */
  def multiplicativeInverse(m: Int): Option[Seq[Int]] = {
    val r = residues(m)

    val i = for {
      x <- r
      y <- r
      if (mod((x * y), m) == 1)
    } yield x

    if (i.nonEmpty) Some(i)
    else None
  }

  /**
   * Returns a set of residues of remainders obtained by dividing positive
   * integers by a chosen positive number m *
   * @param m modulus: Integer
   * @return Seq[Int]
   */
  def residues(m: Int): Seq[Int] = {
    for (i <- 0 until m)
    yield i
  }

  /**
   * Returns the result of n mod m *
   * @param n Integer
   * @param m Integer
   * @return n mod m
   */
  def mod(n: Int, m: Int): Int = {
    if (n < 0) {
      (m - (Math.abs(n) % m)) % m
    }
    else if (m < 0) {
      (m - (-n % m)) % m
    }
    else {
      n % m
    }
  }
}

