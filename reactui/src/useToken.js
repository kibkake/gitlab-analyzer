import { useState } from 'react';

function useToken() {
    const getUser=()=> {
        const userString = sessionStorage.getItem('user');
        return userString?.user
    };
    const [user, setUser] = useState(getUser());

    const saveUser=userString=> {
        sessionStorage.setItem('user', userString);
        setUser(userString.user);
    };

    return {
        setUser: saveUser, user
    }
}export default useToken;