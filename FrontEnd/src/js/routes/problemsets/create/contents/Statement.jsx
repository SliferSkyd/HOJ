import CustomTable from '../../../../components/utils/Table'
import TableRow from '../../../../components/utils/TableRow'
import TableCell from '../../../../components/utils/TableCell'
import TableCellHead from '../../../../components/utils/TableCellHead'

import React from 'react'
import { ProblemsetInfoContext } from '../../../../../utils/context'
import MarkdownArea from '../components/MarkdownArea'

function Statement() {
    const { info, setInfo } = React.useContext(ProblemsetInfoContext)
    const [example, setExample] = React.useState({ inp: '', out: '' })

    const onExampleChange = (e, type, value) => {
        e.preventDefault();
        const temp = { ...example }
        temp[type] = value
        setExample(temp)
    }

    const onExampleCreate = (e) => {
        e.preventDefault();
        if (example.inp.trim() !== '' && example.out.trim() !== '') {
            const temp = { ...info }
            temp.description.example.push(example)
            setExample({ inp: '', out: '' })
            setInfo(temp)
        }

    }

    const onExampleRemove = (e, key) => {
        e.preventDefault();
        const temp = { ...info }
        temp.description.example.splice(key, 1)
        setInfo(temp)
    }

    return (
        <div className='flex-vertical'>
            <div className="text-heading">Statement</div>
            <div className='flex-vertical'>
                <MarkdownArea title='Main statement' height='300px' type="statement_main" />
                <div>Example input/output </div>
                <CustomTable>
                    <TableRow>
                        <TableCellHead title="Input" />
                        <TableCellHead title="Output" />
                        <TableCellHead title="Action" />
                    </TableRow>
                    {
                        info.description.example.map((value, key) => (
                            <React.Fragment key={key}>
                                <TableRow>
                                    <TableCell padding="15px" align="left"><pre>{value.inp}</pre></TableCell>
                                    <TableCell padding="15px" align="left"><pre>{value.out}</pre></TableCell>
                                    <TableCell><button className="btn-submit" onClick={(e) => onExampleRemove(e, key)}>Remove</button></TableCell>
                                </TableRow>
                            </React.Fragment>
                        ))
                    }
                    <TableRow>
                        <TableCell padding="15px"><textarea rows="5" type="text" value={example['inp']} onChange={(e) => onExampleChange(e, 'inp', e.target.value)} /></TableCell>
                        <TableCell padding="15px"><textarea rows="5" type="text" value={example['out']} onChange={(e) => onExampleChange(e, 'out', e.target.value)} /></TableCell>
                        <TableCell><button className="btn-submit" onClick={(e) => onExampleCreate(e)}>Add</button></TableCell>
                    </TableRow>
                </CustomTable>
                <MarkdownArea title='Notes' height='150px' type="statement_note" />
            </div>
        </div>
    )
}
export default Statement