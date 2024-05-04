import Content from "../../components/contents/Content"
import Header from "../../components/headers/Header"
import LeftSide from "../../components/contents/LeftSide"
import LeftSideComponent from "../../components/contents/LeftSideComponent"
import React from "react"

function Home() {
    React.useEffect(() => {
        document.title = "Home"
    })

    return (
        <React.Fragment>
            <Header />
            <Content>
                <LeftSide width="100%">
                    <LeftSideComponent>
                        <div style={{display: "flex", columnGap: 30}}>
                            <div>
                                <div className="text-heading" style={{ fontSize: 25}}>Welcome to Hoang's Online Judge System.</div>
                                <div className="text-content" style={{ fontSize: 18}}>
                                    <p>This is a simple online judge system, where you can submit your code and get the result of your code.</p>
                                    <p>Currently, the system only supports C++, Java and Python programming languages.</p>
                                </div>
                            </div>
                            <img src="https://c0.wallpaperflare.com/preview/919/570/398/lawyer-judge-african-cartoon.jpg" alt="" style={{height: 200}} />
                        </div>
                        
                    </LeftSideComponent>
                </LeftSide>
            </Content>

        </React.Fragment>

    )
}
export default Home