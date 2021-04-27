import * as React from "react";
import "./selectionHeader-styles.css";

export default class SelectionHeader extends React.Component{

     constructor(props) {
         super(props);
         this.state = {      
        }
     }

     render() {
         return (
             <div className="selection-item">
                <div className="selection-header">
                    <div className="row no-gutters">
                        <div className="col-trash">
                            <div className="selection-index">
                                <span>
                                    &nbsp;
                                </span>
                            </div>
                        </div>
                        <div className="col-status">
                            <div className="selection-index">
                                <span>
                                    وضعیت
                                </span>
                            </div>
                        </div>
                        <div className="col-code">
                            <div className="selection-index">
                                <span>
                                    کد
                                </span>
                            </div>
                        </div>
                        <div className="col-name">
                            <div className="selection-index">
                                <span>
                                    نام درس
                                </span>
                            </div>
                        </div>
                        <div className="col-instructor">
                            <div className="selection-index">
                                <span>
                                    استاد
                                </span>
                            </div>
                        </div>
                        <div className="col-1">
                            <div className="selection-units">
                                <span>
                                    واحد
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
         );
     }
}