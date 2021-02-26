import {user} from './Navbar_Developers'
import {rep} from './Navbar_Developers'
export const MenuItems = [

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
        title: 'Merge Requests',
        url: '/Repo/' + rep + '/Developers/' + user + '/codediff',
        cName: 'nav-links'
    },
    {
        title: 'Comments',
        url: '/Repo/' + rep + '/Developers/' + user + '/comments',
        cName: 'nav-links'
    },
    {
        title: 'Developers',
        url: '/Repo/' + rep + '/Developers',
        cName: 'nav-links'
    },

];