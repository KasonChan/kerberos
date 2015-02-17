package kerberos.messages

import kerberos.encryption.ElGamalEncryptedMsg

/**
 * Created by kasonchan on 2/16/15.
 */
case class EncryptedService(serviceName: List[ElGamalEncryptedMsg], args: List[ElGamalEncryptedMsg])

case class ServiceRequest(CID: List[ElGamalEncryptedMsg])
