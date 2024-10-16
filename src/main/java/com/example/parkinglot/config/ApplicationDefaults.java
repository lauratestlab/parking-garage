package com.example.parkinglot.config;

public interface ApplicationDefaults {
    interface Http {
        interface Cache {
            int timeToLiveInDays = 1461; // 4 years (including leap day)
        }
    }

    interface Mail {
        boolean enabled = false;
        String from = "";
        String baseUrl = "";
    }

    interface Security {
        String contentSecurityPolicy =
                "default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:";

        interface ClientAuthorization {
            String accessTokenUri = null;
            String tokenServiceId = null;
            String clientId = null;
            String clientSecret = null;
        }

        interface Authentication {
            interface Jwt {
                String secret = null;
                String base64Secret = null;
                long tokenValidityInSeconds = 1800; // 30 minutes
                long tokenValidityInSecondsForRememberMe = 2592000; // 30 days
            }
        }

        interface RememberMe {
            String key = null;
        }
    }
}
