import * as React from "react";
import "../../../assets/styles/exit.css";
import {toast} from "react-toastify";
import {Link} from "react-router-dom";


export default class Exit extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            show : false
        };
        this._isMounted = false;
    }


    componentDidMount() {
        this._isMounted = true;
        toast.configure({rtl: true, className: "text-center", position: "top-right"});
    }

    componentWillUnmount() {
        this._isMounted = false;
    }

    render() {
        return (
            <div className="exit-wrapper">
                <div className="exit-question col-sm-12">
                    آیا می‌خواهید از حساب کاربری خود خارج شوید؟
                </div>
                <div className="row">
                    <div class="col-blank">
                        <span>
                            &nbsp;
                        </span>
                    </div>
                    <div className="col-option">
                        <Link to= "" style={{ color: 'inherit', textDecoration: 'inherit'}}>
                            <span className="option-box reject">
                                انصراف
                            </span>
                        </Link>
                    </div>
                    <div className="col-option">
                        <Link to= "/login" style={{ color: 'inherit', textDecoration: 'inherit'}}>
                            <span className="option-box leave">
                                خروج
                            </span>
                        </Link>
                    </div>
                </div>
            </div>
        );
    }
}


