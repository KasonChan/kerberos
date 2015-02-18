package kerberos.messages

import kerberos.encryption.{ElGamalPublicKey, ElGamalEncryptedMsg}

/**
 * Created by kasonchan on 1/29/15.
 */
/**
 * Requests session key message *
 * @param CID Client id: String
 * @param SID Application server id: String
 */
case class SessionKeyRequest(CID: String, SID: String)

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
 * Reply session key message *
 * Encrypted by client's private key *
 * @param CID Encrypted client id: List[ElGamalEncryptedMsg(t: BigInt, o: BigInt)]
 * @param SID Encrypted application server id: List[ElGamalEncryptedMsg(t: BigInt, o: BigInt)]
 * @param SessionKey Encrypted session key: EncryptedSessionKey
 * @param encryptedToken Encrypted session key token: EncryptedToken
 */
case class SessionKeyReply(CID: List[ElGamalEncryptedMsg], SID: List[ElGamalEncryptedMsg], SessionKey: EncryptedSessionKey, encryptedToken: EncryptedToken)

/**
 * Decrypted replied session key message *
 * @param CID Decrypted client id: String
 * @param SID Decrypted application server id: String
 * @param SessionKey Decrypted session key: ElGamalPublicKey(p: BigInt, alpha: BigInt, aa: BigInt)
 * @param encryptedToken Encrypted session key token: EncryptedToken
 */
case class DecryptedSessionKeyReply(CID: String, SID: String, SessionKey: ElGamalPublicKey, encryptedToken: EncryptedToken)

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