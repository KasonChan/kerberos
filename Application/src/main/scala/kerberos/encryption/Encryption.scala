package kerberos.encryption

import kerberos.general.General

/**
 * Created by kasonchan on 2/16/15.
 */
case class ElGamalPublicKey(p: BigInt, alpha: BigInt, aa: BigInt)

case class ElGamalPrivateKey(a: BigInt)

case class ElGamalEncryptedMsg(t: BigInt, o: BigInt)

trait ElGamal extends General {
  /**
   * Returns public key and private key*
   * @param r BigInt
   * @return (public key, private key)
   */
  def ElGamal_GenerateKeyPair(r: BigInt): (ElGamalPublicKey, ElGamalPrivateKey) = {
    val primes = generatePrimes(r.toInt)

    val p = primes.last

    val a = 2

    val alpha = primes.init.last

    val aa = BigInt(alpha).pow(a) % p

    (ElGamalPublicKey(p, alpha, aa), ElGamalPrivateKey(a))
  }

  /**
   * Returns encrypted message *
   * @param publicKey ElGamalPublicKey(p: BigInt, alpha: BigInt, aa: BigInt)
   * @param m List[BigInt]
   * @return result: List[ElGamalEncryptedMsg(t: BigInt, o: BigInt)]
   */
  def ElGamal_EncryptMessage(publicKey: ElGamalPublicKey, m: List[BigInt]) = {
    val result = m.map(m => ElGamal_Encrypt(publicKey, m)).toList

    result
  }

  /**
   * Returns the encrypted message (t, o) *
   * @param publicKey ElGamalPublicKey
   * @param m BigInt
   * @return ElGamalEncryptedMsg(t: BigInt, o: BigInt)
   */
  def ElGamal_Encrypt(publicKey: ElGamalPublicKey, m: BigInt): ElGamalEncryptedMsg = {
    val p = publicKey.p
    val alpha = publicKey.alpha
    val aa = publicKey.aa
    val k = p / 2

    val t = alpha.pow(k.toInt)
    val o = m * (aa.pow(k.toInt) % p)

    ElGamalEncryptedMsg(t, o)
  }

  /**
   * Returns decrypted message *
   * @param publicKey ElGamalPublicKey(p: BigInt, alpha: BigInt, aa: BigInt)
   * @param privateKey ElGamalPrivateKey(a: BigInt)
   * @param c Seq[ElGamalEncryptedMsg]
   * @return String
   */
  def ElGamal_DecryptMessage(publicKey: ElGamalPublicKey, privateKey: ElGamalPrivateKey,
                             c: Seq[ElGamalEncryptedMsg]) = {
    val result = c.map(c => ElGamal_Decrypt(publicKey, privateKey, c)).toList

    bigIntToString(result)
  }

  /**
   * Returns the decrypted message *
   * @param publicKey ElGamalPublicKey(p: BigInt, alpha: BigInt, aa: BigInt)
   * @param privateKey ElGamalPrivateKey(a: BigInt)
   * @param c ElGamalEncryptedMsg(t: BigInt, o: BigInt)
   * @return Decrypted message: BigInt
   */
  def ElGamal_Decrypt(publicKey: ElGamalPublicKey, privateKey: ElGamalPrivateKey,
                      c: ElGamalEncryptedMsg): BigInt = {
    val p = publicKey.p
    val alpha = publicKey.alpha
    val a = privateKey.a
    val aa = publicKey.aa
    val k = p / 2

    val t = c.t.pow((p - 1 - a).toInt) % p
    val m = t * (c.o % p)

    m
  }
}
