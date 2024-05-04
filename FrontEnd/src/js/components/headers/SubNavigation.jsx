import NavigationComponent from "./NavigationComponent"

function SubNavigation() {
    return (
        <div className="content">
            <div className="sub-nav">
                <NavigationComponent path="/problemsets" title="Problems" />
                <NavigationComponent path="/problemsets/submit" title="Submit" />
            </div>
        </div>

    )
}
export default SubNavigation