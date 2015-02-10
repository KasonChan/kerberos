package kerberos.encryption

import kerberos.general.General

/**
 * Created by kasonchan on 2/10/15.
 */
case class ElGamalPublicKey(p: BigInt, alpha: BigInt, aa: BigInt)

case class ElGamalPrivateKey(a: BigInt)

case class ElGamalEncryptedMsg(t: BigInt, o: BigInt)

trait ElGamal extends General {
  /**
   * Returns public key and private key*
   * @param r Integer
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
   * Return the encrypted message (t, o)*
   * @param publicKey ElGamalPublicKey
   * @param m String
   * @return (t, o)
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
   * Returns the decrypted message *
   * @param publicKey ElGamalPublicKey
   * @param privateKey ElGamalPrivateKey
   * @param c Encrypted message
   * @return Decrypted message: Int
   */
  def ElGamal_Decrypt(publicKey: ElGamalPublicKey, privateKey: ElGamalPrivateKey,
                      c: ElGamalEncryptedMsg): Int = {
    val p = publicKey.p
    val alpha = publicKey.alpha
    val a = privateKey.a
    val aa = publicKey.aa
    val k = p / 2

    val t = c.t.pow((p - 1 - a).toInt) % p
    val m = t * (c.o % p)

    m.toInt
  }
}

