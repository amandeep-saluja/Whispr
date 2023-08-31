import React, { useEffect } from 'react';
import './style.css';
import { objIsEmpty } from './utils/Helper';
import ReactDOM from 'react-dom/client';
import Login from './components/Login/Login';
import Home from './components/Home/Home';
import Connector from './components/WebSocketConnection/Connector';
import { Provider, useDispatch, useSelector } from 'react-redux';
import store from './store/store';
import { initializeMessages } from './store/messageSlice';
import useSSE from './hooks/useSSE';

const AppLayout = () => {
    const currentUser = useSelector((store) => store.user.currentUser);

    const sse = useSSE(currentUser?.id);

    return objIsEmpty(currentUser) ? <Login /> : <Home />;
};

const app = document.getElementById('app');

const root = ReactDOM.createRoot(app);

root.render(
    <Provider store={store}>
        <AppLayout />
    </Provider>
);
