import React from "react";
import "./homeTopSection-styles.css";

export default class HomeTopSection extends React.Component {
    render() {
        return (
            <nav>
                <div className="background-img">
                    <img src={require("../../../assets/images/cover_photo.png")} alt=""/>
                </div>
            </nav>
        );
    }
}