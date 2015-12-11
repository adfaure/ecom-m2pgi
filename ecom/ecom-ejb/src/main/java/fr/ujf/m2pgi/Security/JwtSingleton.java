package fr.ujf.m2pgi.Security;

import fr.ujf.m2pgi.Security.JwtInfo;
import fr.ujf.m2pgi.SessionExpiredException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;

/**
* A connection to Elasticsearch implemented as a Singleton Session Bean
* I started to add listeners on entities to automatically index or remove documents in Elasticsearch.
*/
@Singleton
@Startup// The EJB container must initialize the singleton session bean upon application startup.
public class JwtSingleton {

	RsaJsonWebKey rsaJsonWebKey;

	// Use JwtConsumerBuilder to construct an appropriate JwtConsumer, which will
	// be used to validate and process the JWT.
	// The specific validation requirements for a JWT are context dependent, however,
	// it typically advisable to require a expiration time, a trusted issuer, and
	// and audience that identifies your system as the intended recipient.
	// If the JWT is encrypted too, you need only provide a decryption key or
	// decryption key resolver to the builder.
	JwtConsumer jwtConsumer;

	@PostConstruct// on startup
	void init() {

		// Generate an RSA key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
		try {
			rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
		} catch (JoseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Give the JWK a Key ID (kid), which is just the polite thing to do
		rsaJsonWebKey.setKeyId("k1");

		jwtConsumer = new JwtConsumerBuilder()
		//.setRequireExpirationTime() // the JWT must have an expiration time
		//.setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
		//.setRequireSubject() // the JWT must have a subject claim
		//.setExpectedIssuer("Issuer") // whom the JWT needs to have been issued by
		//.setExpectedAudience("Audience") // to whom the JWT is intended for
		.setVerificationKey(rsaJsonWebKey.getKey()) // verify the signature with the public key
		.build(); // create the JwtConsumer instance
	}

	public String generateToken(Long id, String group) {
		// Create the Claims, which will be the content of the JWT
		JwtClaims claims = new JwtClaims();
		//claims.setIssuer("Issuer");  // who creates the token and signs it
		//claims.setAudience("Audience"); // to whom the token is intended to be sent
		// Clients are issued with a token with a session time of 10 mins.
		//claims.setExpirationTimeMinutesInTheFuture(10); // time when the token will expire (10 minutes from now)
		//claims.setGeneratedJwtId(); // a unique identifier for the token
		//claims.setIssuedAtToNow();  // when the token was issued/created (now)
		//claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
		//claims.setSubject("subject"); // the subject/principal is whom the token is about
		claims.setClaim("id", id); // additional claims/attributes about the subject can be added
		claims.setClaim("group", group);


		// A JWT is a JWS and/or a JWE with JSON claims as the payload.
		// In this example it is a JWS so we create a JsonWebSignature object.
		JsonWebSignature jws = new JsonWebSignature();

		// The payload of the JWS is JSON content of the JWT Claims
		jws.setPayload(claims.toJson());

		// The JWT is signed using the private key
		jws.setKey(rsaJsonWebKey.getPrivateKey());

		// Set the Key ID (kid) header because it's just the polite thing to do.
		// We only have one key in this example but a using a Key ID helps
		// facilitate a smooth key rollover process
		jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());

		// Set the signature algorithm on the JWT/JWS that will integrity protect the claims
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

		// Sign the JWS and produce the compact serialization or the complete JWT/JWS
		// representation, which is a string consisting of three dot ('.') separated
		// base64url-encoded parts in the form Header.Payload.Signature
		// If you wanted to encrypt it, you can simply set this jwt as the payload
		// of a JsonWebEncryption object and set the cty (Content Type) header to "jwt".
		String jwt = null;
		try {
			jwt = jws.getCompactSerialization();
		} catch (JoseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jwt;
	}

	public JwtInfo validate(String jwt) {
		try
		{
			//  Validate the JWT and process it to the Claims
			JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);

			JwtInfo info =  new JwtInfo();
			info.setUserId((Long)jwtClaims.getClaimValue("id"));
			info.setUserGroup((String)jwtClaims.getClaimValue("group"));
			System.out.println("JWT id " + info.getUserId());
			System.out.println("JWT group " + info.getUserGroup());
			System.out.println("JWT validation succeeded! " + jwtClaims);
			return info;
		}
		catch (InvalidJwtException e)
		{
			// InvalidJwtException will be thrown, if the JWT failed processing or validation in anyway.
			// Hopefully with meaningful explanations(s) about what went wrong.
			System.out.println("Invalid JWT! " + e);
			return null;
		}
	}


}
