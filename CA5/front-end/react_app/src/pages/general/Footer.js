import React from "react";
import "../../assets/styles/footer.css";

export default class Footer extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            test: 1
        };
    }

    render() {
        return (
            <footer className="footer">
                <div className="container">
                    <div className="row no-gutters flex justify-content-center align-items-center">
                        <div className="copy-right-text col-md-6">
                            <p>
                                ©
                                    دانشگاه تهران - سامانه جامع بلبلستان
                            </p>
                        </div>
                        <div classNmame="col-md-6 text-md-left">
                            <ul className="social list-unstyled">
                                <li>
                                    <a href="#">
                                        <i className="facebook-icon flaticon-facebook"></i> 
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i className="linkding-icon flaticon-linkedin-logo"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i className="instagram-icon flaticon-instagram"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i className="twitter-icon flaticon-twitter-logo-on-black-background"></i>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </footer>
        );
    }
}