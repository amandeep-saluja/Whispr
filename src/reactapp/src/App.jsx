import React from 'react';
import ReactDOM from 'react-dom/client';
import ChatRoom from './components/ChatRoom';

const AppLayout = () => {
    return (
        <>
            <ChatRoom />
        </>
    );
};

const app = document.getElementById('app');

const root = ReactDOM.createRoot(app);

root.render(<AppLayout />);
