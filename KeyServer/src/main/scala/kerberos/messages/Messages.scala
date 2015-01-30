package kerberos.messages

/**
 * Created by kasonchan on 1/29/15.
 */
/**
 * Request session key message *
 * @param CID Client id: String
 * @param SID Application server id: String
 */
case class SessionKeyRequest(CID: String, SID: String)

/**
 * Encrypted session key token *
 * Encrypted by private key of the application server *
 * @param CID Encrypted client id: String
 * @param SID Encrypted application server id: String
 * @param SessionKey Encrypted session key: BigInt
 */
case class EncryptedToken(CID: String, SID: String, SessionKey: BigInt)

/**
 * Reply session key message *
 * Encrypted by client's private key *
 * @param CID Encrypted client id: String
 * @param SID Encrypted application server id: String
 * @param SessionKey Encrypted session key: BigInt
 * @param encryptedToken Encrypted session key token
 */
case class SessionKeyReply(CID: String, SID: String, SessionKey: BigInt, encryptedToken: EncryptedToken)