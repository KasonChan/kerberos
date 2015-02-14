package kerberos.messages

import kerberos.encryption.ElGamalEncryptedMsg

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
