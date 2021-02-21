import {user} from './Navbar_Developers'
import {rep} from './Navbar_Developers'


import Navbar_Developers from "./Navbar_Developers";
export const MenuItems = [
    {
        title: 'Developers',
        url: '/Repo/' + rep + '/Developers',
        cName: 'nav-links'
    },
    {
        title: 'Summary',
        url: '/Repo/' + rep + '/Developers/' + user + '/summary',
        cName: 'nav-links'
    },
    //{
        //title: 'Code',
        //url: '/Repo/' + rep + '/Developers/' + user + '/codeContribution',
        //cName: 'nav-links'
    //}
    {
        title: 'Commits',
        url: '/Repo/' + rep + '/Developers/' + user + '/commits',
        cName: 'nav-links'
    },
    {
        title: 'Single Commit Diff',
        url: '/Repo/' + rep + '/Developers/' + user + '/codediff',
        cName: 'nav-links'
    },
    {
        title: 'Comment Contribution',
        url: '/Repo/' + rep + '/Developers/' + user + '/comments',
        cName: 'nav-links'
    },

];