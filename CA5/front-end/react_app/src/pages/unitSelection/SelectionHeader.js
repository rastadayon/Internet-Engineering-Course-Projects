import * as React from "react";
import "../../assets/styles/courses-styles.css";

export default class SelectionHeader extends React.Component{

     constructor(props) {
         super(props);
         this.state = {      
        }
     }

     render() {
         return (
             <div class="selection-item">
                <div class="selection-header">
                    <div class="row no-gutters">
                        <div class="col-trash">
                            <div class="selection-index">
                                <span>
                                    &nbsp;
                                </span>
                            </div>
                        </div>
                        <div class="col-status">
                            <div class="selection-index">
                                <span>
                                    وضعیت
                                </span>
                            </div>
                        </div>
                        <div class="col-code">
                            <div class="selection-index">
                                <span>
                                    کد
                                </span>
                            </div>
                        </div>
                        <div class="col-name">
                            <div class="selection-index">
                                <span>
                                    نام درس
                                </span>
                            </div>
                        </div>
                        <div class="col-instructor">
                            <div class="selection-index">
                                <span>
                                    استاد
                                </span>
                            </div>
                        </div>
                        <div class="col-1">
                            <div class="selection-units">
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