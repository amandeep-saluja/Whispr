export const transformTime = (dateTime) => {
    return dateTime ? formatAMPM(new Date(dateTime)) : undefined;
};

function formatAMPM(date) {
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var ampm = hours >= 12 ? 'pm' : 'am';
    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'
    minutes = minutes < 10 ? '0' + minutes : minutes;
    var strTime = hours + ':' + minutes + ' ' + ampm;
    return strTime;
}

export const objIsEmpty = (obj) => {
    return (
        (obj != null) | (obj != undefined) && Object?.keys(obj)?.length === 0
    );
};

export const msgReceived = (chat, msg) => {
    let receivedRecipentCount = 0;
    const totalUsers = chat?.userDetails?.length;
    msg?.receivedUserId?.forEach((id) => {
        if (chat.userDetails.indexOf(id) != -1) receivedRecipentCount++;
    });
    return receivedRecipentCount == totalUsers;
};
