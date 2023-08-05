import React, { useState } from 'react';
import './style.css';
import ReactDOM from 'react-dom/client';
import ChatRoom from './components/ChatRoom/ChatRoom';
import Login from './components/Login/Login';
import Home from './components/Home/Home';

const AppLayout = () => {
    const [user, setUser] = useState({});
    return (
        <>
            {Object.keys(user).length === 0 ? (
                <Login setUser={setUser} />
            ) : (
                <Home user={user} />
            )}
        </>
    );
};

const app = document.getElementById('app');

const root = ReactDOM.createRoot(app);

root.render(<AppLayout />);
