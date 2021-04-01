import * as GoIcons from "react-icons/go";
import * as HiIcons from "react-icons/hi";
import * as AIIcons from "react-icons/ai";
import {CgProfile} from 'react-icons/cg';
import {RiCamera2Line} from "react-icons/all";
export const MenuItems=[

    {
        title: 'Repositories',
        url: '/Repo',
        cName: 'nav-links',
        icon: <GoIcons.GoRepo/>
    },
    // {
    //     title: 'Export',
    //     url: '/Export',
    //     cName: 'nav-links',
    //     icon: <AIIcons.AiOutlineExport/>
    // },
    {
        title: 'Snapshots',
        url: '/Snapshots',
        cName: 'nav-links',
        icon: <RiCamera2Line/>
    },
    {
        title: 'Profile',
        url: '/Profile',
        cName: 'nav-links-signin',
        icon: <CgProfile/>
    }
]
