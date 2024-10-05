package com.example;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

public class LDAPAuthentication {

    /**
     * Tenta di autenticare un utente contro un server LDAP.
     *
     * @param user     Il nome utente da autenticare.
     * @param password La password dell'utente.
     * @return true se l'autenticazione è riuscita, false altrimenti.
     */
    public boolean authenticate(String user, String password) {
        // URL e porta del server LDAP
        String ldapHost = "10.0.0.1";
        int ldapPort = 389;

        // Costruzione dell'URL completo
        String providerUrl = "ldap://" + ldapHost + ":" + ldapPort;

        // DN (Distinguished Name) dell'utente
        String dn = "uid=" + user + ",dc=XXXXX,dc=YYY,dc=ZZ";

        // Configurazione delle properties per il contesto LDAP
        Hashtable<String, String> environment = new Hashtable<>();
        environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        environment.put(Context.PROVIDER_URL, providerUrl);
        environment.put(Context.SECURITY_AUTHENTICATION, "simple");
        environment.put(Context.SECURITY_PRINCIPAL, dn);
        environment.put(Context.SECURITY_CREDENTIALS, password);

        try {
            // Tentativo di creazione di un contesto iniziale
            new InitialDirContext(environment);
            System.out.println("Autenticazione riuscita.");
            return true;
        } catch (NamingException e) {
            // Gestione dell'errore di autenticazione
            System.out.println("Errore durante l'autenticazione: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        LDAPAuthentication ldapAuth = new LDAPAuthentication();

        // Esempio di username e password
        String username = "tiagoadami";
        String password = "super_secret_password";

        // Chiamata al metodo di autenticazione
        boolean isAuthenticated = ldapAuth.authenticate(username, password);

        // Stampa del risultato
        System.out.println("Is authenticated: " + isAuthenticated);
    }
}
