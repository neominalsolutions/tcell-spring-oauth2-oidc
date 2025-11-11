export const authConfig = {
	clientId: 'react-oidc-client',
	redirectUri: 'http://localhost:5173/login/oauth2/code/react-oidc-client',
	postLogoutRedirectUri: 'http://localhost:5173/',
	authority: 'http://localhost:8080', // Authorization Server URL
	scopes: ['openid', 'profile', 'offline_access'],
	automaticSilentRenew: true, // otomatik access token yenileme
};

// Bu özelliğin aktif olması için aşağıdaki özellikler auth serverdan açmamız lazım.
// sunucu tarafında .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
// .scope("offline_access")
