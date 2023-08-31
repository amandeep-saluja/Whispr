import { createSlice } from '@reduxjs/toolkit';

const userSlice = createSlice({
    name: 'user',
    initialState: {
        userMap: {},
        currentUser: {},
    },
    reducers: {
        initializeUsers: (state, action) => {
            action.payload.forEach((user) => (state.userMap[user.id] = user));
        },
        addUser: (state, action) => {
            if (Object.entries(action.payload).length == 0) {
                state.userMap[action.payload.id] = action.payload;
            }
        },
        clearUsers: (state, action) => {
            state.userMap = {};
        },
        setLoggedInUser: (state, action) => {
            state.currentUser = action.payload;
        },
        clearLoggedInUser: (state, action) => {
            state.currentUser = {};
        },
        addChatId: (state, action) => {
            state.currentUser.chatIds.push(action.payload);
        },
        removeChatId: (state, action) => {
            const index = state.currentUser.chatIds.indexOf(action.payload);
            if (index > -1) {
                state.currentUser.chatIds.splice(index, 1);
            }
        },
    },
});

export const {
    addUser,
    addChatId,
    clearUsers,
    initializeUsers,
    setLoggedInUser,
    removeChatId,
} = userSlice.actions;

export default userSlice.reducer;
