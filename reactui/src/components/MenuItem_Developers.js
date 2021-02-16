import {user} from './Navbar_Developers'
import {rep} from './Navbar_Developers'


import Navbar_Developers from "./Navbar_Developers";
export const MenuItems = [
    {
        title: 'Summary',
        url: '/Developers/summary',
        cName: 'nav-links'
    },
    {
        title: 'Code Contribution',
        url: '/Developers/commits',
        cName: 'nav-links'
    },
    {
        title: 'Single Commit Diff',
        url: '/Developers/codediff',
        cName: 'nav-links'
    },
    {
        title: 'Comment Contribution',
        url: '/Developers/comments',
        cName: 'nav-links'
    },
    {
        title: 'Commits',
        url: '/Repo/' + rep + '/Developers/' + user + '/commits',
        cName: 'nav-links'
    }
];