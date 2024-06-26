import { AiFillHome } from 'react-icons/ai'
import { IoNewspaperOutline } from 'react-icons/io5'
import { BsFillGrid3X3GapFill } from 'react-icons/bs'
import { GrUserAdmin } from 'react-icons/gr'
import NavigationComponent from './NavigationComponent'
import Dropdown from '../utils/Dropdown'
import DropdownComponent from '../utils/DropdownComponent'
import Icon from '../utils/Icon'
import React from 'react'
import { SessionContext } from '../../../utils/context'

function Navigation(props) {
    const { session } = React.useContext(SessionContext)
    return (
        <div>
            <div className="nav">
                <NavigationComponent path="/" title="Home" uppercase />
                <NavigationComponent path="/problemsets" title="Problemsets" uppercase />
                {session.role === 1 && <NavigationComponent path="/manage" title="Manage" uppercase />}
                <NavigationComponent path="/submissions" title="Submissions" uppercase />
                <NavigationComponent path="/ranking" title="Ranking" uppercase />
                <NavigationComponent path="/contests" title="Contests" uppercase />
            </div>
            <div className="nav-dropdown">
                <Dropdown trigger={<Icon><BsFillGrid3X3GapFill /></Icon>} drop="center">
                    <DropdownComponent title="Home" icon={<AiFillHome />} href='/' />
                    <DropdownComponent title="Problemsets" icon={<IoNewspaperOutline />} href='/problemsets' />
                    {session.role === 1 && <DropdownComponent title="Manage" icon={<GrUserAdmin />} href="/manage" />}
                    <DropdownComponent title="Submissions" icon={<IoNewspaperOutline />} href='/submissions' />
                    <DropdownComponent title="Ranking" icon={<IoNewspaperOutline />} href='/ranking' />
                    <DropdownComponent title="Contests" icon={<IoNewspaperOutline />} href='/contests' />
                </Dropdown>
            </div>

        </div>

    )
}
export default Navigation