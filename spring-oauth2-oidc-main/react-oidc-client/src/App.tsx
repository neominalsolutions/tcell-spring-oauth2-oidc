/* eslint-disable @typescript-eslint/no-explicit-any */
import React, { useEffect, useState } from 'react';
import { User } from 'oidc-client-ts';
import {
	handleCallback,
	login,
	logout,
	userManager,
} from './services/auth.client';

const App: React.FC = () => {
	const [user, setUser] = useState<User | null>(null);
	const [data, setData] = useState<any>(null);

	useEffect(() => {
		const handleAuth = async () => {
			// Eğer redirect ile geldiysen callback işlemini yap
			if (window.location.pathname === '/login/oauth2/code/react-oidc-client') {
				const loggedUser = await handleCallback();
				setUser(loggedUser);
				window.history.replaceState({}, document.title, '/'); // URL temizle
			}

            // token expire olursa yeni access token alma işlemi
			userManager.events.addAccessTokenExpired(() => {
				console.log(
					'Access token expired, silent renew triggered automatically'
				);

                // Sunucundan hala logout olmadığımız için, access token yenilemek için yeniden kullanıcıya ait
                // oturum bilgisini talep etmemiz gerekir.
                // bu talep sonrası /oauth/token endpoint tetiklenir ve yeni bir accesstoken alırız.
				// Silent renew sonrası user bilgisini güncelle
				userManager.getUser().then((data) => {
					console.log('User after silent renew:', data);
					setUser(data); // React state’i güncelle
				}); // silent renew sonrası güncel user
			});
		};
		handleAuth();
	}, []);

	const fetchData = async () => {
		if (user) {
			const response = await fetch('http://localhost:9090/api', {
				headers: {
					Authorization: `Bearer ${user.access_token}`,
				},
			});
			const data = await response.json();
			alert(`Fetched Data: ${JSON.stringify(data)}`);
			setData(data);
		} else {
			alert('User is not authenticated');
		}
	};

	return (
		<div style={{ padding: '2rem' }}>
			{!user && (
				<div>
					<button onClick={login}>Login with OAuth 2.0</button>
				</div>
			)}

			{user && (
				<div>
					<h2>Hoşgeldiniz, {user.profile?.sub}</h2>
					<pre>{JSON.stringify(user.profile, null, 2)}</pre>

					<button onClick={fetchData}>Fetch Data From Resource Server</button>

					<p>
						{data
							? `Fetched Data: ${JSON.stringify(data)}`
							: 'No data fetched yet.'}
					</p>
					<button onClick={logout}>Logout</button>
				</div>
			)}
		</div>
	);
};

export default App;
