import { createSlice } from '@reduxjs/toolkit';

const chatSlice = createSlice({
    name: 'chat',
    initialState: {
        chats: {},
        activeChatId: '',
    },
    reducers: {
        initializeChats: (state, action) => {
            action.payload.forEach((chat) => (state.chats[chat.id] = chat));
        },
        clearChats: (state, payload) => {
            state.chats = {};
        },
        setActiveChat: (state, action) => {
            state.activeChatId = action.payload;
        },
        addNewChat: (state, action) => {
            state.chats[action.payload.id] = action.payload;
        },
        removeChat: (state, action) => {
            delete state.chats[action.payload];
            state.activeChatId = '';
        },
    },
});

export const { initializeChats, clearChats, setActiveChat, addNewChat, removeChat } =
    chatSlice.actions;

export default chatSlice.reducer;
