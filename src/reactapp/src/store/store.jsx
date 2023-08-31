import { configureStore } from '@reduxjs/toolkit';
import userSlice from './userSlice';
import chatSlice from './chatSlice';
import messageSlice from './messageSlice';

const store = configureStore({
    reducer: {
        user: userSlice,
        chat: chatSlice,
        message: messageSlice,
    },
});

export default store;
