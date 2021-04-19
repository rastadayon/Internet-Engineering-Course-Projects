import React from "react";

export default class HomeTopSection extends React.Component {
    render() {
        return (
            <nav>
                <div className="background-img">
                    <img src={require("../../assets/images/cover_photo.png")} alt=""/>
                </div>
            </nav>
        );
    }
}