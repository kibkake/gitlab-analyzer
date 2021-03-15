import React from 'react'
//[https://github.com/gopinav/React-Table-Tutorials/tree/master/react-table-demo/src/components]
export const Checkbox = React.forwardRef(({ indeterminate, ...rest }, ref) => {
    const defaultRef = React.useRef()
    const resolvedRef = ref || defaultRef

    React.useEffect(() => {
        resolvedRef.current.indeterminate = indeterminate
    }, [resolvedRef, indeterminate])

    return (
        <>
            <input type='checkbox' ref={resolvedRef} {...rest} />
        </>
    )
})