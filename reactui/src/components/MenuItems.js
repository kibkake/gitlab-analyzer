import * as GoIcons from "react-icons/go";
import * as HiIcons from "react-icons/hi";
import {AiOutlineExport} from "react-icons/ai";


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
        icon: <AiOutlineExport/>
    },
    {
        title: 'Signup',
        url: '#',
        cName: 'nav-links'
    }
]