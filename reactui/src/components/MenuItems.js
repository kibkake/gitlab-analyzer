import * as GoIcons from "react-icons/go";
import * as HiIcons from "react-icons/hi";
import * as AIIcons from "react-icons/ai";
import {CgProfile} from 'react-icons/cg';
import {IoSettingsOutline} from 'react-icons/io5';
export const MenuItems=[

    {
        title: 'Repositories',
        url: '/Repo',
        cName: 'nav-links',
        icon: <GoIcons.GoRepo/>
    },
    {


        title: 'Developers',
        url: '/Developers',
        cName: 'nav-links',
        icon: <HiIcons.HiOutlineUserGroup/>
    },
    {
        title: 'Export',
        url: '/Export',
        cName: 'nav-links',
        icon: <AIIcons.AiOutlineExport/>
    },
    {
        title: 'Settings',
        url: '/Settings',
        cName: 'nav-links',
        icon: <IoSettingsOutline/>
    },

    {
        title: 'Profile',
        url: '#',
        cName: 'nav-links-signin',
        icon:<CgProfile/>
    }
]