package kerberos.messages

import kerberos.encryption.ElGamalEncryptedMsg

/**
 * Created by kasonchan on 2/16/15.
 */
/**
 * Encrypted session key *
 * @param p ElGamalEncryptedMsg(t: BigInt, o: BigInt)
 * @param alpha ElGamalEncryptedMsg(t: BigInt, o: BigInt)
 * @param aa ElGamalEncryptedMsg(t: BigInt, o: BigInt)
 */
case class EncryptedSessionKey(p: ElGamalEncryptedMsg, alpha: ElGamalEncryptedMsg, aa: ElGamalEncryptedMsg)

/**
 * Encrypted session key token *
 * Encrypted by private key of the application server *
 * @param CID Encrypted client id: List[ElGamalEncryptedMsg(t: BigInt, o: BigInt)]
 * @param SID Encrypted application server id: List[ElGamalEncryptedMsg(t: BigInt, o: BigInt)]
 * @param SessionKey Encrypted session key: EncryptedSessionKey
 */
case class EncryptedToken(CID: List[ElGamalEncryptedMsg], SID: List[ElGamalEncryptedMsg], SessionKey: EncryptedSessionKey)

/**
 * Application service message *
 * @param name Encrypted name of the service: List[ElGamalEncryptedMsg]
 * @param args Encrypted arguments: List[List[ElGamalEncryptedMsg\]\]
 */
case class Service(name: String, args: List[String])

/**
 * Requests service *
 * @param CID Encrypted client id: List[ElGamalEncryptedMsg]
 * @param Service Service: Service
 * @param encryptedToken Encrypted session key token: EncryptedToken
 */
case class ServiceRequest(CID: String, Service: Service, encryptedToken: EncryptedToken)
