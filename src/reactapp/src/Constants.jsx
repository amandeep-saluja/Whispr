export const BASE_URL = 'http://localhost:8800/';

export const USERS_ALL = BASE_URL + 'user/all';

export const NEW_CHAT = BASE_URL + 'chat/create';

export const LOGIN = BASE_URL + 'user/login';

export const SIGNUP = BASE_URL + 'user/save';

export const CHATS_BY_USER_ID = BASE_URL + '/user/all-chats?userId=';

export const WS_URL = BASE_URL + 'stomp-endpoint';

export const MESSAGE_SEND = '/app/send';

export const MESSAGE_RECEIVED = '/app/received';

export const MESSAGE_READ = '/app/read';

export const MESSAGE_LISTENER = (userId) => `/user/${userId}/message`;

export const MESSAGE_RECEIEVED_RECEPIENT_LISTENER = (userId) => `/user/${userId}/message-received`;

export const MESSAGE_READ_RECEPIENT_LISTENER = (userId) => `/user/${userId}/message-read`;

export const MESSAGE_ALL = '/app/all';

export const DELETE_CHAT = BASE_URL + 'chat/delete?chatId=';
